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

/*globals  _ */

'use strict';

// This service should encapsulate all data access. 
// A future extension could be offline support.
angular.module('tpsApp')
  .service('ProjectService', function ProjectService(Restangular, $rootScope, PersonService) {
    var svc;
    var baseProjects = Restangular.all('project');
    var projects = [];
    var projectsLoaded = false;

    svc = {
      //
      // Refresh project list from server
      getAll: function () {
        return projects;
      },
      //
      // Get all available project for the given person
      // No server reload done
      refresh: function () {
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
      // Update or create project
      update: function (project) {
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
      remove: function (project) {
        console.log('remove project(name: %s, id: %d)', project.name, project.id);
        project.remove().then(function () {
          console.log('Project deleted from backend');
          var index = _.indexOf(projects, project);
          console.log('Removing project at index %d', index);
          projects.splice(index, 1);
          //projects = _.without(projects, project);
        });
      },
      //
      // Find project in the cached project list
      get: function (id) {
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
        var p = this.get(project.id);
        if (p) {
         p.active = active;        
        } else {
          console.error('Failed to find project to set as active');
        }
      }

    };
    return svc;
  });
