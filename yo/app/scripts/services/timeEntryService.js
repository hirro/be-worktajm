/*globals angular, _, $, $filter */

'use strict';

// This service manages time entries.
// It has the following responsibilities
// * Communicate with backend when neccessary.
// * Keep track of object references so that data binding is valid in all controllers using each time entry instance.
// * Keep track of synchronization status, possible to change appearance of time entry if it has not been persisted yet (slow or offline).
// * 
angular.module('tpsApp')
  .service('TimeEntryService', function TimeEntryService($resource, $rootScope, Restangular) {
    var svc;
    var baseTimeEntries = Restangular.all('timeEntry');
    var selectedDate = new Date().toISOString().substring(0, 10);
    var timeEntries;

    svc = {

      selectedDate: function ( date ) {
        selectedDate = date;
      },

      // Gets the time entries for the currently selected date
      getTimeEntries: function () {
        console.log('getTimeEntries');
        var q = baseTimeEntries.getList();
        return q.then(function (result) {
          console.log('List of time entries retrieved from backend, size: %d', result.length);
          timeEntries = result;
          $rootScope.$broadcast('onTimeEntriesRefreshed', timeEntries);
          return timeEntries;
        });
      },

      removeTimeEntry: function (timeEntry) {
        var id = timeEntry.id;
        console.log('removeTimeEntry(%s)', id);

        var q = timeEntry.remove();
        q.then(function () {
          console.log('Time entry deleted from backend');
          timeEntries = _.without(timeEntries, timeEntry);
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
        if (timeEntry.endTime !== null) {
          result = $filter('date')(timeEntry.endTime, 'HH:mm:ss');
        }
        return result;
      },

      updateTimeEntries:  function() {
        console.log('updateTimeEntries');
      },

      startTimer: function(person, project) {
        console.log('Starting timer');
        var timeEntry = { person: person, project: project, startTime: $.now()};
        var q = baseTimeEntries.post(timeEntry);
        q.then(function (newTimeEntry) {
          console.log('Time entry created');
          newTimeEntry.active = true;
          timeEntries.push(newTimeEntry);
          person.activeTimeEntry = newTimeEntry;
          person.put().then( function () {
            console.log('startTimer - Person updated in backend');
            $rootScope.$broadcast('onTimeEntryUpdated', newTimeEntry);
          });
        }, function () {
          console.error('Failed to add time entry');
        });
        return q;
      },

      stopTimer: function(person, project) {
        console.log('stopTimer');
        if (person) {
          if (person.activeTimeEntry) {
            var timeEntryId = person.activeTimeEntry.id;
            console.log('Stopping timer with id %d', timeEntryId);
            // Refresh from db, promises?
            var timeEntry = this.getTimeEntryById(timeEntryId);
            if (timeEntry) {
              timeEntry.endTime = $.now();
              timeEntry.put().then(function () {
                console.log('Time entry updated');
                person.activeTimeEntry = null;
                person.put().then(function () {
                  console.log('Person is inactive in database');
                  project.active = false;
                });
              });
            } else {
              console.log('Failed to locate entry');
            }
          } else {
            console.log('No active time entry for person %s', person.username);
          }
        } else {
          console.log('Person is null');
        }
      }
    };
    
    return svc;
  });
