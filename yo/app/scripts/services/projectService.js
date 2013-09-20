'use strict';

// This service should encapsulate all data access. 
// A future extension could be offline support.
angular.module('tpsApp')
	.service('ProjectService', function Projectservice(Restangular) {
		var svc;
		var baseProjects = Restangular.all('project');
		var projects = baseProjects.getList();

		svc = {
			// Get all available project for the given person
			// No server reload done
			getAll: function() {
				return projects;
			},
			// Refresh project list from server
			reloadAll: function() {
				baseProjects.getList().then( function(result) {
					projects = result;
				});
			},
			// Update or create project
			update: function(project) {
	      if (project.id >= 0) {
	        console.log('updateProject - update');
	        project.put();
	      } else {
	        console.log('updateProject - create');
	        baseProjects.post(project).then(function (newProject) {
	          this.projects.push(newProject);
	        });
				}
			},
			// Find project in the cached project list
			getProject: function(id) {
				console.log('Finding project with id %d', id);
	      var item = $.grep($scope.projects, function (e) { return e.id === id; })[0];
	      return item;
			},
			start: function (project) {
				console.log('Starting project');
				project.isActive = true;
				// Create open time entry
				
			}
		};
		return svc;
	});
