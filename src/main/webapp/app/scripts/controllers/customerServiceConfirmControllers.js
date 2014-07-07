/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')

    .controller('customerServiceConfirmViewController', function ($scope, $state, $stateParams, $http, Material, Invoice) {
        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });
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
                }
            }
        };

    })

    .controller('customerServiceConfirmReceiveController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.submit = function () {
            Invoice.patch({
                invoiceId: $scope.invoice.invoiceId,
                auditStatus: $scope.invoice.auditStatus
            }).$promise.then(function (data) {
                    console.log(data);
                    $scope.success = true;
                }, function (data) {
                    $scope.error = true;
                });
        };
    })

    .controller('customerServiceConfirmEditController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.submit = function () {
            Invoice.patch({
                invoiceId: $scope.invoice.invoiceId,
                auditStatus: $scope.invoice.auditStatus
            }).$promise.then(function (data) {
                    console.log(data);
                    $scope.success = true;
                }, function (data) {
                    $scope.error = true;
                });
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
                }
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
;