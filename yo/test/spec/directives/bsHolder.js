'use strict';

describe('Directive: bsHolder', function () {

  // load the directive's module
  beforeEach(module('tpsApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<bs-holder></bs-holder>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the bsHolder directive');
  }));
});
