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

/*globals  _, $, $q */

'use strict';

// This service should encapsulate all data access. 
// A future extension could be offline support.
angular.module('tpsApp')
  .service('TimerService', function TimerService(Restangular, $rootScope, $q, PersonService) {
    var svc;
    var baseProjects = Restangular.all('project');
    var projects = [];
    var projectsLoaded = false;
    var baseTimeEntries = Restangular.all('timeEntry');
    var selectedDate = new Date().toISOString().substring(0, 10);
    var timeEntries;    

    svc = {

      //
      // Refresh project list from server
      getProjects: function () {
        return projects;
      },

      //
      // Get all available project for the given person
      // No server reload done
      // 
      reloadProject: function () {
        console.log('Loading projects');
        var q = baseProjects.getList();
        q.then(function (result) {
          console.log('Projects retrieved from backend, size: %d', result.length);
          projects = result;
          projectsLoaded = true;
          var activeProjectId = PersonService.getActiveProjectId();
          _(projects).each(function (project) {
            if (project.id === activeProjectId) {
              project.active = true;
            } else {
              project.active = false;
            }
          });

          // Notify all listeners that project list has been refreshed
          console.log('Sending event - projectsRefreshed');
          $rootScope.$broadcast('onProjectsRefreshed', projects);
          return result;
        });
        return q;
      },

      //
      // CRUD for Project
      updateProject: function (project) {
        if (project.id >= 0) {
          console.log('updateProject - update');
          project.put().then(function () {
            console.log('updateProject - Backend updated successfully');
            $rootScope.$broadcast('onProjectUpdated', project);
          });
        } else {
          console.log('updateProject - creating new entry');
          baseProjects.post(project).then(function (newProject) {
            console.log('Updated project successfully at backend. New id is: %s', newProject.id);
            projects.push(newProject);
            $rootScope.$broadcast('onProjectUpdated', newProject);
          });
        }
      },
      removeProject: function (project) {
        console.log('remove project(name: %s, id: %d)', project.name, project.id);
        project.remove().then(function () {
          console.log('Project deleted from backend');
          var index = _.indexOf(projects, project);
          console.log('Removing project at index %d', index);
          projects.splice(index, 1);
          //projects = _.without(projects, project);
        });
      },
      getProject: function (id) {
        console.log('Finding project with id %d', id);
        var item = _.find(projects, function (p) {
          return p.id === id;
        });
        return item;
      },

      //
      // Set project status
      // Only one project may be active at the time.
      setActive: function (project, active) {
        console.log('setActive - %d', active);
        var p = this.getProject(project.id);
        if (p) {
         p.active = active;        
        } else {
          console.error('Failed to find project to set as active');
        }
      },
      setSelectedDate: function ( date ) {
        selectedDate = date;
      },
      getSelectedDate: function () {
        return selectedDate;
      },

      //
      // CRUD for time entries
      //
      getTimeEntries: function () {
        console.log('TimerService::getTimeEntries');
        var q = baseTimeEntries.getList();
        q.then(function (result) {
          console.log('List of time entries retrieved from backend, size: %d', result.length);
          timeEntries = result;
          $rootScope.$broadcast('onTimeEntriesRefreshed', timeEntries);
          return timeEntries;
        });
        return q;
      },
      updateTimeEntry: function (timeEntry) {
        var deferred = $q.defer();
        var restangularTimeEntry = svc.getTimeEntryById(timeEntry.id);
        if (restangularTimeEntry) {
          restangularTimeEntry.put().then(function (result) {
            console.log('Time entry updated at backend');
            deferred.resolve(result);
          }, function () {
            var errorMsg = 'Failed to update the time entry';
            console.error(errorMsg);
            deferred.reject(errorMsg);
          });
        } else {
          var errorMsg = 'Failed to find time entry';
          console.error(errorMsg);
          deferred.reject(errorMsg);
        }
        console.log('Promise for updateTimeEntry returned');
        return deferred.promise;        
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
      //
      // CRUD time entries - END
      //

      // XXX: Only id is required
      startTimer: function(project) {
        console.log('startTimer');
        var person = PersonService.getPerson();
        var timeEntry = { person: person, project: project, startTime: $.now()};
        var q = baseTimeEntries.post(timeEntry);
        q.then(function (newTimeEntry) {
          console.log('startTimer - Time entry created');
          newTimeEntry.active = true;
          svc.setActive(project, true);
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

      // XXX: Active project can be derived from person
      // XXX: Use promise so start timer can depend on it...
      stopTimer: function() {
        console.log('stopTimer');
        PersonService.getPerson().then(function (result) {
          var person = result;
          var timeEntry = result.activeTimeEntry;
          var project = timeEntry.project;
          if (person && timeEntry && project) {
            timeEntry.endTime = $.now();
            timeEntry.put().then(function () {
              console.log('stopTimer - Time entry updated at backend');
              PersonService.setActiveTimeEntry(null);
            });
          } else {
            console.log('This person has no active timer');
          }
        });
      }
    };
    return svc;
  });
