'use strict';
/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .factory('currentUser',  function (localStorageService) {
        return {
            getUsername: function () {
                return localStorageService.get('username');
            }
        }
    })
    .factory('authService',  function ( $http,  localStorageService) {

        return {
            login: function (username, password) {
                return this.authenticate(username, password);
            },
            logout: function () {
                localStorageService.remove('username');
                $http.post('/logout', $.param({
                }), {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },
            authenticate: function (username, password) {
                return $http.post('/auth/authenticate', $.param({
                    username: username,
                    password: password
                }), {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },
            isAuthenticated: function () {
                return $http.get('/auth/authenticate', $.param({
                    username: username
                }), {
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },
            setCredentials: function (username) {
                localStorageService.set('username', username);
            },
            clearCredentials: function () {
                document.execCommand('ClearAuthenticationCache');
                localStorageService.remove('username');
            },
            getCurrentUsername: function () {
                return localStorageService.get('username');
            },
            getPreUsername: function () {
                return localStorageService.get('username');
            }
        };
    })
;