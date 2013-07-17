'use strict';

angular.module('tpsApp')
  .directive('clickToEdit', function () {
    return {
      template: '<div></div>',
      restrict: 'E',
      link: function postLink(scope, element, attrs) {
        element.text('this is the clickToEdit directive');
      }
    };
  });
