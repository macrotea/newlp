/**
 * Created by Sean on 5/10/2014.
 */
angular.module('newlpApp')
    .controller('loginCtrl', function ($scope, $state, authService) {

        $scope.user = {};

        /*handle login click*/
        $scope.login = function () {

            $scope.form.username.$dirty = true;
            $scope.form.password.$dirty = true;
            $scope.form.$error.incorrect = false;

            /*authenticate user*/
            authService.login($scope.user.username, $scope.user.password)
                .success(function (data) {
                    authService.setCredentials($scope.user.username);
                    $state.go('home');
                }).error(function (data) {
                    $scope.form.$error.incorrect = true;
                });
        }
    })
    .controller('logoutCtrl', function ($scope, $state, authService) {

        authService.logout();

        $scope.user = {};

        /*handle login click*/
        $scope.login = function () {

            $scope.form.username.$dirty = true;
            $scope.form.password.$dirty = true;
            $scope.form.$error.incorrect = false;

            /*authenticate user*/
            authService.login($scope.user.username, $scope.user.password)
                .success(function (data) {
                    authService.setCredentials($scope.user.username);
                    $state.go('home');
                }).error(function (data) {
                    $scope.form.$error.incorrect = true;
                });
        }
    })
    .controller('sessionTimeoutCtrl', ['$scope','$modalInstance','$state',function ($scope, $modalInstance,$state) {

        $scope.goToLoginPage = function () {
            $modalInstance.close("");
            $state.go('login');
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

    }])
;