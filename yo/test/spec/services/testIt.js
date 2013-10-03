'use strict';

describe('Service: testIt', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var testIt;
  beforeEach(inject(function (_testIt_) {
    testIt = _testIt_;
  }));

  // Mock the Restangular service
  var Restangular;
  before(inject(function (_Restangular_) {
    Restangular = _Restangular_;
  }));

  it('should do something', function () {
    expect(testIt.a()).toBe(456);
  });

  it('should return the promise to a person', function() {
    var person = testIt.b();
    //person.resolve();
  });
 
});
