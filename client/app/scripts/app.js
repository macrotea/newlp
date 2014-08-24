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
        'mgcrea.ngStrap',
        'ngDialog',
//        'ng-bootstrap3-datepicker',
//        'ngBootstrap',
        'daterangepicker',
//        'dateRangePicker',
//        'datatables',
//        'ngTable',
        'LocalStorageModule'

    ])
    .config(function ($httpProvider, $locationProvider) {

//        $locationProvider.html5Mode(true);

        var publicStates = ['login', 'logout'];

        var sessionTimeoutModal;

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

                        if (publicStates.indexOf($rootScope.$state.current.name) < 0 && undefined == sessionTimeoutModal) {

                            /*popup sessiontime warning modal*/
                            sessionTimeoutModal = $rootScope.$modal({
                                template: 'views/auth.sessionTimeout.html',
                                show: true
                            });
                            sessionTimeoutModal.$scope.goLogin = function () {
                                $rootScope.$state.go('login');
                            };

                        } else {
                            $rootScope.currentUser = undefined;
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
