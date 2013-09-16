'use strict';

describe('Controller: DashboardTimeEntriesCtrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var DashboardTimeEntriesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DashboardTimeEntriesCtrl = $controller('DashboardTimeEntriesCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
