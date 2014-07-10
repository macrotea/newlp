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

        $httpProvider.interceptors.push(function ($q, $rootScope) {

            var publicStates =['login','logout'];
            var sessionTimeoutCtrl = function ($scope, $modalInstance,$state) {

                $scope.goToLoginPage = function () {
                    $modalInstance.close("");
                    $state.go('login');
                };

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };

            };

            var modalInstance;

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
                        }
                    }
                    return $q.reject(rejection);
                }
            };
        });

//        var publicStates =['login','logout'];
//        var sessionTimeoutCtrl = function ($scope, $modalInstance,$state) {
//
//            $scope.goToLoginPage = function () {
//                $modalInstance.close("");
//                $state.go('login');
//            };
//
//            $scope.cancel = function () {
//                $modalInstance.dismiss('cancel');
//            };
//
//        };
//
//        var modalInstance = undefined;
//
//        var interceptor = ['$rootScope', '$q', function ($rootScope, $q) {
//            function success(response) {
//                return response;
//            }
//            function error(response) {
//                var status = response.status;
//                if (status === 401) {
//                    if(publicStates.indexOf($rootScope.$state.current.name) < 0  && undefined ==modalInstance){
//                        modalInstance =  $rootScope.$modal.open({
//                            templateUrl: 'views/auth.sessionTimeout.html',
//                            controller:sessionTimeoutCtrl
//                        });
//                    }
//                    return;
//                }
//                // otherwise
//                return $q.reject(response);
//            }
//            return function (promise) {
//                return promise.then(success, error);
//            }
//        }];
//        $httpProvider.interceptors.push(interceptor);

    })
;
