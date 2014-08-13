/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "materialManagementForm": function () {
            return {
                templateUrl: 'templates/material.datatable.html',
                restrict: 'AE',
                scope: {
                    options: '=options'
                },
                controller: function ($scope,$timeout, $state, Material, ngDialog) {

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
                        {name: 100, value: 100},
                        {name: 5,value:5}
                    ];
                    $scope.pageSize = $scope.pageSizes[0];
                    $scope.page = {};
                    $scope.page.number = 1;
                    $scope.page.size = $scope.pageSize.value;

                    /*temp vars*/
                    var tmpSeqForNewMaterial = 0;
                    var tmpMaterialIds = [];

                    /* watch materialNum , special character is forbidden */
                    $scope.$watch(function($scope) {
                        if($scope.data && $scope.data.content){
                            return $scope.data.content.
                                map(function(material) {
                                    return material.materialNum
                                });
                        }
                    }, function (newVal, oldVal) {
                        if(_.isArray(newVal)){
                            var materialNum = newVal[newVal.length-1];
                            if( undefined !== materialNum && _.intersection(materialNum.split(''),['‘','’','“','%','#']).length > 0){
                                var material =   _.findWhere($scope.data.content, {materialNum: newVal[newVal.length-1]});
                                material.materialNum = oldVal[oldVal.length-1];  // restore to oldVal
                            }
                        }
                    }, true);

                    $scope.onMaterialNumChange = function(detailForm,materialNum){
                        Material.findByMaterialNum({materialNum:materialNum},function(data){
                            if(data._embedded && data._embedded.materials.length > 0){
                                detailForm.materialNum.$setValidity("duplicateMaterialNum",false);
                            }else{
                                detailForm.materialNum.$setValidity("duplicateMaterialNum",true);
                            }
                        });
                    };

                    $scope.onMaterialNameChange = function(detailForm,materialName){
                        Material.findByMaterialName({materialName:materialName},function(data){
                            if(data._embedded && data._embedded.materials.length > 0){
                                detailForm.materialName.$setValidity("duplicateMaterialName",false);
                            }else{
                                detailForm.materialName.$setValidity("duplicateMaterialName",true);
                            }
                        });
                    };

                    $scope.onPriceEditable = function(material,$event){
                        var $trElem =$($event.currentTarget).parent().parent();
                        material.priceEditable = true;
                        $timeout(function () {
                            $trElem.find("input[ng-model='material.price']").focus().select();
                        },0);
                    };


                    $scope.onMaterialPriceBlur = function (material) {
                        material.priceEditable = false;
                        Material.update({
                            materialNum:material.materialNum
                        },material).$promise.then(function (data) {
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                    };

                    $scope.onMaterialPriceKeypress = function (material, $event) {
                        if( 13 ==$event.keyCode){
                            var $trElem =$($event.currentTarget).parent().parent().next();
                            material.priceEditable = false;
                            Material.update({
                                materialNum:material.materialNum
                            },material).$promise.then(function (data) {
                                    $scope.success = true;
                                    material.priceEditable = false;
                                    $timeout(function () {
                                        $trElem.find("a:eq(0)").click();
                                    },0);
                                }, function (data) {
                                    $scope.error = true;
                                });
                        }
                    };


                    $scope.$watch(function ($scope) {
                        if ($scope.data && $scope.data.content) {
                            return $scope.data.content.
                                map(function (material) {
                                    return material.price
                                });
                        }
                    }, function (newVal, oldVal) {
                        if (undefined != newVal && undefined != oldVal) {
                            for (var i = 0; i < newVal.length; i++) {
                                var n = newVal[i];
                                var o = oldVal[i];
                                if (n !== o) {
                                    if(['',undefined].indexOf(n) > -1){
                                        $scope.data.content[i].price = 0;
                                    }else if (!/^[0-9]\d*(.\d+)?$/.test(n) && !/^\d+.$/.test(n)) {
                                        $scope.data.content[i].price = oldVal[i];
                                    }
                                }
                            }
                        }
                    }, true);

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
                        $scope.onPageChanged = $scope.search;
                        $scope.onPageChanged();
                    };

                    $scope.rest = function(){
                        $scope.loading = true;
                        $scope.reset();
                    };

                    $scope.save = function (material) {

                        var materialUpdated = angular.copy(material);
                        materialUpdated.materialId = Math.round(material.materialId);

                        if(Math.round(material.materialId)){

                            /*find materialTmp idx*/
                            var idx = undefined;
                            for (var i = 0; i < $scope.data.content.length; i++) {
                                var obj = $scope.data.content[i];
                                if(obj.materialId == materialUpdated.materialId){
                                    idx = i;
                                    break;
                                }
                            }

                            /*update material*/
                            Material.update({
                                materialNum: materialUpdated.materialNum
                            }, materialUpdated).$promise.then(function (data) {
                                    material.editable = false;/*hide edit*/
                                    data.materialId = materialUpdated.materialId;
                                    $scope.data.content[idx] = data;
                                    $scope.success = true;
                                }, function (data) {
                                    $scope.error = true;
                                });
                        }else{

                            /*save material*/
                            materialUpdated.materialId = undefined;
                            Material.save({}, materialUpdated).$promise.then(function (data) {
                                $scope.data.content.forEach(function(material){
                                    ++material.materialId;
                                });

                                data.materialId = 1;
                                material.editable = false;
                                material.materialId = data.materialId -0.00001;
                                material.isNew = undefined;
                                tmpMaterialIds.push(material.materialId);

                                $scope.data.content.push(data);
                                ++$scope.data.page.totalElements;

                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                        }

                    };

                    $scope.edit = function (material) {

                        if(!Math.round(material.materialId) || (typeof material.materialId== "number" && isFinite(material.materialId) && material.materialId%1 != 0)){
                            return ;
                        }

                        var tmpMaterialId = material.materialId -0.00001;
                        
                        if(tmpMaterialIds.indexOf(tmpMaterialId) < 0){
                            var obj = angular.copy(material);
                            obj.materialId = tmpMaterialId;
                            obj.editable =false;
                            $scope.data.content.push(obj);
                            tmpMaterialIds.push(tmpMaterialId);
                        }

                        toggleEditable(tmpMaterialId);
                    };

                    $scope.cancel = function(material){
                        toggleEditable(material.materialId);
                    };

                    $scope.add = function () {
                        var currentDate = moment().format();
                        tmpSeqForNewMaterial = tmpSeqForNewMaterial -0.00001;

                        var material = {
                            'materialId':tmpSeqForNewMaterial,
                            "materialType": {
                                "materialTypeId":1
                            },
                            editable: true,
                            isNew: true
                        };

                        $scope.data.content.push(material);
                    };

                    $scope.remove = function (materialNum) {
                        $scope.materialNumToRemove = materialNum;
                        ngDialog.open({
                            template: 'views/material.management.remove.html',
                            className: 'ngdialog-theme-plain',
                            controller: 'materialManagementRemoveConfirmCtrl',
                            scope: $scope
                        });
                    };


                    var toggleEditable = function (materialId) {
                        for (var i = 0; i < $scope.data.content.length; i++) {
                            var obj = $scope.data.content[i];
                            if(obj.materialId == materialId){
                                obj.editable = !obj.editable;
                                break;
                            }
                        }
                    };


                    //criteria search
                    $scope.search = function () {

                        tmpMaterialIds=[];

                        Material.search({
                                page: $scope.page.number - 1,
                                size: $scope.page.size,
                                sort:'materialNum,ASC'
                            }, $scope.searchTerm
                        ).$promise.then(function (data) {
                                for (var i = 0; i < data.content.length; i++) {
                                    var obj = data.content[i];
                                    obj.materialId = data.content.length-i;
                                }

                                var materialNums = _.map(data.content,function(material){return material.materialNum});

                                $scope.data = data;
                                Material.findUsedMaterialNums({
                                    materialNums :JSON.stringify(_.map(data.content,function(material){return material.materialNum}))
                                }).$promise.then(function (data) {
                                     var usedMaterialNums =  _.intersection(materialNums,data);
                                        $scope.data.content.forEach(function (material) {
                                            if(usedMaterialNums.indexOf(material.materialNum) < 0){
                                                material.isRemoveAble = true;
                                            }else{
                                                material.isRemoveAble = false;
                                            }
                                        })
                                        $scope.loading = false;
                                    }, function (data) {
                                });

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
                        };
                        $scope.onPageChanged();
                    };

                    $scope.reset();


                },
                link: function (scope, element, attrs, ctrl) {
                }
            };
        },
        "materialManagementIncForm": function () {
            return {
                templateUrl: 'templates/inc_material.datatable.html',
                restrict: 'AE',
                scope: {
                    options: '=options'
                },
                controller: function ($scope, $timeout, $state, Material, ngDialog) {

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
                        {name: 100, value: 100},
                        {name: 5,value:5}
                    ];
                    $scope.pageSize = $scope.pageSizes[0];
                    $scope.page = {};
                    $scope.page.number = 1;
                    $scope.page.size = $scope.pageSize.value;

                    $scope.onPageSizeChange = function () {
                        $scope.page.size = $scope.pageSize.value;
                        $scope.onPageChanged();
                    };


                    $scope.$watch(function ($scope) {
                        if ($scope.data && $scope.data.content) {
                            return $scope.data.content.
                                map(function (material) {
                                    return material.incPrice
                                });
                        }
                    }, function (newVal, oldVal) {
                        if (undefined != newVal && undefined != oldVal) {
                            for (var i = 0; i < newVal.length; i++) {
                                var n = newVal[i];
                                var o = oldVal[i];
                                if (n !== o) {
                                    if(['',undefined].indexOf(n) > -1){
                                        $scope.data.content[i].incPrice = 0;
                                    }else if (!/^[0-9]\d*(.\d+)?$/.test(n) && !/^\d+.$/.test(n)) {
                                        $scope.data.content[i].incPrice = oldVal[i];
                                    }
                                }
                            }
                        }
                    }, true);

                    $scope.$watch(function ($scope) {
                        if ($scope.data && $scope.data.content) {
                            return $scope.data.content.
                                map(function (material) {
                                    return material.inventory
                                });
                        }
                    }, function (newVal, oldVal) {
                        if (undefined != newVal && undefined != oldVal) {
                            for (var i = 0; i < newVal.length; i++) {
                                var n = newVal[i];
                                var o = oldVal[i];
                                if (n !== o) {
                                    if(['',undefined].indexOf(n) > -1){
                                        $scope.data.content[i].inventory = 0;
                                    }else if (!/^[0-9]\d*(.\d+)?$/.test(n) && !/^\d+.$/.test(n)) {
                                        $scope.data.content[i].inventory = oldVal[i];
                                    }
                                }
                            }
                        }
                    }, true);


                    $scope.onActiveChange = function(material){
                        Material.updateIncMaterial({
                            incMaterialId:material.incMaterialId
                        },material).$promise.then(function (data) {
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                    };

                    $scope.onInventoryEditable = function(material,$event){
                        var $trElem =$($event.currentTarget).parent().parent();
                        material.inventoryEditable = true;
                        $timeout(function () {
                            $trElem.find("input[ng-model='material.inventory']").focus().select();
                        },0);
                    };


                    $scope.onMaterialInventoryBlur = function (material) {
                        material.inventoryEditable = false;
                        Material.updateIncMaterial({
                            incMaterialId:material.incMaterialId
                        },material).$promise.then(function (data) {
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                    };
                    
                    $scope.onMaterialInventoryKeypress = function (material, $event) {
                        if( 13 ==$event.keyCode){
                            var $trElem =$($event.currentTarget).parent().parent().next();
                            material.inventoryEditable = false;
                            Material.updateIncMaterial({
                                incMaterialId:material.incMaterialId
                            },material).$promise.then(function (data) {
                                    $scope.success = true;
                                    material.inventoryEditable = false;
                                    $timeout(function () {
                                        $trElem.find("a:eq(1)").click();
                                    },0);
                                }, function (data) {
                                    $scope.error = true;
                                });
                        }
                    };


                    $scope.onIncPriceEditable = function(material,$event){
                        var $trElem =$($event.currentTarget).parent().parent();
                        material.incPriceEditable = true;
                        $timeout(function () {
                            $trElem.find("input[ng-model='material.incPrice']").focus().select();
                        },0);
                    };


                    $scope.onMaterialIncPriceBlur = function (material) {
                        material.incPriceEditable = false;
                        Material.updateIncMaterial({
                            incMaterialId:material.incMaterialId
                        },material).$promise.then(function (data) {
                                $scope.success = true;
                            }, function (data) {
                                $scope.error = true;
                            });
                    };

                    $scope.onMaterialIncPriceKeypress = function (material, $event) {
                        if( 13 ==$event.keyCode){
                            var $trElem =$($event.currentTarget).parent().parent().next();
                            material.incPriceEditable = false;
                            Material.updateIncMaterial({
                                incMaterialId:material.incMaterialId
                            },material).$promise.then(function (data) {
                                    $scope.success = true;
                                    material.incPriceEditable = false;
                                    $timeout(function () {
                                        $trElem.find("a:eq(0)").click();
                                    },0);
                                }, function (data) {
                                    $scope.error = true;
                                });
                        }
                    };


                    $scope.onAddIncMaterial = function (material) {
                        Material.addIncMaterial({
                            materialNum:material.materialNum
                        }).$promise.then(function () {
                                material.added = true;
                                $scope.success = true;
                            }, function () {
                                $scope.error = true;
                            })
                    };

                    //handle form action
                    $scope.submit = function () {
                        $scope.loading = true;
                        $scope.onPageChanged = $scope.search;
                        $scope.onPageChanged();
                    };

                    $scope.rest = function(){
                        $scope.loading = true;
                        $scope.reset();
                    };

                    $scope.queryIncActiveMaterials = function () {
                        $scope.searchTerm.isRelated = true;
                        $scope.searchTerm.active = true;

                        $scope.search();
                    };

                    $scope.queryIncInactiveMaterials = function () {
                        $scope.searchTerm.isRelated = true;
                        $scope.searchTerm.active = false;

                        $scope.search();
                    };

                    $scope.queryMaterials = function () {
                        $scope.searchTerm.isRelated = false;

                        $scope.search();
                    };


                    //criteria search
                    $scope.search = function () {
                        $scope.loading = true;

                        Material.findByMaterialNumAndIncId({
                                page: $scope.page.number - 1,
                                size: $scope.page.size,
                                sort:'materialNum,ASC'
                            }, $scope.searchTerm
                        ).$promise.then(function (data) {

                                $scope.data = data;

                                $scope.loading = false;
                            }, function (data) {
                                $scope.loading = false;
                                $scope.error = true;
                            });
                    };
                    $scope.onPageChanged = $scope.search;


                    $scope.searchTerm = $scope.searchTerm || {};

                    $scope.queryIncActiveMaterials();


                },
                link: function (scope, element, attrs, ctrl) {

                }
            };
        }

    })
;

