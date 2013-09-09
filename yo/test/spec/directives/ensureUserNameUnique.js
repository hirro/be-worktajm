'use strict';

describe('Directive: ensureUserNameUnique', function () {

  // load the directive's module
  beforeEach(module('tpsApp'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<ensure-user-name-unique></ensure-user-name-unique>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the ensureUserNameUnique directive');
  }));
});
