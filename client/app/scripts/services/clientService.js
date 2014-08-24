/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Client', function ( $resource, currentUser) {
        return $resource('/api/rest/clients/:clientId', {invoiceId: "@clientId"}, {
            update: {
                method: 'PUT'
            },
//            save: {
//                method: 'POST',
//                params:{},
//                isArray:false,
//                headers:{
//                    'Content-Type':'application/json'
//                }
//            },
            getAll: {
                method: 'GET',
                params: {
                }
            },
            search: {
                url: '/api/rest/clients/search',
                method: 'POST',
                params: {},
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            findByNameOrNumLike: {
                url: '/api/rest/clients/search/findByNameOrNumLike',
                method: 'GET',
                isArray: false,
                params: {
                    memberId:currentUser.getUsername()
                },
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }
        })
    })
;

