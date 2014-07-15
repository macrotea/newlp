/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')
    .controller('depotAddDeliveryController', function ($scope, $state, Material, Invoice) {

        $scope.options = {
            fields:{
                orderCount:{
                    editable:true
                },
                deliveryCount:{
                    editable:true
                },
                actions:{
                    editable:true
                }
            }
        };

        //init
        $scope.invoice = {
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

        $scope.options = {
            fields:{
                orderCount:{
                    editable:true
                },
                deliveryCount:{
                    editable:true
                },
                actions:{
                    editable:true
                }
            }
        };

        //init
        $scope.invoice = {
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

        $scope.options = {
            fields:{
                orderCount:{
                    editable:true
                },
                deliveryCount:{
                    editable:true
                },
                actions:{
                    editable:true
                }
            }
        };


        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;

            $scope.success = 70 == invoice.auditStatus;
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

    .controller('depotReceiveController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;

            $scope.success = 60 == invoice.auditStatus;
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

    .controller('depotAdjustController', function ($scope, $state, $stateParams, Material, Invoice) {

        Invoice.get({invoiceId: $stateParams.invoiceId}).$promise.then(function (invoice) {
            $scope.invoice = invoice;

            $scope.success = 70 == invoice.auditStatus || 30 == invoice.auditStatus;
        });

        $scope.submit = function () {
            Invoice.patch({
                invoiceId: $scope.invoice.invoiceId,
                invoiceDetails: $scope.invoice.invoiceDetails,
                auditStatus: $scope.invoice.auditStatus
            }).$promise.then(function (data) {
                    console.log(data);
                    $scope.success = true;
                }, function (data) {
                    $scope.error = true;
                });
        };
    })

    .controller('depotReceivesController', function ($scope, $state, $location, $stateParams, Invoice, ngDialog) {
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
                sendBackController: 'customerServiceConfirmSendBackConfirmCtrl'
            }
        };
    })

    .controller('depotAuditUnauditedController', function ($scope, $state, $location, $stateParams, Invoice, ngDialog) {

        /*init params*/
        $scope.pageSizes=[
            {name: 20,value:20},
            {name: 50,value:50},
            {name: 100,value:100}
        ];
        $scope.pageSize =$scope.pageSizes[0];
        $scope.page ={};
        $scope.page.number=1;
        $scope.page.size=$scope.pageSize.value;
        $scope.searchTerm = {
            auditStatus: ';auditStatuses=50,60'
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
                    auditStatus: ';auditStatuses=50,60'
                },
                function (data) {
                    $scope.data = data;
                    $scope.loading = false;
                });

//            Invoice.queryByAuditStatus({
//                    page: $scope.currentPage - 1,
//                    auditStatus: 50
//                },
//                function (data) {
//                    Invoice.queryByAuditStatus({
//                            page: $scope.currentPage - 1,
//                            auditStatus: 60
//                        },
//                        function (dataTmp) {
//
//                            data.content = data.content.concat(dataTmp.content);
//                            data.page.totalElements = data.page.totalElements + dataTmp.page.totalElements;
//                            data.page.totalPages = data.page.totalPages > dataTmp.page.totalPages ? data.page.totalPages : dataTmp.page.totalPages;
//                            $scope.data = data;
//
//                            $scope.loading = false;
//                        });
//                });

        };


        //submit search
        $scope.search = function () {
            $scope.loading = true;
            if (_.isDate($scope.searchTerm.endDateOfReceived)) {
                $scope.searchTerm.endDateOfReceived = moment($scope.searchTerm.endDateOfReceived).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
            }

            $scope.searchTerm.auditStatus = 50;
            Invoice.search({
                page: $scope.page.number - 1,
                size:$scope.page.size
            }, $scope.searchTerm).$promise.then(function (data) {

                $scope.searchTerm.auditStatus = 60;
                Invoice.search({
                        page: $scope.page.number - 1,
                        size:$scope.page.size
                    }, $scope.searchTerm,
                    function (dataTmp) {

                        data.content = data.content.concat(dataTmp.content);
                        data.page.totalElements = data.page.totalElements + dataTmp.page.totalElements;
                        data.page.totalPages = data.page.totalPages > dataTmp.page.totalPages ? data.page.totalPages : dataTmp.page.totalPages;
                        $scope.data = data;
                        $scope.loading = false;

                    });

            }, function (data) {
                $scope.loading = false;
                $scope.error = true;
            });
        };

        //handle Criteria Query Form action
        $scope.submit = function () {
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

        $scope.edit = function (invoiceId, auditStatus) {
            auditStatus == 50 ? $state.go('home.depot.edit', {invoiceId: invoiceId}) :
                auditStatus == 60 ? $state.go('home.depot.adjust', {invoiceId: invoiceId}) :
                    undefined;
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
                    auditStatus: 70
                },
                actions: {
                    view: function (invoiceId) {
                        $state.go('home.depot.view', {invoiceId: invoiceId});
                    }
                }
            }

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