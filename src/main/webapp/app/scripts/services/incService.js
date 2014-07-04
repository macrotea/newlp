/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Inc', function ($http, $resource, currentUser) {
        return $resource('/api/rest/incs/:incId', {invoiceId: "@incId"}, {
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
                url: '/api/rest/incs/search',
                method: 'POST',
                params: {},
                isArray: false,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            findByMemberId: {
                url: '/api/rest/incs/search/findByMemberId',
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

