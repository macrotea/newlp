'use strict';

/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .controller('headerCtrl', function ($scope, home,currentUser) {

        $scope.currentUser = {};
        $scope.currentUser.username = currentUser.getUsername();

    })
    .controller('mainCtrl', function ($scope, home,currentUser) {

        /*init panels*/
            home.getPanels().$promise.then(function (data) {
                $scope.panels = data.content;
            });
    })
;