'use strict';
/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .factory('currentUser',  function (localStorageService) {
        return {
            getUsername: function () {
                return  localStorageService.get('authentication') && localStorageService.get('authentication').username;
            }
        }
    })
    .factory('authService',  function ( $rootScope,$http,  localStorageService,$state) {

        return {
            login: function (username, password) {
                return this.authenticate(username, password);
            },
            logout: function () {
                localStorageService.remove('authentication');
                $http.post('/api/v1/auth/logout', $.param({
                }), {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },
            authenticate: function (username, password) {
                return $http.post('/api/v1/auth/authenticate', $.param({
                    username: username,
                    password: password
                }), {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },
//            isAuthenticated: function () {
//                return $http.get('/api/v1/auth/authenticate', $.param({
//                    username: username
//                }), {
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                });
//            },
//            getCurrentUsername: function () {
//                return localStorageService.get('username');
//            },
//            getPreUsername: function () {
//                return localStorageService.get('username');
//            }
            setAuthentication: function (authentication) {

                var authorities = authentication.authorities.map(function (obj) {
                    return obj.authority;
                });
                authentication.isAuthorized = function () {
                    var args = Array.prototype.slice.call(arguments);
                    return _.intersection(authorities,args).length > 0;
                };

                $rootScope.authentication =authentication;

                localStorageService.set('authentication', authentication);
            },
            getAuthentication: function () {
                var authentication = localStorageService.get('authentication');

                var authorities = authentication.authorities.map(function (obj) {
                    return obj.authority;
                });
                authentication.isAuthorized = function () {
                    var args = [].slice.call(arguments)[0];
                    for (var i = 0; i < args.length; i++) {
                        args[i] = args[i].trim();
                    }
                    var granted = false;
                    var authoritiesTmp = _.intersection(authorities,args);
                    authoritiesTmp.forEach(function (auth) {
                        if($state.is(auth.split('|')[0])){
                            granted = true;
                        }
                    });
                    return granted;
                };

                return authentication;
            },
            clearCredentials: function () {
                $rootScope.authentication =undefined;
                document.execCommand('ClearAuthenticationCache');
                localStorageService.remove('authentication');
            }
        };
    })
;