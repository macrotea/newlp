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
                controller: function ($scope, Inc, currentUser) {
                    var incs = sessionStorage.getItem('incs');
                    if(undefined == incs){
                        Inc.findByMemberId({memberId: currentUser.getUsername()}, {}, function (data) {
                            incs= data._embedded.incs;
                            $scope.inc = incs[0];
                            sessionStorage.setItem('incs',JSON.stringify(incs));
                        });
                    }else{
                        $scope.inc = JSON.parse(incs)[0];
                    }
                }
            };
        }
    })
;