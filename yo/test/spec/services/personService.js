'use strict';

describe('Service: personService', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var personService;
  beforeEach(inject(function (_personService_) {
    personService = _personService_;
  }));

  it('should do something', function () {
    expect(!!personService).toBe(true);
  });

});
