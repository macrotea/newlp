/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "invoiceDatatable": function () {
            return {
                templateUrl: 'templates/invoice.datatable.html',
                restrict: 'AE',
                transclude: true,
                scope: {
                    options: '=options'
                },
                controller: function ($scope, $state, Invoice, ngDialog) {

                    /*init actions*/
                    $scope.edit = $scope.options.actions.edit;
                    $scope.view = $scope.options.actions.view;
                    $scope.rowDblClick =
                        $scope.options.actions.rowDblClick ? $scope.options.actions.rowDblClick :
                            $scope.options.actions.edit ? $scope.options.actions.edit :
                                $scope.options.actions.view ? $scope.options.actions.view : undefined
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
                    $scope.pageSizes = [
                        {name: 20, value: 20},
                        {name: 50, value: 50},
                        {name: 100, value: 100},
                        {name: 5,value:5}
                    ];
                    $scope.pageSize = $scope.pageSizes[0];
                    $scope.page = {};
                    $scope.page.number = 1;
                    $scope.page.size = $scope.pageSize.value;

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


                    //default search
                    $scope.queryByAuditStatus = function () {
                        $scope.loading = true;

                        if($scope.options.criteria.auditedStatus){
                            Invoice.queryByOriginalAuditStatus({
                                    page: $scope.page.number - 1,
                                    size: $scope.page.size,
                                    auditedStatus: $scope.options.criteria.auditedStatus
                                },
                                function (data) {
                                    $scope.data = data;
                                    $scope.loading = false;
                                });
                        }else{
                            Invoice.queryByAuditStatus({
                                    page: $scope.page.number - 1,
                                    size: $scope.page.size,
                                    auditStatus: $scope.options.criteria.auditStatus
                                },
                                function (data) {
                                    $scope.data = data;
                                    $scope.loading = false;
                                });
                        }


                    };

                    //criteria search
                    $scope.search = function () {
                        if($scope.options.criteria.auditedStatus) {
                            Invoice.searchByOriginalAuditStatus({
                                    page: $scope.page.number - 1,
                                    size: $scope.page.size,
                                    auditedStatus:$scope.options.criteria.auditedStatus
                                }, $scope.searchTerm
                            ).$promise.then(function (data) {
                                    $scope.data = data;
                                    $scope.loading = false;
                                }, function (data) {
                                    $scope.loading = false;
                                    $scope.error = true;
                                });
                        }else{
                            Invoice.search({
                                    page: $scope.page.number - 1,
                                    size: $scope.page.size
                                }, $scope.searchTerm
                            ).$promise.then(function (data) {
                                    $scope.data = data;
                                    $scope.loading = false;
                                }, function (data) {
                                    $scope.loading = false;
                                    $scope.error = true;
                                });
                        }

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
                        $scope.page.size = $scope.pageSize.value;
                        $scope.onPageChanged();
                    };

                    //reset
                    $scope.reset = function () {
                        $scope.onPageChanged = $scope.queryByAuditStatus;

                        //reset criteria
                        $scope.searchTerm = {
                            auditStatus: $scope.options.criteria.auditStatus,
                            receivedDateRange: {
                                startDate:  moment().startOf('day').format(),
                                endDate:  moment().endOf('day').format()
                            }
                        };
                        $scope.onPageChanged();
                    };

                    $scope.reset();


                },
                link: function (scope, element, attrs, ctrl) {
                }
            };
        },
        "invoiceDetails": function () {
            return {
                templateUrl: 'templates/invoice.details.html',
                restrict: 'AE',
//                transclude: true,
                scope: {
                    invoiceDetails: '=details',
                    readOnly: '=?readOnly',
                    options: '=?options'
                },
                controller: function ($scope, Material) {


                    $scope.options = $scope.options ? $scope.options : {};
                    $scope.options.fields = $scope.options.fields ? $scope.options.fields : {};
                    $scope.options.actions = $scope.options.actions ? $scope.options.actions : {};
                    $scope.fields = $scope.options.fields ? $scope.options.fields : {};
                    $scope.fields.orderCount = $scope.options.fields.orderCount ? $scope.options.fields.orderCount : {};
                    $scope.fields.deliveryCount = $scope.options.fields.deliveryCount ? $scope.options.fields.deliveryCount : {};
                    $scope.fields.orderCount = $scope.options.fields.orderCount ? $scope.options.fields.orderCount : {};
                    $scope.fields.remark = $scope.options.fields.remark ? $scope.options.fields.remark : {};

                    $scope.readOnly = $scope.options.readOnly ? $scope.options.readOnly : false;
//                    $scope.fields.actions = $scope.options.fields.actions?$scope.options.fields.actions:{};


                    //init temp variables
                    $scope.data = undefined;
                    $scope.searchTerm = '';
                    $scope.currentPage = 1;


                    //search materials
                    $scope.search = function () {
                        $scope.loading = true;

                        Material.findByNameOrNumLike({
                            page: $scope.currentPage - 1,
                            size: 10,
                            searchTerm: $scope.searchTerm
                        }, {}, function (data) {
                            $scope.loading = false;
                            $scope.data = data;
                        });
                    };
                    $scope.onPageChanged = $scope.search;


                    //add to selected list
                    $scope.add = function (material) {

                        var notExist = true;
                        $scope.invoiceDetails.forEach(function (invoiceDetail) {
                            if (material.materialNum == invoiceDetail.material.materialNum) {
                                notExist = false;
                            }
                        });

                        if (notExist && 20 > $scope.invoiceDetails.length) {
                            var invoiceDetail = {};
                            invoiceDetail.orderCount = 1;
                            invoiceDetail.deliveryCount = invoiceDetail.orderCount;
                            invoiceDetail.remark = '明细备注';
                            invoiceDetail.unit = material.unit;
                            invoiceDetail.auxiliaryUnitOne = material.auxiliaryUnitOne;
                            invoiceDetail.auxiliaryUnitTwo = material.auxiliaryUnitTwo;
                            invoiceDetail.conversionRateOne = material.conversionRateOne;
                            invoiceDetail.conversionRateTwo = material.conversionRateTwo;
                            invoiceDetail.price = material.price;
                            invoiceDetail.material = material;
                            $scope.invoiceDetails.push(invoiceDetail);
                        }

                    };

                    //remove to selected list
                    $scope.remove = function (materialNum) {

                        var isExist = false;
                        $scope.invoiceDetails.forEach(function (invoiceDetail) {
                            if (materialNum == invoiceDetail.material.materialNum) {
                                isExist = true;
                            }
                        });

                        if (isExist) {
                            $scope.invoiceDetails = $scope.invoiceDetails.filter(function (invoiceDetail) {
                                return materialNum != invoiceDetail.material.materialNum;
                            });
                        }
                    };
                },
                link: function (scope, element, attrs, ctrl) {
                }
            };
        },
        "invoiceForm": function () {
            return {
                templateUrl: 'templates/invoice.form.html',
                restrict: 'AE',
//                transclude: true,
                scope: {
                    invoice: '=invoice',
                    readOnly: '=?readOnly',
                    options: '=?options'
                },
                controller: function ($scope, Invoice) {

                    $scope.options = $scope.options ? $scope.options : {};
                    $scope.options.fields = $scope.options.fields ? $scope.options.fields : {};
                    $scope.options.actions = $scope.options.actions ? $scope.options.actions : {};
                    $scope.fields = $scope.options.fields ? $scope.options.fields : {};
                    $scope.fields.inc = $scope.fields.inc ? $scope.fields.inc : {};
                    $scope.fields.client = $scope.fields.client ? $scope.fields.client : {};
                    $scope.fields.invoiceType = $scope.fields.invoiceType ? $scope.fields.invoiceType : {};
                    $scope.fields.receivedDate = $scope.fields.receivedDate ? $scope.fields.receivedDate : {};
                    $scope.fields.carNum = $scope.fields.carNum ? $scope.fields.carNum : {};
                    $scope.fields.clientAddress = $scope.fields.clientAddress ? $scope.fields.clientAddress : {};
                    $scope.fields.remark = $scope.fields.remark ? $scope.fields.remark : {};
                    $scope.fields.orderCount = $scope.fields.orderCount ? $scope.fields.orderCount : {};

                    $scope.actions = $scope.options.actions ? $scope.options.actions : {};
//                    $scope.actions.submit = $scope.options.actions.submit?$scope.options.actions.submit:{};
//                    $scope.actions.draft = $scope.options.actions.draft?$scope.options.actions.draft:{};


                    $scope.invoiceTypes = [
                        {"invoiceTypeId":1, "name":'销售'},
                        { "invoiceTypeId":2, "name":'外购'},
                        {"invoiceTypeId":3, "name":'自用'}
                    ];

                    if($scope.options.activeInvoiceTypes){
                        $scope.invoiceTypes = $scope.invoiceTypes.filter(function (invoiceType) {
                            return $scope.options.activeInvoiceTypes.indexOf(invoiceType.invoiceTypeId) > -1;
                        });
                    }


                    /*popup datepicker*/
                    $scope.receivedDateOpen = function ($event) {
                        $event.preventDefault();
                        $event.stopPropagation();

                        $scope.receivedDateOpened = true;
                    };


                    $scope.save = function () {
                        $scope.invoice.auditStatus = $scope.actions.save.auditStatus;
                        Invoice.save({}, $scope.invoice).$promise.then(function (data) {
                            console.log(data);
                            $scope.success = true;
                        }, function (data) {
                            $scope.error = true;
                        });
                    };

                    $scope.update = function () {
                        $scope.invoice.auditStatus = $scope.actions.update.auditStatus;
                        Invoice.update({}, $scope.invoice).$promise.then(function (data) {
                            console.log(data);
                            $scope.success = true;
                        }, function (data) {
                            $scope.error = true;
                        });
                    };

                    $scope.draft = function () {
                        $scope.invoice.auditStatus = $scope.actions.draft.auditStatus;
                        if ($scope.invoice.invoiceId) {
                            Invoice.update({}, $scope.invoice).$promise.then(function (data) {
                                console.log(data);
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                        } else {
                            Invoice.save({}, $scope.invoice).$promise.then(function (data) {
                                console.log(data);
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                        }
                    };

                    $scope.submit = function () {
                        $scope.invoice.auditStatus = $scope.actions.submit.auditStatus;
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

                    $scope.receive = function () {
                        $scope.invoice.auditStatus = $scope.actions.receive.auditStatus;
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

                    $scope.adjust = function () {
                        $scope.invoice.auditStatus = $scope.actions.adjust.auditStatus;
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

                    $scope.sendBack = function () {
                        $scope.invoice.auditStatus = $scope.actions.sendBack.auditStatus;
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

                },
                link: function (scope, element, attrs, ctrl) {
                    scope.$watch(attrs['invoice'],function(){
                        if(scope.invoice){
                            scope.success = scope.options.activeStatus &&  scope.options.activeStatus != scope.invoice.auditStatus;
                        }
                    });

                    scope.$watch('options.actions.sendBack.auditStatus',function(val){
                        if(scope.options.actions.sendBack && val){
                            scope.options.actions.sendBack.auditStatus = val;
                        }
                    });

                    scope.$watch('receivedDateOpened',function(val){
                        scope.receivedDateOpened = val;
                    });
                }
            };
        }
    })

    .directive('carnum', function() {
        return {
            require: 'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$parsers.unshift(function(viewValue) {
                    if (/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/.test(viewValue)) {
                        // it is valid
                        ctrl.$setValidity('carNum', true);
                        return viewValue;
                    } else {
                        // it is invalid, return undefined (no model update)
                        ctrl.$setValidity('carNum', false);
                        return undefined;
                    }
                });
            }
        };
    });
;

