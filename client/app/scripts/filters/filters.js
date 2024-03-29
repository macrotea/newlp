'use strict';

/* Filters */

angular.module('newlpApp').
    filter('reverse', function () {
        return function (input, uppercase) {
            var out = "";
            for (var i = 0; i < input.length; i++) {
                out = input.charAt(i) + out;
            }
            // conditional based on optional argument
            if (uppercase) {
                out = out.toUpperCase();
            }
            return out;
        }
    }).
    filter('ceil', function() {
        return function(input) {
            return Math.ceil(input);
        }
    })
;
