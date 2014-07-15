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
                        $scope.page.size = $scope.pageSize.value;
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
        },
        "invoiceSelectMaterials": function () {
            return {
                templateUrl: 'templates/invoice.select.materials.html',
                restrict: 'AE',
                transclude: true,
                scope: {
                    invoiceDetails: '=invoiceDetails',
                    readOnly:'=readOnly',
                    options:'=options'
                },
                controller: function ($scope,  Material) {

                    $scope.fields = $scope.options.fields?$scope.options.fields:{};
                    $scope.fields.orderCount = $scope.options.fields.orderCount?$scope.options.fields.orderCount:{};
                    $scope.fields.deliveryCount = $scope.options.fields.deliveryCount?$scope.options.fields.deliveryCount:{};
                    $scope.fields.orderCount = $scope.options.fields.orderCount?$scope.options.fields.orderCount:{};
                    $scope.fields.remark = $scope.options.fields.remark?$scope.options.fields.remark:{};
                    $scope.fields.actions = $scope.options.fields.actions?$scope.options.fields.actions:{};


                    //init temp variables
                    $scope.data = undefined;
                    $scope.searchTerm = '';
                    $scope.currentPage = 1;


                    //search materials
                    $scope.search = function () {
                        $scope.loading = true;

                        Material.findByNameOrNumLike({
                            page: $scope.currentPage - 1,
                            searchTerm:$scope.searchTerm
                        },{}, function (data) {
                            $scope.loading = false;
                            $scope.data = data;
                        });
                    };
                    $scope.onPageChanged = $scope.search;


                    //add to selected list
                    $scope.add = function (material) {

                        var notExist = true;
                        $scope.invoiceDetails.forEach(function (invoiceDetail) {
                            if(material.materialNum ==invoiceDetail.material.materialNum){
                                notExist = false;
                            }
                        });

                        if(notExist){
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
                            if(materialNum ==invoiceDetail.material.materialNum){
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
        }

    })
;

