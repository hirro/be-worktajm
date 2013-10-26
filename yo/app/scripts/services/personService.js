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

'use strict';

// This service handle operations for the currently logged in user.
angular.module('tpsApp')
  .service('PersonService', function PersonService($rootScope, $q, Restangular) {
    var person = null;
    var svc;
    svc = {

      /**
       * Returns the logged in person.
       * If person has not been loaded before it will be retrieved from backend.
       * @return promise to Person.
       */
      getPerson: function () {
        console.log('PersonService:getPerson');

        var deferred = $q.defer();

        if (person) {
          deferred.resolve(person);
        } else {
          console.log('Person not loaded');
          var qPerson = Restangular.one('person', 1).get();
          qPerson.then(function (p) {
            console.log('PersonService:getPerson - Backend responded %s', p.username);
            person = p;
            // XXX: Ugly
            $rootScope.person = p;
            return deferred.resolve(p);
          }, function () {
            console.error('Failed to load the person from backend');
            return deferred.reject('Failed to load project from backend');
          });
        }
        return deferred.promise;        
      },

      /**
       * Sets the logged in person as actively running the provided time entry.
       * @return promise to the person.
       */
      setActiveTimeEntry: function (timeEntry) {
        var deferred = $q.defer();
        console.log('Updating person, id %d', person.id);
        var stoppedProject = person.activeTimeEntry ? person.activeTimeEntry.project : null;
        var startedProject = timeEntry ? timeEntry.project : null;
        person.activeTimeEntry = timeEntry;
        person.put().then(function (result) {
          // Signal that the project status has changed.
          if (stoppedProject) {
            $rootScope.$broadcast('onProjectUpdated', stoppedProject);
          }
          if (startedProject) {
            $rootScope.$broadcast('onProjectUpdated', startedProject);
          }
          return deferred.resolve(result);
        }, function (reason) {
          console.error('setActiveTimeEntry failed. %s', reason);
          return deferred.reject(reason);
        });
        return deferred.promise;
      },

      /**
       * Returns the active time entry of the logged in person.
       * @return active time entry of logged in person, null if not active.
       */
      getActiveTimeEntry: function () {
        var timeEntry = null;
        if (person && person.activeTimeEntry) {
          timeEntry = person.activeTimeEntry;
        }
        return timeEntry;
      },

      /** Returns the id if the active project.
       * @return id of the active project, -1 if no project is active.
       */
      getActiveProjectId: function () {
        var result = -1;
        if (person &&
            person.activeTimeEntry &&
            person.activeTimeEntry.project) {
          console.log('getActiveProjectId - %d', person.activeTimeEntry.project.id);
          result = person.activeTimeEntry.project.id;
        } else {
          console.log('No active project');
        }
        return result;
      }
    };
    return svc;
  });
