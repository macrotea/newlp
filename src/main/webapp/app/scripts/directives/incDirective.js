/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "incSelect": function () {
            return {
                templateUrl: 'templates/inc.select.html',
                restrict: 'E',
//            transclude: true,
                scope: {
                    inc: '=inc',
                    incId: '=?incId'
                },
                controller: function ($scope, $modal, Inc, currentUser) {
                    Inc.findByMemberId({memberId: currentUser.getUsername()}, {}, function (data) {
                        $scope.inc = data._embedded.incs[0];
                    });
                }
            };
        }
    }).run(function ($templateCache) {
        $templateCache.put('templates/inc.select.html', '<select class="form-control" title=""><option value="{{inc.incId}}">{{inc.incName}}</option> </select>');
    });