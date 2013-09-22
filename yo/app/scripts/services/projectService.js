/*globals $scope */

'use strict';

// This service should encapsulate all data access. 
// A future extension could be offline support.
angular.module('tpsApp')
  .service('ProjectService', function ProjectService(Restangular, $rootScope, PersonService) {
    var svc;
    var baseProjects = Restangular.all('project');
    var projects = {};
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

          // Notify all listeners that project list has been refreshed
          console.log('Sending event - projectsRefreshed');
          $rootScope.$broadcast('onProjectsRefreshed', projects);
        });
      },
      //
      // Update or create project
      update: function (project) {
        if (project.id >= 0) {
          console.log('updateProject - update');
          project.put().then(function (result) {
            console.log('updateProject - Backend updated successfully');
            $rootScope.$broadcast('onProjectUpdated', project);
          });
        } else {
          console.log('updateProject - create');
          baseProjects.post(project).then(function (newProject) {
            this.projects.push(newProject);
            $rootScope.$broadcast('onProjectUpdated', newProject);
          });
        }
      },
      remove: function (project) {
        console.log('remove project(name: %s, id: %d)', project.name, project.id);
        project.remove().then(function () {
          console.log('Project deleted from backend');
          $scope.projects = _.without($scope.projects, project);
        });
      },
      //
      // Find project in the cached project list
      get: function (id) {
        console.log('Finding project with id %d', id);
        var item = $.grep($scope.projects, function (e) { return e.id === id; })[0];
        return item;
      },
      //
      // Set project status
      // Only one project may be active at the time.
      setActive: function (project, active) {
        console.log('Starting project');
        project.isActive = active;
        // Create open time entry       
      }

    };
    return svc;
  });
