/**
 * Created by Sean on 6/18/2014.
 */
angular.module('newlpApp')
    .factory('Material', function ($resource,currentUser) {

        return $resource('/api/rest/materials/:materialId', {invoiceId: "@materialId"}, {
            findByNameOrNumLike: {
                url: '/api/rest/materials/search/findByNameOrNumLike',
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
