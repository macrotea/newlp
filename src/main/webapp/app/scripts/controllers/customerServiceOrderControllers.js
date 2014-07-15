/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('customerServiceOrderAddController', function ($scope, $state, $http, Material, Invoice) {

        $scope.options = {
            fields:{
                orderCount:{
                    editable:true
                },
                actions:{
                    editable:true
                }
            }
        };

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

        $scope.options = {
            fields:{
                orderCount:{
                    editable:true
                },
                actions:{
                    editable:true
                }
            }
        };


        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
            $scope.success = 40 == invoice.auditStatus;
        });

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