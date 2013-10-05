'use strict';

describe('Service: SampleTest1', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // Inject
  var httpBackend;
  var $scope;
  beforeEach(inject(function($rootScope, $injector) {
    $scope = $rootScope.$new();
    httpBackend = $injector.get('$httpBackend');
  }));

  // instantiate service
  var SampleTest1;
  beforeEach(inject(function (_SampleTest1_) {
    SampleTest1 = _SampleTest1_;
  }));

  it('should do something', function () {
    expect(!!SampleTest1).toBe(true);
  });

  it('should send msg to server', function() {

    httpBackend.expectPOST('/add-msg.py', 'message content').respond(500, '');
    
    SampleTest1.saveMessage('message content');
    //httpBackend.flush();
  
    // Here is the question: How to set $httpBackend.expectPOST to trigger
    // this condition.
    //expect($scope.status).toBe('ERROR!');
  });  

});
