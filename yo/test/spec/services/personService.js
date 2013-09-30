/* globals expect, $httpBackend, it, beforeEach, describe, inject */

'use strict';

describe('Service: personService', function () {

  // API
  var httpBackend;
  var PersonService, persons;
  var Restangular;
  var scope;

  // load the service's module
  beforeEach(module('tpsApp'));

  // Init HTTP mock backend
  beforeEach(inject(function($injector, _$httpBackend_, $rootScope, _Restangular_) {
    httpBackend = _$httpBackend_;
    //httpBackend.expectGET('../json/community/getSearchResults').respond('[{"id":"tester"},{"username":"tester2"}]');
    Restangular = _Restangular_;
    scope = $rootScope.$new();

    // Model
    persons = [
      { id: 1, email: 'alice@example.com' },
      { id: 2, email: 'bob@example.com' },
    ];
    httpBackend.whenGET('/person/1').respond(persons[0]);

    // Service
    
  }));

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('set active time entry', function () {
    spyOn(PersonService, 'getPerson').andCallThrough();
    var person = PersonService.getPerson();
    expect(PersonService.getPerson).toHaveBeenCalled();

    var resolvedValue;
    person.then(function (pr) {
      resolvedValue = pr;
    });

    httpBackend.flush();
    //person.resolve();
    expect(person).toBeDefined();
    expect(person.id).toBe(1);
    expect(person.username).toBeDefined();
    expect(person.username).toBe('alice@example.com');
  });

});
