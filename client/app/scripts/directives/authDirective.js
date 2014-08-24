/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive({
        "authorities": function ($compile) {
            return {
//                templateUrl: 'templates/inc.select.html',
                restrict: 'AE',
//                transclude: true,
                scope:true,
                controller: function ($scope,  authService) {
                    $scope.authService = authService;
                },
                link: function (scope, element, attrs, ctrl) {
                    var authorities = attrs.authorities.split(',');
                    var au = scope.authService.getAuthentication();
                    if(!au.isAuthorized(authorities)){
                        element.hide();
                    }
//                    au.isAuthorized()
                }
            };
        }
    })
;