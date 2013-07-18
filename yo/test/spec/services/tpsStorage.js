'use strict';

describe('Service: tpsStorage', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var tpsStorage;
  beforeEach(inject(function (_tpsStorage_) {
    tpsStorage = _tpsStorage_;
  }));

  it('should do something', function () {
    expect(!!tpsStorage).toBe(true);
  });

});
