/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('customerServiceOrderAddController', function ($scope,$stateParams) {

        $scope.$stateParams = $stateParams;

        //init
        $scope.invoice = {
            inc: null,
            client: null,
            carNum: '',
            shift: '',
            clientAddress: '',
            receivedDate: moment().format('YYYY-MM-DD'),
            remark: '',
            auditStatus: '0',
            invoiceType: {
                invoiceTypeId: 1
            },
            invoiceDetails: []
        };

        $scope.options = {
            fields:{
                invoiceNum:{
                    hidden:true
                },
                inc:{
                    editable:true
                },
                client:{
                    editable:true
                },
                invoiceType:{
                    editable:true
                },
                receivedDate:{
                    editable:true
                },
                carNum:{
                    editable:true
                },
                shift:{
                    editable:true
                },
                clientAddress:{
                    editable:true
                },
                remark:{
                    editable:true
                },
                orderCount:{
                    editable:true
                }
            },
            actions:{
                save:{
                    auditStatus:40
                },
                draft:{
                    auditStatus:30
                }
            },
            activeStatus:0,
            activeInvoiceTypes:[1,3]
        };
    })

    .controller('customerServiceOrderViewController', function ($scope, $state, $stateParams, Invoice) {
        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.options = {
            readOnly:true
        };
    })

    .controller('customerServiceOrderReceiveController', function ($scope, $state, $stateParams, Invoice) {

        $scope.options = {
            fields:{
//                inc:{
//                    editable:true
//                },
//                client:{
//                    editable:true
//                },
//                invoiceType:{
//                    editable:true
//                },
//                receivedDate:{
//                    editable:true
//                },
                carNum:{
                    editable:true
                },
                shift:{
                    editable:true
                },
                clientAddress:{
                    editable:true
                },
                remark:{
                    editable:true
                }
//                orderCount:{
//                    editable:true
//                }
            },
            actions:{
                receive:{
                    auditStatus:40
                }
            },
            activeStatus:20,
            activeInvoiceTypes:[1,3],
            readOnly:true
        };

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });
    })

    .controller('customerServiceOrderEditController', function ($scope, $state, $stateParams, Invoice) {

        $scope.options = {
            fields:{
                inc:{
                    editable:true
                },
                client:{
                    editable:true
                },
                invoiceType:{
                    editable:true
                },
                receivedDate:{
                    editable:true
                },
                carNum:{
                    editable:true
                },
                shift:{
                    editable:true
                },
                clientAddress:{
                    editable:true
                },
                remark:{
                    editable:true
                },
                orderCount:{
                    editable:true
                }
            },
            actions:{
                update:{
                    auditStatus:40
                },
                draft:{
                    auditStatus:30
                }
            },
            activeStatus:30,
            activeInvoiceTypes:[1,3]
        };

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });
    })

    .controller('customerServiceOrderDraftsController', function ($scope, $state) {
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
                },
                sendBackController: 'orderSendBackConfirmCtrl'
            }
        };

    })

    .controller('customerServiceOrderSubmittedController', function ($scope, $state) {

        $scope.searchForm = {
            options: {
                criteria: {
                    auditedStatus:40
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


    .controller('orderSendBackConfirmCtrl', function ($scope, ngDialog, Invoice) {

        $scope.confirm = function () {
            var invoiceId = this.invoiceIdToRemove;
            Invoice.patch({invoiceId: invoiceId, auditStatus: 10}, function (data) {
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