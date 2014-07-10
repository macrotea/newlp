/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "invoiceSearchForm": function () {
            return {
                templateUrl: 'templates/invoice.search.form.html',
                restrict: 'AE',
                transclude: true,
                scope: {
                    options: '=options'
                },
                controller: function ($scope, $state,  Invoice,ngDialog) {

                    /*init actions*/
                    $scope.edit = $scope.options.actions.edit;
                    $scope.view = $scope.options.actions.view;
                    $scope.rowDblClick =
                        $scope.options.actions.rowDblClick?$scope.options.actions.rowDblClick:
                        $scope.options.actions.edit?$scope.options.actions.edit:
                        $scope.options.actions.view?$scope.options.actions.view:undefined
                    ;

                    $scope.enableRemove = $scope.options.removeController;
                    $scope.remove = function (invoiceId) {
                        $scope.invoiceIdToRemove = invoiceId;
                        ngDialog.open({
                            template: 'views/invoice.remove.html',
                            className: 'ngdialog-theme-plain',
                            controller: $scope.options.removeController,
                            scope: $scope
                        });
                    };

                    $scope.enableSendBack = $scope.options.sendBackController;
                    $scope.sendBack = function (invoiceId) {
                        $scope.invoiceIdToRemove = invoiceId;
                        ngDialog.open({
                            template: 'views/invoice.sendBack.html',
                            className: 'ngdialog-theme-plain',
                            controller: $scope.options.sendBackController,
                            scope: $scope
                        });
                    };

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
                        auditStatus: $scope.options.criteria.auditStatus
                    };

                    //default search
                    $scope.queryByAuditStatus = function () {
                        $scope.loading = true;
                        Invoice.queryByAuditStatus({
                                page: $scope.page.number - 1,
                                size:$scope.page.size,
                                auditStatus: $scope.options.criteria.auditStatus
                            },
                            function (data) {
                                $scope.data = data;
                                $scope.loading = false;
                            });
                    };

                    //criteria search
                    $scope.search = function () {
                        if (_.isDate($scope.searchTerm.endDateOfReceived)) {
                            $scope.searchTerm.endDateOfReceived = moment($scope.searchTerm.endDateOfReceived).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
                        }

                        Invoice.search({
                            page: $scope.page.number - 1,
                            size:$scope.page.size
                            }, $scope.searchTerm
                        ).$promise.then(function (data) {
                            $scope.data = data;
                            $scope.loading = false;
                        }, function (data) {
                            $scope.loading = false;
                            $scope.error = true;
                        });
                    };

                    //handle form action
                    $scope.submit = function () {
                        $scope.loading = true;
                        if ($scope.action == 'RESET') {
                            $scope.reset();
                        }

                        if ($scope.action == 'SUBMIT') {
                            $scope.onPageChanged = $scope.search;
                            $scope.onPageChanged();
                        }
                    };

                    $scope.onPageSizeChange = function () {
                        $scope.page.size = $scope.pageSize.value;;
                        $scope.onPageChanged();
                    };

                    //reset
                    $scope.reset = function () {
                        $scope.onPageChanged = $scope.queryByAuditStatus;

                        //reset criteria
                        $scope.searchTerm = {
                            auditStatus: $scope.options.criteria.auditStatus
                        };
                        $scope.onPageChanged();
                    };

                    $scope.reset();


                },
                link: function (scope, element, attrs, ctrl) {
                }
            };
        }
    })
;

