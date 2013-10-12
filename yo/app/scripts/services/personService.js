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
  .service('PersonService', function PersonService($rootScope, Restangular) {
    var person;
    var svc;
    svc = {
      // Get currently logged in person
      getPerson: function () {
        console.log('PersonService:getPerson')
        person = Restangular.one('person', 1).get();
        person.then(function (p) {
          console.log('PersonService:getPerson - Backend responded %s', person.toString());
          person = p;
          $rootScope.person = person;
          return p;
        }, function () {
          console.error('Failed to load the person from backend');
        });
        return person;
      },
      setActiveTimeEntry: function (timeEntry) {
        console.log('Updating person, id %d', person.id);
        person.activeTimeEntry = timeEntry;
        return person.put();
      },
      setActiveProjectId: function (id) {
        if (person) {
          
        }
      },
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
