/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('depotAddDeliveryController', function ($scope) {

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
                deliveryCount:{
                    editable:true
                }
            },
            actions:{
                save:{
                    auditStatus:60
                },
                draft:{
                    auditStatus:50
                }
            },
            activeStatus:0,
            activeInvoiceTypes:[1,3,5],
            isCreatedByDepot:true
        };
    })

    .controller('depotAddRestockController', function ($scope) {

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
                invoiceTypeId: 2
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
                    auditStatus:60
                },
                draft:{
                    auditStatus:50
                }
            },
            activeStatus:0,
            activeInvoiceTypes:[2,4],
            isCreatedByDepot:true
        };

    })

    .controller('depotViewController', function ($scope, $state, $stateParams, Invoice) {
        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.options = {
            readOnly:true
        };
    })

    .controller('depotEditController', function ($scope, $state, $stateParams, Material, Invoice) {

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
                    auditStatus:60
                },
                draft:{
                    auditStatus:50
                }
            },
            activeStatus:50
        };


        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });


    })

    .controller('depotReceiveController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;
        });

        $scope.options = {
            actions:{
                receive:{
                    auditStatus:50
                },
                sendBack:{
                    auditStatus:30
                }
            },
            activeStatus:40,
            readOnly:true
        };
    })

    .controller('depotAdjustController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;

        });

        $scope.options = {
            fields:{
                deliveryCount:{
                    editable:true
                }
            },
            actions:{
                adjust:{
                    auditStatus:70
                },
                sendBack:{
                    auditStatus:30
                }
            },
            activeStatus:50,
            readOnly:true
        };
    })

    .controller('depotReceivesController', function ($scope, $state,$stateParams) {
        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus: 40
                },
                actions: {
                    edit: function (invoiceId) {
                        $state.go('home.depot.receive', {invoiceId: invoiceId})
                    }
                },
                sendBackController: 'depotSendBackConfirmCtrl'
            }
        };
    })

    .controller('depotAuditUnauditedController', function ($scope, $state, $location, $stateParams, Invoice, ngDialog) {

        /*init params*/
        $scope.pageSizes=[
            {name: 20,value:20},
            {name: 50,value:50},
            {name: 100,value:100},
            {name: 5,value:5}
        ];
        $scope.pageSize =$scope.pageSizes[0];
        $scope.page ={};
        $scope.page.number=1;
        $scope.page.size=$scope.pageSize.value;

        $scope.daterangeOptions = {
            locale: {
                fromLabel:'开始日期',
                toLabel:'结束日期',
                applyLabel:'确定',
                cancelLabel: '取消'
            }
        };
        $scope.showDateRange = function (e) {
            var datePickerElem = angular.element(e.currentTarget).siblings('.date-picker');
            if(datePickerElem.length > 0){
                e.preventDefault();
                e.stopPropagation();
                datePickerElem.trigger('click')
            }
        };

        $scope.onPageSizeChange = function () {
            $scope.page.size = $scope.pageSize.value;
            $scope.onPageChanged();
        };


        $scope.queryByAuditStatus = function () {
            $scope.loading = true;
            Invoice.queryByAuditStatus({
                    page: $scope.page.number - 1,
                    size:$scope.page.size,
                    auditStatus: 50
                },
                function (data) {
                    $scope.data = data;
                    $scope.loading = false;
                });

        };


        //submit search
        $scope.search = function () {
            $scope.loading = true;

            $scope.searchTerm.auditStatus = 50;
            Invoice.search({
                page: $scope.page.number - 1,
                size:$scope.page.size
            }, $scope.searchTerm).$promise.then(function (data) {
                        $scope.data = data;
                        $scope.loading = false;

            }, function (data) {
                $scope.loading = false;
                $scope.error = true;
            });
        };

        //handle Criteria Query Form action
        $scope.submit = function () {
                $scope.onPageChanged = $scope.search;
                $scope.onPageChanged();
        };

        $scope.rest = function(){
            $scope.reset();
        };

        //reset
        $scope.reset = function () {
            $scope.onPageChanged = $scope.queryByAuditStatus;

            //search criteria
            $scope.searchTerm = {
                auditStatus: 50,
                receivedDateRange: {
                    startDate:  moment().startOf('day').format(),
                    endDate:  moment().endOf('day').format()
                }
            };
            $scope.onPageChanged();
        };

        $scope.edit = function (invoiceId, isCreatedByDepot) {
            isCreatedByDepot ? $state.go('home.depot.edit', {invoiceId: invoiceId}) : $state.go('home.depot.adjust', {invoiceId: invoiceId});
        };

        $scope.sendBack = function (invoiceId) {
            $scope.invoiceIdToRemove = invoiceId;
            ngDialog.open({
                template: 'views/invoice.sendBack.html',
                className: 'ngdialog-theme-plain',
                controller: 'depotSendBackConfirmCtrl',
                scope: $scope
            });
        };

        $scope.remove = function (invoiceId) {
            $scope.invoiceIdToRemove = invoiceId;
            ngDialog.open({
                template: 'views/invoice.remove.html',
                className: 'ngdialog-theme-plain',
                controller: 'customerServiceOrderRemoveConfirmCtrl',
                scope: $scope
            });
        };

        $scope.reset();


    })

    .controller('depotAuditAuditedController', function ($scope, $state) {
        $scope.searchForm = {
            options: {
                criteria: {
                    auditStatus:'60,70,80,90'
                },
                actions: {
                    view: function (invoiceId) {
                        $state.go('home.depot.view', {invoiceId: invoiceId});
                    }
                },
                sendBackController:'depotAuditedSendBackConfirmCtrl'
            }

        };
    })

    .controller('depotSendBackConfirmCtrl', function ($scope, ngDialog, Invoice) {

        $scope.confirm = function () {
            var invoiceId = this.invoiceIdToRemove;
            Invoice.getSendBackAuditStatus({invoiceId: this.invoiceIdToRemove}, function (data) {

                /*bad code, api should return correct auditStatus*/
                if(20 == data.auditStatus){
                    Invoice.patch({invoiceId: invoiceId, auditStatus: 20}, function (data) {
                        $scope.$parent.data.content = $scope.$parent.data.content.filter(function (invoice) {
                            return invoice.invoiceId != $scope.$parent.invoiceIdToRemove;
                        });
                        $scope.$parent.invoiceIdToRemove = undefined;
                        $scope.closeThisDialog();
                    });
                }else{
                    Invoice.patch({invoiceId: invoiceId, auditStatus: 30}, function (data) {
                        $scope.$parent.data.content = $scope.$parent.data.content.filter(function (invoice) {
                            return invoice.invoiceId != $scope.$parent.invoiceIdToRemove;
                        });
                        $scope.$parent.invoiceIdToRemove = undefined;
                        $scope.closeThisDialog();
                    });
                }
            });


        };

        $scope.cancel = function () {
            ngDialog.close();
        };
    })

    .controller('depotAuditedSendBackConfirmCtrl', function ($scope, ngDialog, Invoice) {

        $scope.confirm = function () {
            var invoiceId = this.invoiceIdToRemove;
            Invoice.patch({invoiceId: invoiceId, auditStatus: 50}, function (data) {
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