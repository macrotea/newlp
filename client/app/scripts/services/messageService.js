/**
 * Created by Sean on 6/12/2014.
 */
"use strict";

angular.module('newlpApp')
    .factory('message', function ($resource) {
        return $resource('/api/wx/messages',null,{
            'getAll':{
                method:'GET',
                params:{}
            }
        })
    })
;