'use strict';

describe('Directive: clickToEdit', function () {
  beforeEach(module('tpsApp'));

  var element;

  it('should make hidden element visible', inject(function ($rootScope, $compile) {
    element = angular.element('<click-to-edit></click-to-edit>');
    element = $compile(element)($rootScope);
    expect(element.text()).toBe('this is the clickToEdit directive');
  }));
});
