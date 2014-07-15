'use strict';

angular
    .module('newlpApp', [
        'ngRoute',
        'ngResource',
        'ngCookies',
        'ngAnimate',

        /*pulgins*/
        'ui.router',
        'ui.bootstrap',
        'ngDialog',
//        'datatables',
//        'ngTable',
        'LocalStorageModule'

    ])
    .config(function ($httpProvider) {

        var publicStates =['login','logout'];
        var sessionTimeoutCtrl = ['$scope','$modalInstance','$state',function ($scope, $modalInstance,$state) {

            $scope.goToLoginPage = function () {
                $modalInstance.close("");
                $state.go('login');
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        }];
        var modalInstance;

        var inteceptor = ['$rootScope', '$q', function ($rootScope, $q) {
            return {
                'response': function (response) {
                    //Will only be called for HTTP up// to 300
                    return response;
                },
                'responseError': function (rejection) {
//                    if (rejection.status === 403) {
//                        alert('Deny');
//                    }
                    if (rejection.status === 401) {

                        if(publicStates.indexOf($rootScope.$state.current.name) < 0  && undefined ==modalInstance){
                            modalInstance =  $rootScope.$modal.open({
                                templateUrl: 'views/auth.sessionTimeout.html',
                                controller:sessionTimeoutCtrl
                            });
                        }else{
                            $rootScope.currentUser =undefined;
                            document.execCommand('ClearAuthenticationCache');
                            localStorage.removeItem('username');
                        }
                    }
                    return $q.reject(rejection);
                }
            }
        }];
        $httpProvider.interceptors.push(inteceptor);

    })
;
