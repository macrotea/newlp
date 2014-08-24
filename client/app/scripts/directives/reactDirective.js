'use strict';
/**
 * Created by Sean on 6/28/2014.
 */

//window.FundooRating = React.createClass({
//    render: function () {
//        var items = [];
//        for (var i = 0; i < this.props.scope.max; i++) {
//            var clickHandler = this.props.scope.$apply.bind(this.props.scope, this.props.scope.toggle.bind(null, i));
//            items.push(<li class={i < this.props.scope.ratingValue && 'filled'} onClick={clickHandler}>{'\u2605'}</li>);
//        }
//        return <ul class="rating">{items}</ul>;
//    }
//});

angular.module('newlpApp')
    .directive('fundooRating', function () {
        return {
            restrict: 'A',
            scope: {
                ratingValue: '=',
                max: '=',
                readonly: '@',
                onRatingSelected: '&'
            },
            link: function (scope, elem, attrs) {
                scope.toggle = function (index) {
                    if (scope.readonly && scope.readonly === 'true') {
                        return;
                    }
                    scope.ratingValue = index + 1;
                    scope.onRatingSelected({rating: index + 1});
                };
                scope.$watch('ratingValue', function (oldVal, newVal) {
                    React.renderComponent(window.FundooRating({scope: scope}), elem[0]);
                });
            }
        }
    });