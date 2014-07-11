/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('customerServiceOrderAddController', function ($scope, $state, $http, Material, Invoice) {

        //init
        $scope.invoice = {
            num: '',
            inc: null,
            client: null,
            carNum: '',
            clientAddress: '',
            receivedDate: new Date(),
            remark: '',
            auditStatus: '0',
            invoiceType: {
                invoiceTypeId: '1'
            },
            invoiceDetails: []
        };
        //init temp variables
        $scope.data = undefined;
        $scope.searchTerm = '';
        var selectedNums = [];
        $scope.currentPage = 1;


        //search materials
        $scope.search = function () {
            $scope.loading = true;

            Material.findByNameOrNumLike({
                page: $scope.currentPage - 1,
                searchTerm:$scope.searchTerm
            },{}, function (data) {
                $scope.loading = false;
                $scope.data = data;
            });
        };
        $scope.onPageChanged = $scope.search;


        //add to selected list
        $scope.add = function (material) {
            if (selectedNums.indexOf(material.materialNum) < 0) {
                selectedNums.push(material.materialNum);
                var invoiceDetail = {};
                invoiceDetail.orderCount = 1;
                invoiceDetail.remark = '明细备注';
                invoiceDetail.unit = material.unit;
                invoiceDetail.auxiliaryUnitOne = material.auxiliaryUnitOne;
                invoiceDetail.auxiliaryUnitTwo = material.auxiliaryUnitTwo;
                invoiceDetail.conversionRateOne = material.conversionRateOne;
                invoiceDetail.conversionRateTwo = material.conversionRateTwo;
                invoiceDetail.price = material.price;
                invoiceDetail.material = material;
                $scope.invoice.invoiceDetails.push(invoiceDetail);
            }
        };

        //remove to selected list
        $scope.remove = function (materialNum) {
            if (selectedNums.indexOf(materialNum) >= 0) {
                selectedNums = selectedNums.filter(function (n) {
                    return materialNum != n;
                });
                $scope.invoice.invoiceDetails = $scope.invoice.invoiceDetails.filter(function (material) {
                    return materialNum != material.materialNum;
                })
            }
        };

        //submit
        $scope.submit = function () {
            Invoice.save({}, $scope.invoice).$promise.then(function (data) {
                console.log(data);
                $scope.success = true;
            }, function (data) {
                $scope.error = true;
            });
        };
    })

    .controller('customerServiceOrderViewController', function ($scope, $state, $stateParams, $http, Material, Invoice) {
        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });
    })

    .controller('customerServiceOrderEditController', function ($scope, $state, $stateParams, $http, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
            $scope.success = 40 == invoice.auditStatus;
        });

        //单据明细
        $scope.data = undefined;
        $scope.searchTerm = '';
        $scope.currentPage = 1;

        $scope.search = function () {
            $scope.loading = true;

            Material.findByNameOrNumLike({
                page: $scope.currentPage - 1,
                searchTerm:$scope.searchTerm
            },{}, function (data) {
                $scope.loading = false;
                $scope.data = data;
            });
        };
        $scope.onPageChanged = $scope.search;


        $scope.add = function (material) {

            var notExist = true;
            $scope.invoice.invoiceDetails.forEach(function (invoiceDetail) {
                if (invoiceDetail.material.materialNum == material.materialNum) {
                    notExist = false;
                }
            });

            if (notExist) {
                var invoiceDetail = {};
                invoiceDetail.orderCount = 1;
                invoiceDetail.remark = '明细备注';
                invoiceDetail.unit = material.unit;
                invoiceDetail.auxiliaryUnitOne = material.auxiliaryUnitOne;
                invoiceDetail.auxiliaryUnitTwo = material.auxiliaryUnitTwo;
                invoiceDetail.conversionRateOne = material.conversionRateOne;
                invoiceDetail.conversionRateTwo = material.conversionRateTwo;
                invoiceDetail.price = material.price;
                invoiceDetail.material = material;
                $scope.invoice.invoiceDetails.push(invoiceDetail);
            }

        };

        $scope.remove = function (materialNum) {
            $scope.invoice.invoiceDetails = $scope.invoice.invoiceDetails.filter(function (invoiceDetail) {
                return materialNum != invoiceDetail.material.materialNum;
            })
        };

        $scope.submit = function () {
            Invoice.update({}, $scope.invoice).$promise.then(function (data) {
                console.log(data);
                $scope.success = true;
            }, function (data) {
                $scope.error = true;
            });
        };
    })

    .controller('customerServiceOrderDraftsController', function ($scope, $state, $http, $location, $stateParams, Invoice, ngDialog) {
        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 30
                },
                actions: {
                    edit: function (invoiceId) {
                        $state.go('home.customer_service.order.edit', {invoiceId: invoiceId});
                    }
                },
                removeController:'customerServiceOrderRemoveConfirmCtrl'
            }
        };
    })

    .controller('customerServiceOrderReceivesController', function ($scope, $state) {

        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 20
                },
                actions: {
                    edit: function (invoiceId) {
                        $state.go('home.customer_service.order.receive', {invoiceId: invoiceId});
                    }
                }
            }
        };

    })

    .controller('customerServiceOrderSubmittedController', function ($scope, $state) {

        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 40
                },
                actions: {
                    view: function (invoiceId) {
                        $state.go('home.customer_service.order.view', {invoiceId: invoiceId});
                    }
                }
            }
        };

    })

    .controller('customerServiceOrderRemoveConfirmCtrl', function ($scope, ngDialog, Invoice) {

        $scope.confirm = function () {
            Invoice.remove({invoiceId: this.invoiceIdToRemove}, function (data) {
                $scope.$parent.data.content = $scope.$parent.data.content.filter(function (invoice) {
                    return invoice.invoiceId != $scope.$parent.invoiceIdToRemove;
                });
                $scope.$parent.invoiceIdToRemove = undefined;
                $scope.closeThisDialog();
            });
        };

        $scope.cancel = function () {
            ngDialog.close();
        };
    })
;