'use strict';

/**
 * Created by Sean on 5/11/2014.
 */
angular.module('newlpApp')
    .controller('headerCtrl', function ($scope, $rootScope) {

//        $scope.currentUser = {};
//        $scope.currentUser.username = currentUser.getUsername();

        $scope.toggleSidebar = function () {
            $rootScope.collapseSidebar=!$rootScope.collapseSidebar;
        };
    })
    .controller('mainCtrl', function ($scope, home,$state) {

        /*init panels*/
            home.getPanels().$promise.then(function (data) {
                $scope.panels = data;
            });

        $scope.socket = {
            client: null,
            stomp: null
        };

        $scope.notify = function (response) {
            var updatedPanels = JSON.parse(response.body);
            $scope.panels.forEach(function (panel) {
                panel.panes.forEach(function (pane) {
                    updatedPanels.forEach(function (updatedPanel) {
                        if (updatedPanel.panelId == panel.panelId) {
                            panel.count = updatedPanel.count; //update panel's count
                            updatedPanel.panes.forEach(function (updatedPane) {
                                if (updatedPane.paneId == pane.paneId) {
                                    pane.count = updatedPane.count;  //update pane's count
                                }
                            });
                        }
                    });
                });
            });
            $scope.$digest();
        };

        $scope.reconnect = function(e) {
            setTimeout($scope.initSockets, 10000);
        };

        $scope.initSockets = function() {
            $scope.socket.client = new SockJS('/notify');
            $scope.socket.stomp = Stomp.over($scope.socket.client);
            $scope.socket.stomp = Stomp.over($scope.socket.client);
            $scope.socket.stomp.connect({}, function() {
                $scope.socket.stomp.subscribe("/user/topic/notify", $scope.notify);
            });
            $scope.socket.client.onclose = $scope.reconnect;
        };

        $scope.initSockets();
    })
;