/**
 * Created by Sean on 6/17/2014.
 */
angular.module('newlpApp')

    .controller('creditManagementBalanceController', function ($scope, $state,  ngDialog) {
        $scope.searchForm = {
            options: {
                criteria: {
                },
                actions: {
                    remove: function (creditId) {
                        $scope.creditIdToRemove = creditId;
                        ngDialog.open({
                            template: 'views/credit.management.remove.html',
                            className: 'ngdialog-theme-plain',
                            controller: 'creditManagementRemoveConfirmCtrl',
                            scope: $scope
                        });
                    }
                }
            }
        };
    })
    .controller('creditManagementRemoveConfirmCtrl', function ($scope, ngDialog, Credit) {

        $scope.confirm = function () {
            Credit.remove({creditId: this.creditIdToRemove}, function (data) {
                $scope.$parent.data.content = $scope.$parent.data.content.filter(function (credit) {
                    return credit.creditId != $scope.$parent.creditIdToRemove;
                });
                $scope.$parent.creditIdToRemove = undefined;
                $scope.closeThisDialog();
            });
        };

        $scope.cancel = function () {
            ngDialog.close();
        };
    })
;