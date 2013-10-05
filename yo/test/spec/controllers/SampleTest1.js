'use strict';

describe('Controller: SampleTest1Ctrl', function () {

  // load the controller's module
  beforeEach(module('tpsApp'));

  var SampleTest1Ctrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SampleTest1Ctrl = $controller('SampleTest1Ctrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });

  this.saveMessage = function(message) {
    $scope.status = 'Saving...';
    $http.post('/add-msg.py', message).success(function(response) {
      $scope.status = '';
    }).error(function() {
      $scope.status = 'ERROR!';
    });
  };
});
