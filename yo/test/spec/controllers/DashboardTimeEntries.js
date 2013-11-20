/*
  @licstart The following is the entire license notice for the 
            JavaScript code in this page.
  @source TBD

  Copyright (C) 2013 Jim Arnell.

  The JavaScript code in this page is free software: you can
  redistribute it and/or modify it under the terms of the GNU
  General Public License (GNU GPL) as published by the Free Software
  Foundation, either version 3 of the License, or (at your option)
  any later version.  The code is distributed WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE.  See the GNU GPL for more details.

  As additional permission under GNU GPL version 3 section 7, you
  may distribute non-source (e.g., minimized or compacted) forms of
  that code without the copy of the GNU GPL normally required by
  section 4, provided you include this license notice and a URL
  through which recipients can access the Corresponding Source.

  @licend The above is the entire license notice
          for the JavaScript code in this page.  
*/
/*globals describe, it, beforeEach, inject, expect, spyOn */

'use strict';

describe('Controller: DashboardTimeEntriesCtrl', function () {
  // load the controller's module
  beforeEach(module('tpsApp'));

  var DashboardTimeEntriesCtrl, scope;
  var projects = [
    {'id': 1, 'name': 'Project A', 'description': null, 'rate': null, 'new': false},
    {'id': 2, 'name': 'Project B', 'description': null, 'rate': null, 'new': false},
    {'id': 3, 'name': 'Project C', 'description': null, 'rate': null, 'new': false}
  ];
  var timeEntries = [
    {id: 1, startTime: 2, endTime: 3},
    {id: 2, startTime: 2, endTime: 3}
  ];

  // Initialize the TimerServiceMock
  var TimerServiceMock = {
    remove: function (project) {
      console.log('TimerServiceMock:remove called');
    },
    refresh: function () {
      console.log('TimerServiceMock:refresh called');
    },
    update: function () {
      console.log('TimerServiceMock:update called');
    }
  };

  // Initialize the TimerServiceMock
  var TimerServiceMock = {
    startTimer: function () {
      console.log('TimerServiceMock:startTimer called');
    },
    stopTimer: function () {
      console.log('TimerServiceMock:stopTimer called');
    },
    getTimeEntries: function () {
      console.log('TimerServiceMock::getTimeEntries');
      return null;
    },
    removeTimeEntry: function(timeEntry) {
      //
    }
  };

  // Initialize the PersonServiceMock
  var activeProjectId = -1;
  var PersonServiceMock = {
    getActiveProjectId: function () {
      return activeProjectId;
    }
  };

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector) {
    scope = $rootScope.$new();
    DashboardTimeEntriesCtrl = $controller('DashboardTimeEntriesCtrl', {
      $scope: scope,
      PersonService: PersonServiceMock,
      TimerService: TimerServiceMock
    });
    var project = [];
    DashboardTimeEntriesCtrl.$inject = ['$scope',  '$route', 'ProjectServic', 'PersonService', 'TimerService'];
  }));

  describe('General tests', function () {
    it('it should format the end time properly for a time entry', function () {
      var endTime = scope.getEndTime(timeEntries[0]);
      expect(endTime).toBe('01:00:00');
    });

    it('should return the time difference in a formatted string', function () {
      var timeEntry = {
        startTime: 0,
        endTime: 1000*34 + 1000*60*24 + 1000*60*60*3
      };
      var duration = scope.getDuration(timeEntry);
      expect(duration).toBe('03:24');
    });

    it('should return the time difference in a formatted string', function () {
      var timeEntry = {
        startTime: 0,
        endTime: 1000*34 + 1000*60*24
      };
      var duration = scope.getDuration(timeEntry);
      expect(duration).toBe('00:24');
    });

    it('should not return the time entry with id 1 when not yet initialized', function () {
      var timeEntry = scope.findTimeEntryById(1);
      expect(timeEntry).not.toBeDefined();
    });

    it('should return the time entry with the provider id', function () {
      var timeEntry = scope.findTimeEntryById(1);
      expect(timeEntry).not.toBeDefined();
      scope.$broadcast('onTimeEntriesRefreshed', timeEntries);
      scope.$digest();
      timeEntry = scope.findTimeEntryById(1);
      expect(timeEntry).toBeDefined();
    });

    it('should remove the time entry', function () {
      var timeEntry = scope.findTimeEntryById(1);
      spyOn(TimerServiceMock, 'removeTimeEntry').andCallThrough();
      expect(timeEntry).not.toBeDefined();
      scope.$broadcast('onTimeEntriesRefreshed', timeEntries);
      scope.$digest();
      timeEntry = scope.findTimeEntryById(1);
      expect(timeEntry).toBeDefined();
      scope.removeTimeEntry(timeEntry);
      expect(TimerServiceMock.removeTimeEntry).toHaveBeenCalledWith(timeEntry);
    });
  });

});
