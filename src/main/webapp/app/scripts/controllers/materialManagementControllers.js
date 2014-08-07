/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')

    .controller('materialManagementController', function ($scope) {
        $scope.searchForm = {
            options: {
                criteria: {
                },
                actions: {
                }
            }
        };
    })
    .controller('materialManagementRemoveConfirmCtrl', function ($scope, ngDialog, Material) {

        $scope.confirm = function () {
            Material.remove({materialNum: this.materialNumToRemove}, function (data) {
                $scope.$parent.data.content = $scope.$parent.data.content.filter(function (credit) {
                    return credit.materialNum != $scope.$parent.materialNumToRemove;
                });
                $scope.$parent.materialNumToRemove = undefined;
                --$scope.$parent.data.page.totalElements;
                $scope.closeThisDialog();
            });
        };

        $scope.cancel = function () {
            ngDialog.close();
        };
    })


    .controller('inc_materialManagementController', function ($scope) {
        $scope.searchForm = {
            options: {
                criteria: {
                },
                actions: {
                }
            }
        };
    })
    .controller('inc_materialManagementRemoveConfirmCtrl', function ($scope, ngDialog, Material) {

        $scope.confirm = function () {
            Material.remove({materialNum: this.materialNumToRemove}, function (data) {
                $scope.$parent.data.content = $scope.$parent.data.content.filter(function (credit) {
                    return credit.materialNum != $scope.$parent.materialNumToRemove;
                });
                $scope.$parent.materialNumToRemove = undefined;
                --$scope.$parent.data.page.totalElements;
                $scope.closeThisDialog();
            });
        };

        $scope.cancel = function () {
            ngDialog.close();
        };
    })
;