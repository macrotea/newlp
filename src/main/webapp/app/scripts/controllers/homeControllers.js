'use strict';

/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .controller('headerCtrl', function ($scope, home) {

//        $scope.currentUser = {};
//        $scope.currentUser.username = currentUser.getUsername();

    })
    .controller('mainCtrl', function ($scope, home) {

        /*init panels*/
            home.getPanels().$promise.then(function (data) {
                $scope.panels = data.content;
            });
    })
;