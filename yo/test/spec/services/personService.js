/* globals expect, $httpBackend, it, beforeEach, describe, inject */

'use strict';

describe('Service: personService', function () {

  var svc,
      httpBackend,
      scope,
      restangular;

  // Test constants
  var persons = [
    { id: 1, username: 'User A'},
    { id: 2, username: 'User B'}
  ];

  // load the service's module
  beforeEach(module('tpsApp'));

  beforeEach(inject(function(Restangular, _$httpBackend_){
    restangular = Restangular;
    $httpBackend = _$httpBackend_;
}));

  // Inject the wanted responses into httpBackend
  beforeEach(inject(function ($httpBackend, PersonService) {
    svc = PersonService;
    httpBackend = $httpBackend;
    // scope = $rootScope;
    // restangular = Restangular;
  }));

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it('should get the currenly logged in person', function () {
    console.log('bbbbb');
    httpBackend.expectGET('http://localhost:8080/api/api/person/1').respond(persons[0]);

    spyOn(svc, 'getPerson').andCallThrough();
    var person = svc.getPerson();
    restangular.one('person', 1).get();
    expect(svc.getPerson).toHaveBeenCalled();

    httpBackend.flush();

    // var resolvedValue;
    // person.then(function (pr) {
    //   resolvedValue = pr;
    // });

    //httpBackend.flush();
    // //person.resolve();
    // expect(person).toBeDefined();
    // expect(person.id).toBe(1);
    // expect(person.username).toBeDefined();
    // expect(person.username).toBe('alice@example.com');
  });

});
