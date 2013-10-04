/* globals before, beforeEach, describe, inject, $injector, it, testIt, Restangular, expect */
'use strict';

describe('Service: testIt', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var testIt;
  beforeEach(inject(function (_testIt_) {
    testIt = _testIt_;
  }));

  var Restangular;
  var $httpBackend, $rootScope;
  beforeEach(inject(function($injector) {
    $rootScope = $injector.get('$rootScope');
    
    // Set up the mock http service responses
    $httpBackend = $injector.get('$httpBackend');

    //
    $httpBackend.when('GET', '/person/1').respond({userId: 'userX'}, {'A-Token': 'xxx'});
    Restangular = $injector.get('Restangular');
  }));


  it('should do something', function () {
    expect(testIt.a()).toBe(456);
  });

  it('should return the promise to a person', function() {
    var person = testIt.b();
    var x = Restangular.one('person', 1).get();
    //$httpBackend.flush();
    //person.resolve();
  });
 
});
