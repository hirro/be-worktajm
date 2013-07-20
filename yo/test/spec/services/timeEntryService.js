'use strict';

describe('Service: timeEntryService', function () {

  // load the service's module
  beforeEach(module('tpsApp'));

  // instantiate service
  var timeEntryService;
  beforeEach(inject(function (_timeEntryService_) {
    timeEntryService = _timeEntryService_;
  }));

  it('should do something', function () {
    expect(!!timeEntryService).toBe(true);
  });

});
