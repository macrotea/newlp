/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('depotAddDeliveryController', function ($scope, $state, Material, Invoice) {

        //init
        $scope.invoice = {
            num: '',
            inc: null,
            client: null,
            carNum: '',
            clientAddress: '',
            receivedDate: new Date(),
            remark: '',
            auditStatus: '50',
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
            })
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
    .controller('depotAddRestockController', function ($scope, $state, Material, Invoice) {

        //init
        $scope.invoice = {
            num: '',
            inc: null,
            client: null,
            carNum: '',
            clientAddress: '',
            receivedDate: new Date(),
            remark: '',
            auditStatus: '50',
            invoiceType: {
                invoiceTypeId: '2'
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
            })
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
    .controller('depotEditController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
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

        $scope.add = function (materialNum) {
            if (selectedNums.indexOf(materialNum) < 0) {
                $scope.materialSearchData._embedded.materials.forEach(function (material) {
                    if (materialNum == material.materialNum) {
                        selectedNums.push(materialNum);
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
                })
            }
        };

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

        $scope.submit = function () {
            Invoice.update({}, $scope.invoice).$promise.then(function (data) {
                console.log(data);
                $scope.success = true;
            }, function (data) {
                $scope.error = true;
            });
        };
    })

    .controller('depotReceivesController', function ($scope, $state, $location, $stateParams, Invoice, ngDialog) {

        $scope.queryByAuditStatus = function () {
            $scope.loading = true;
            Invoice.queryByAuditStatus({
                    page: $scope.currentPage - 1,
                    sort: [
                        'receivedDate,desc'
                    ],
                    auditStatus: 40
                },
                function (data) {
                    $scope.loading = false;
                    $scope.data = data;
                });
        };

        //submit search
        $scope.search = function () {
            $scope.loading = true;
            if (_.isDate($scope.searchTerm.endDateOfReceived)) {
                $scope.searchTerm.endDateOfReceived = moment($scope.searchTerm.endDateOfReceived).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
            }

            Invoice.search({page: $scope.currentPage - 1}, $scope.searchTerm).$promise.then(function (data) {
                $scope.loading = false;
                $scope.data = data;
            }, function (data) {
                $scope.loading = false;
                $scope.error = true;
            });
        };

        //handle Criteria Query Form action
        $scope.submit = function () {
            $scope.currentPage = 1;
            if ($scope.action == 'RESET') {
                $scope.reset();
            }

            if ($scope.action == 'SUBMIT') {
                $scope.onPageChanged = $scope.search;
                $scope.onPageChanged();
            }
        };

        //reset
        $scope.reset = function () {
            $scope.onPageChanged = $scope.queryByAuditStatus;

            //search criteria
            $scope.searchTerm = {
                num: null,
                incId: null,
                clientId: null,
                carNum: null,
                startDateOfReceived: null,
                endDateOfReceived: null,
                invoiceTypeId: null,
                auditStatus: 40
            };
            $scope.onPageChanged();
        };

        $scope.edit = function (invoiceId) {
            $state.go('home.depot.edit', {invoiceId: invoiceId});
        };

        $scope.sendBack = function (invoiceId) {
            $scope.invoiceIdToRemove = invoiceId;
            ngDialog.open({
                template: 'views/depot.sendBack.html',
                className: 'ngdialog-theme-plain',
                controller: 'depotSendBackConfirmCtrl',
                scope: $scope
            });
        };

        $scope.reset();

    })

    .controller('depotAuditUnauditedController', function ($scope, $state, $location, $stateParams, Invoice) {

        $scope.queryByAuditStatus = function () {
            $scope.loading = true;
            Invoice.queryByAuditStatus({
                    page: $scope.currentPage - 1,
                    sort: [
                        'receivedDate,desc'
                    ],
                    auditStatus: 50
                },
                function (data) {
                    $scope.loading = false;
                    $scope.data = data;
                });
        };

        //submit search
        $scope.search = function () {
            $scope.loading = true;
            if (_.isDate($scope.searchTerm.endDateOfReceived)) {
                $scope.searchTerm.endDateOfReceived = moment($scope.searchTerm.endDateOfReceived).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
            }

            Invoice.search({page: $scope.currentPage - 1}, $scope.searchTerm).$promise.then(function (data) {
                $scope.loading = false;
                $scope.data = data;
            }, function (data) {
                $scope.loading = false;
                $scope.error = true;
            });
        };

        //handle Criteria Query Form action
        $scope.submit = function () {
            $scope.currentPage = 1;
            if ($scope.action == 'RESET') {
                $scope.reset();
            }

            if ($scope.action == 'SUBMIT') {
                $scope.onPageChanged = $scope.search;
                $scope.onPageChanged();
            }
        };

        //reset
        $scope.reset = function () {
            $scope.onPageChanged = $scope.queryByAuditStatus;

            //search criteria
            $scope.searchTerm = {
                num: null,
                incId: null,
                clientId: null,
                carNum: null,
                startDateOfReceived: null,
                endDateOfReceived: null,
                invoiceTypeId: null,
                auditStatus: 50
            };
            $scope.onPageChanged();
        };

        $scope.edit = function (invoiceId) {
            $state.go('home.depot.edit', {invoiceId: invoiceId});
        };

        $scope.sendBack = function (invoiceId) {
            $scope.invoiceIdToRemove = invoiceId;
            ngDialog.open({
                template: 'views/depot.sendBack.html',
                className: 'ngdialog-theme-plain',
                controller: 'depotSendBackConfirmCtrl',
                scope: $scope
            });
        };

        $scope.reset();


    })

    .controller('depotAuditAuditedController', function ($scope, $state, $location, $stateParams, Invoice) {

        $scope.queryByAuditStatus = function () {
            $scope.loading = true;
            Invoice.queryByAuditStatus({
                    page: $scope.currentPage - 1,
                    sort: [
                        'receivedDate,desc'
                    ],
                    auditStatus: 70
                },
                function (data) {
                    $scope.loading = false;
                    $scope.data = data;
                });
        };

        //submit search
        $scope.search = function () {
            $scope.loading = true;
            if (_.isDate($scope.searchTerm.endDateOfReceived)) {
                $scope.searchTerm.endDateOfReceived = moment($scope.searchTerm.endDateOfReceived).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
            }

            Invoice.search({page: $scope.currentPage - 1}, $scope.searchTerm).$promise.then(function (data) {
                $scope.loading = false;
                $scope.data = data;
            }, function (data) {
                $scope.loading = false;
                $scope.error = true;
            });
        };

        //handle Criteria Query Form action
        $scope.submit = function () {
            $scope.currentPage = 1;
            if ($scope.action == 'RESET') {
                $scope.reset();
            }

            if ($scope.action == 'SUBMIT') {
                $scope.onPageChanged = $scope.search;
                $scope.onPageChanged();
            }
        };

        //reset
        $scope.reset = function () {
            $scope.onPageChanged = $scope.queryByAuditStatus;

            //search criteria
            $scope.searchTerm = {
                num: null,
                incId: null,
                clientId: null,
                carNum: null,
                startDateOfReceived: null,
                endDateOfReceived: null,
                invoiceTypeId: null,
                auditStatus: 70
            };
            $scope.onPageChanged();
        };

        $scope.view = function (invoiceId) {
            $state.go('home.depot.view', {invoiceId: invoiceId});
        };

        $scope.reset();


    })

    .controller('depotSubmittedController', function ($scope, $state, $location, $stateParams, Invoice) {
        var requestParams = $location.search();
        $scope.loading = true;

        //handle page selection
        $scope.onPageChanged = function () {
            $scope.loading = true;
            Invoice.queryByAuditStatus({
                    page: $scope.currentPage - 1,
                    auditStatus: 40
                },
                function (data) {
                    $scope.loading = false;
                    $scope.data = data;

                })
        };

        //load first page
        $scope.currentPage = 1;
        $scope.onPageChanged();

        $scope.edit = function (invoiceId) {
            $state.go('home.depot.edit', {invoiceId: invoiceId});
        };

    })

    .controller('depotSendBackConfirmCtrl', function ($scope, ngDialog, Invoice) {

        $scope.confirm = function () {
            Invoice.patch({invoiceId: this.invoiceIdToRemove, auditStatus: 30}, function (data) {
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