'use strict';

describe('Service: personService', function () {

  // API
  var $httpBackend;
  var PersonService, persons;

  // load the service's module
  beforeEach(module('tpsApp'));

  beforeEach(inject(function (_PersonService_) {
    PersonService = _PersonService_;
  }));

  // Init HTTP mock backend
  beforeEach(inject(function($injector) {
  	// Model
  	persons = [
			{ id: 0, username: 'jim@anrellconsulting.com', rate: 0 }
		];
  	$httpBackend = $injector.get("$httpBackend");
  	$httpBackend.whenGET("/person/1").respond(persons);
  }));

  it('set active time entry', function () {
    PersonService.getPerson();
  });

});
