'use strict';

/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .factory('home', function ($resource) {
        return $resource('/api/v1/home',{},{
            getPanels: {
                url:'/api/v1/home/panels',
                method: 'GET',
                isArray:true,
                params:{
                }
            }
        })
    })
;