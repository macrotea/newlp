/**
 * Created by Sean on 5/10/2014.
 */
angular.module('newlpApp')
    .controller('managementMessageListCtrl', function ($scope,  $state,message) {
            message.getAll(function (data) {
                $scope.chats = data.messages;
                $scope.messages = data.messages;
            })
    })
;