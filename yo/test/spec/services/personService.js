/* globals expect, $httpBackend, it, beforeEach, describe, inject */

'use strict';

describe('Service: personService', function () {

  // API
  var httpBackend;
  var personSvc, persons;
  var Restangular;
  var scope;

  // Test constants
  var persons = [
    { id: 1, username: 'User A'},
    { id: 2, username: 'User B'}
  ];

  // load the service's module
  beforeEach(module('tpsApp'));

  // Inject the wanted responses into httpBackend
  beforeEach(inject(function ($httpBackend) {
    $httpBackend.whenGET('/api/types/ferrari/1').respond(persons[0]);    
  }));

  // Inject the person service
  inject(function($httpBackend, PersonService) {
    personSvc = PersonService;
    httpBackend = $httpBackend;
  });  
  // // Inject the used services
  // beforeEach(inject(function(_$httpBackend_, $rootScope, _Restangular_) {
  //   httpBackend = _$httpBackend_;
  //   //httpBackend.expectGET('../json/community/getSearchResults').respond('[{"id":"tester"},{"username":"tester2"}]');
  //   Restangular = _Restangular_;
  //   scope = $rootScope.$new();

  //   // Model
  //   persons = [
  //     { id: 1, email: 'alice@example.com' },
  //     { id: 2, email: 'bob@example.com' },
  //   ];
  //   httpBackend.whenGET('/person/1').respond(persons[0]);

  //   // Service

  // }));

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('should get the currenly logged in person', function () {
    console.log('XxxxxXX');
    //spyOn(PersonService, 'getPerson').andCallThrough();
    var person = personSvc.getPerson();
    //expect(PersonService.getPerson).t©oHaveBeenCalled();

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
