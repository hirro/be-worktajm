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
/*globals angular, _, $ */

'use strict';

// This service manages time entries.
// It has the following responsibilities
// * Communicate with backend when neccessary.
// * Keep track of object references so that data binding is valid in all controllers using each time entry instance.
// * Keep track of synchronization status, possible to change appearance of time entry if it has not been persisted yet (slow or offline).
// * 
angular.module('tpsApp')
  .service('TimeEntryService', function TimeEntryService($resource, $rootScope, Restangular, PersonService, ProjectService) {
    var svc;
    var baseTimeEntries = Restangular.all('timeEntry');
    var selectedDate = new Date().toISOString().substring(0, 10);
    var timeEntries;

    svc = {

      setSelectedDate: function ( date ) {
        selectedDate = date;
      },
      getSelectedDate: function () {
        return selectedDate;
      },

      //  Gets the time entries for the currently selected date
      getTimeEntries: function () {
        console.log('TimeEntryService::getTimeEntries');
        var q = baseTimeEntries.getList();
        q.then(function (result) {
          console.log('List of time entries retrieved from backend, size: %d', result.length);
          timeEntries = result;
          $rootScope.$broadcast('onTimeEntriesRefreshed', timeEntries);
          return timeEntries;
        });
        return q;
      },

      removeTimeEntry: function (entry) {
        var id = entry.id;
        var timeEntry = this.getTimeEntryById(id);
        var index = _.indexOf(timeEntries, timeEntry);
        console.log('removeTimeEntry(%s)', id);

        var q = timeEntry.remove();
        q.then(function () {
          console.log('Time entry deleted from backend');
          timeEntries.splice(index, 1);
          $rootScope.$broadcast('onTimeEntryRemoved', timeEntry);
        });

        // Mark time entry as invalid until it is physically removed
        timeEntry.disable = true;
        return q;
      },

      getTimeEntryById: function (id) {
        return _(timeEntries).find({
          'id': id
        });
      },

      getEndTime: function (timeEntry) {
        var result = 'In Progress';
        if (timeEntry.endTime) {
          console.log('End time %d', timeEntry.endTime);
          result = new Date(timeEntry.endTime).toISOString().substring(0, 10);
          console.log(result);
        }
        return result;
      },

      startTimer: function(project) {
        console.log('startTimer');
        var person = PersonService.getPerson();
        var timeEntry = { person: person, project: project, startTime: $.now()};
        var q = baseTimeEntries.post(timeEntry);
        q.then(function (newTimeEntry) {
          console.log('startTimer - Time entry created');
          newTimeEntry.active = true;
          ProjectService.setActive(project, true);
          timeEntries.push(newTimeEntry);

          // Update person with information that 
          PersonService.setActiveProjectId(project.id);
          PersonService.setActiveTimeEntry(newTimeEntry).then( function () {
            console.log('startTimer - Person updated in backend, now sending events');
            $rootScope.$broadcast('onTimeEntryUpdated', newTimeEntry);
            $rootScope.$broadcast('onProjectUpdated', project);
          });
        }, function () {
          console.error('startTimer - Failed to add time entry');
        });
        return q;
      },

      stopTimer: function(project) {
        console.log('stopTimer');
        var person = PersonService.getPerson();
        if (person) {
          if (person.activeTimeEntry) {
            var timeEntryId = person.activeTimeEntry.id;
            console.log('stopTimer - Stopping active time entry with id %d', timeEntryId);
            // Refresh from db, promises?
            var timeEntry = this.getTimeEntryById(timeEntryId);
            if (timeEntry) {
              timeEntry.endTime = $.now();
              timeEntry.put().then(function () {
                console.log('stopTimer - Time entry updated');
                person.activeTimeEntry = null;
                person.put().then(function () {
                  console.log('stopTimer - Person is inactive in database');
                  ProjectService.setActive(project, false);
                  //$rootScope.$broadcast('onProjectUpdated', project);
                });
              });
            } else {
              console.error('stopTimer - Failed to locate entry');
            }
          } else {
            console.error('stopTimer - No active time entry for person %s', person.username);
          }
        } else {
          console.error('stopTimer - Person is null');
        }
      }
    };
    
    return svc;
  });
