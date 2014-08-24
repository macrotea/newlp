/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "incSelect": function ($compile) {
            return {
                templateUrl: 'templates/inc.select.html',
                restrict: 'AE',
//                transclude: true,
                scope: {
                    inc: '=inc'
                },
                controller: function ($scope, Inc, currentUser) {
//                    var incs = sessionStorage.getItem('incs');
//                    if(undefined == incs){
                    Inc.findByMemberId({memberId: currentUser.getUsername()}, {}, function (data) {
                        var  incs = data._embedded.incs;
                        $scope.incs =incs;
                        $scope.inc = incs[0];
//                            sessionStorage.setItem('incs',JSON.stringify(incs));
                    });

//                    $scope.$watch('inc', function (val) {
//                       if(undefined != val){
//                           var  incs = [val];
//                           $scope.incs =incs;
//                           $scope.inc = incs[0];
//                       }
//                    });

                },
                link: function (scope, element, attrs, ctrl) {

                    if (element.attr('required')){
                        scope.required = true;
                    }
                }
            };
        }
    })
;