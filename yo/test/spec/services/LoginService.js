'use strict';

describe('Service: LoginService', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var LoginService;
  beforeEach(inject(function (_LoginService_) {
    LoginService = _LoginService_;
  }));

  it('should do something', function () {
    expect(!!LoginService).toBe(true);
  });

});
