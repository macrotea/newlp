/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "creditManagementForm": function () {
            return {
                templateUrl: 'templates/credit.datatable.html',
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
                    var tmpSeqForNewCredit = 0;


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

                    $scope.save = function (credit) {

                        var creditUpdated = angular.copy(credit);
                        creditUpdated.creditId = Math.round(credit.creditId);
                        creditUpdated.validDate= credit.validDateRange.startDate;
                        creditUpdated.expiryDate=credit.validDateRange.endDate;

                        if(Math.round(credit.creditId)){

                            /*find creditTmp idx*/
                            var idx = undefined;
                            for (var i = 0; i < $scope.data.content.length; i++) {
                                var obj = $scope.data.content[i];
                                if(obj.creditId == creditUpdated.creditId){
                                    idx = i;
                                    break;
                                }
                            }

                            /*update credit*/
                            Credit.update({
                                creditId: creditUpdated.creditId
                            }, creditUpdated).$promise.then(function (data) {
                                    credit.editable = false;/*hide edit*/
                                    data.validDateRange = credit.validDateRange;
                                    $scope.data.content[idx] = data;
                                    $scope.success = true;
                                }, function (data) {
                                    $scope.error = true;
                                });
                        }else{

                            /*save credit*/
                            creditUpdated.creditId = undefined;
                            Credit.save({}, creditUpdated).$promise.then(function (data) {
                                credit.editable = false;/*hide edit*/
                                credit.creditId=data.creditId - 0.00001;
                                tmpSeqForNewCredit = tmpSeqForNewCredit -0.00001;
                                data.validDateRange = {
                                    startDate: data.validDate,
                                    endDate:data.expiryDate
                                };
                                $scope.data.content.push(data);
                                ++$scope.data.page.totalElements;

                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                        }

                    };

                    $scope.edit = function (credit) {
                        var tmpCreditId = credit.creditId - 0.00001;
                        toggleEditable(tmpCreditId);
                    };

                    $scope.cancel = function(credit){
                        toggleEditable(credit.creditId);
                    };

                    $scope.add = function () {
                        var currentDate = moment().format();
                        $scope.data.content[$scope.data.content.length] = {
                            'creditId':tmpSeqForNewCredit,
                            "type": 0,
                            "amount": 0,
                            "percent": 0.1,
                            "creditAmount": 0,
                            "insertDate": currentDate,
                            "validDate": currentDate,
                            "expiryDate": currentDate,
                            validDateRange:{
                                startDate: currentDate,
                                endDate:currentDate
                            },
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


                    var toggleEditable = function (creditId) {
                        for (var i = 0; i < $scope.data.content.length; i++) {
                            var obj = $scope.data.content[i];
                            if(obj.creditId == creditId){
                                obj.editable = !obj.editable;
                                break;
                            }
                        }
                    };


                    //criteria search
                    $scope.search = function () {

                        Credit.search({
                                page: $scope.page.number - 1,
                                size: $scope.page.size
                            }, $scope.searchTerm
                        ).$promise.then(function (data) {
                                $scope.data = data;

                                var i =0;
                                var len = $scope.data.content.length;
                                for (; i < len; i++) {
                                    var obj = angular.copy($scope.data.content[i]);
                                    obj.creditId = obj.creditId -0.00001;
                                    obj.editable =false;
                                    obj.validDateRange={
                                        startDate:obj.validDate,
                                        endDate:obj.expiryDate
                                    };
                                    $scope.data.content.push(obj);
                                }

                                $scope.loading = false;
                            }, function (data) {
                                $scope.loading = false;
                                $scope.error = true;
                            });
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

