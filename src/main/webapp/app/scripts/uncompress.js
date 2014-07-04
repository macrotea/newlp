'use strict';

/**
 * Created by Sean on 4/21/2014.
 */
angular
    .module('newlpApp')
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
    })
;