/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')

    .controller('customerServiceConfirmViewController', function ($scope, $state, $stateParams, $http, Material, Invoice) {
        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.options = {
            readOnly:true
        };
    })

    .controller('customerServiceConfirmReceivesController', function ($scope, $state) {

        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 70
                },
                actions: {
                    edit: function (invoiceId) {
                        $state.go('home.customer_service.confirm.receive', {invoiceId: invoiceId});
                    }
                },
                sendBackController:'customerServiceConfirmSendBackConfirmCtrl'
            }
        };

    })

    .controller('customerServiceConfirmReceiveController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        Invoice.getPreAuditStatusByInvoiceId({invoiceId: $stateParams.invoiceId},function(data) {
            $scope.options.actions.sendBack.auditStatus = data.auditStatus;
        });

        $scope.options = {
            actions:{
                receive:{
                    auditStatus:80
                },
                sendBack:{
                    auditStatus:60
                }
            },
            activeStatus:70,
            readOnly:true
        };
    })

    .controller('customerServiceConfirmEditController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        Invoice.getPreAuditStatusByInvoiceId({invoiceId: $stateParams.invoiceId},function(data) {
            $scope.options.actions.sendBack.auditStatus = data.auditStatus;
        });

        $scope.options = {
            actions:{
                submit:{
                    auditStatus:90
                },
                sendBack:{
                    auditStatus:60
                }
            },
            activeStatus:80,
            readOnly:true
        };

    })


    .controller('customerServiceConfirmAuditUnauditedController', function ($scope, $state, $location, $stateParams, Invoice, ngDialog) {
        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 80
                },
                actions:{
                    edit:function (invoiceId) {
                        $state.go('home.customer_service.confirm.edit', {invoiceId: invoiceId});
                    }
                },
                sendBackController: 'customerServiceConfirmSendBackConfirmCtrl'
            }
        };
    })

    .controller('customerServiceConfirmAuditAuditedController', function ($scope, $state) {
        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 90
                },
                actions:{
                    view:function (invoiceId) {
                        $state.go('home.customer_service.confirm.view', {invoiceId: invoiceId});
                    }
                }
            }
        };
    })

    .controller('customerServiceConfirmSendBackConfirmCtrl', function ($scope, ngDialog, Invoice) {

        var invoiceIdToRemove = $scope.$parent.$parent.invoiceIdToRemove;
        Invoice.getPreAuditStatusByInvoiceId({invoiceId: invoiceIdToRemove},function(data){
            $scope.confirm = function () {
                Invoice.patch({invoiceId: invoiceIdToRemove, auditStatus: data.auditStatus}, function (data) {
                    $scope.$parent.data.content = $scope.$parent.data.content.filter(function (invoice) {
                        return invoice.invoiceId != $scope.$parent.invoiceIdToRemove;
                    });
                    $scope.$parent.invoiceIdToRemove = undefined;
                    $scope.closeThisDialog();
                });
            };
        });


        $scope.cancel = function () {
            ngDialog.close();
        };
    })
;