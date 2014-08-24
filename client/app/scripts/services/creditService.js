/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Credit', function ($http, $resource) {
        return $resource('/api/v1/credits/:creditId', {creditId: "@creditId"}, {
            update: {
                method: 'PUT'
            },
            patch: {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            },
            queryAll: {
                method: 'GET',
                params: {
                }
            },
            search: {
                url: '/api/v1/credits/search',
                method: 'POST',
                params: {
                },
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        })
    })
;

