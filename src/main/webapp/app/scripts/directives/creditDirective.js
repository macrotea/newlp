/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "creditManagementForm": function () {
            return {
                templateUrl: 'templates/credit.management.form.html',
                restrict: 'AE',
                scope: {
                    options: '=options'
                },
                controller: function ($scope, $state, Credit, ngDialog) {

                    /*init actions*/
                    $scope.edit = $scope.options.actions.edit;
                    $scope.sendBack = $scope.options.actions.sendBack;
                    $scope.view = $scope.options.actions.view;
                    $scope.rowDblClick =
                        $scope.options.actions.rowDblClick ? $scope.options.actions.rowDblClick :
                            $scope.options.actions.edit ? $scope.options.actions.edit :
                                $scope.options.actions.view ? $scope.options.actions.view : undefined
                    ;

                    /*init params*/
                    $scope.pageSizes = [
                        {name: 20, value: 20},
                        {name: 50, value: 50},
                        {name: 100, value: 100}
                    ];
                    $scope.pageSize = $scope.pageSizes[0];
                    $scope.page = {};
                    $scope.page.number = 1;
                    $scope.page.size = $scope.pageSize.value;
                    $scope.searchTerm = {
                        auditStatus: $scope.options.criteria.auditStatus
                    };

                    //default search
                    $scope.queryAll = function () {
                        $scope.loading = true;
                        Credit.queryAll({
                                page: $scope.page.number - 1,
                                size: $scope.page.size
                            },
                            function (data) {
                                $scope.data = data;
                                $scope.originalContent = angular.copy(data.content);
                                $scope.loading = false;
                            });
                    };

                    //criteria search
                    $scope.search = function () {
                        if (_.isDate($scope.searchTerm.expiryDate)) {
                            $scope.searchTerm.expiryDate = moment($scope.searchTerm.expiryDate).add('hours', 23).add('minutes', 59).add('seconds', 59).format();
                        }

                        Credit.search({
                                page: $scope.page.number - 1,
                                size: $scope.page.size
                            }, $scope.searchTerm
                        ).$promise.then(function (data) {
                                $scope.data = data;
                                $scope.originalContent = angular.copy(data.content);
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

                    var rollback = function (credit, $index) {
                        $scope.originalContent.forEach(function (o) {
                            if (o.creditId == credit.creditId) {
                                for (var i = 0; i < $scope.data.content.length; ++i) {
                                    if (o.creditId == $scope.data.content[i].creditId) {
                                        $scope.data.content[i] = angular.copy(o);
                                    }
                                }
                            }
                        });
                    };

                    $scope.update = function (credit, $index) {
                        if (credit.creditId) {
                            Credit.patch({
                                creditId: credit.creditId
                            }, credit).$promise.then(function (data) {
                                    console.log(data);
                                    $scope.success = true;
                                }, function (data) {
                                    rollback(credit.creditId);
                                    $scope.error = true;
                                });
                        } else {
                            Credit.save({}, credit).$promise.then(function (data) {
                                console.log(data);
                                $scope.data.content[$index] = data;
                                $scope.success = true;
                            }, function (data) {
                                rollback(credit.creditId);
                                $scope.error = true;
                            });
                        }

                    };

                    $scope.cancel = function (credit, $index) {
                        if (credit.creditId) {
                            rollback(credit, $index);
                        } else {
                           $scope.data.content.splice($index,1);
                        }
                    };

                    $scope.add = function () {

                        $scope.data.content[$scope.data.content.length] = {
                            "type": 0,
                            "amount": 0,
                            "percent": 0.1,
                            "creditAmount": 0,
                            "insertDate": moment().format(),
                            "validDate": moment().format(),
                            "expiryDate": moment().format(),
                            "description": '',
                            "client": {
                                "clientId": undefined
                            },
                            editable: true
                        };
                    };

                    $scope.remove = function (creditId) {
                        $scope.creditIdToRemove = creditId;
                        ngDialog.open({
                            template: 'views/credit.management.remove.html',
                            className: 'ngdialog-theme-plain',
                            controller: 'creditManagementRemoveConfirmCtrl',
                            scope: $scope
                        });
                    };


                    $scope.onPageSizeChange = function () {
                        $scope.page.size = $scope.pageSize.value;
                        $scope.onPageChanged();
                    };

                    //reset
                    $scope.reset = function () {
                        $scope.loading = true;

                        $scope.onPageChanged = $scope.search;

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

