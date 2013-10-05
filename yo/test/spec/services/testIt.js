/*globals beforeEach, describe, expect, spyOn, inject, it, expect */
'use strict';

describe('Service: testIt', function () {

  // Declarations
  var service;
  var httpBackend;
  var scope;

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  beforeEach(inject(function (testIt, $httpBackend, $rootScope) {
    service = testIt;
    httpBackend = $httpBackend;
    scope = $rootScope;
  }));

  it('should make an ajax call to api/v1/fruits', function () {
      httpBackend.whenGET('/api/v1/fruits').respond([{id:1, name: 'banana'}]);
      expect(service.getData()).toBeDefined();
  });

  it('should resolve to an array of fruit', function () {
    httpBackend.whenGET('/api/v1/fruits').respond([{id:1, name: 'banana'}]);
    spyOn(service, 'getData').andCallThrough();

    var promise = service.getData(),
        theFruits = null;
    expect(service.getData).toHaveBeenCalled();

    promise.then(function (fruits) {
      console.log('Bajs off');
      theFruits = fruits;
    });

    scope.$digest();
    httpBackend.flush();

    expect(theFruits instanceof Array).toBeTruthy();
  });

  it('should reject the promise and respond with error', function () {
    httpBackend.whenGET('/api/v1/fruits').respond(500);
    var promise = service.getData(),
        result = null;

    promise.then(function (fruits) {
      console.log('Bajs off');
      result = fruits;
    }, function (reason) {
      console.log('Bajs on');
      result = reason;
    });

    scope.$digest();
    httpBackend.flush();

    expect(result).toBe('error');
  });
});
