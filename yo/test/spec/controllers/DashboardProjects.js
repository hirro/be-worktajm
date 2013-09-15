'use strict';

describe('Controller: DashboardProjectsCtrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var DashboardProjectsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DashboardProjectsCtrl = $controller('DashboardProjectsCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
