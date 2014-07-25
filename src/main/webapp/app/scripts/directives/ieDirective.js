/**
 * Created by Sean on 7/1/2014.
 */
angular.module('newlpApp')
    .directive('ieSelectFix', [
        function() {

            return {
                restrict: 'A',
                require: 'ngModel',
                link: function(scope, element, attributes, ngModelCtrl) {
                    var isIE = document.attachEvent;
                    if (!isIE) return;

                    var control = element[0];
                    //to fix IE8 issue with parent and detail controller, we need to depend on the parent controller
                    scope.$watch(attributes.ieSelectFix, function() {
                        //this will add and remove the options to trigger the rendering in IE8
                        var option = document.createElement("option");
                        control.add(option,null);
//                        control.remove(control.options.length-1);
                    });
                }
            }
        }
    ]);