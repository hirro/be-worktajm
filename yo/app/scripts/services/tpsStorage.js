/*globals angular, localStorage, $ */

'use strict';

angular.module('tpsApp')
  .service('tpsStorage', function tpsStorage($resource) {
    var PROJECTS_STORAGE_ID = 'projects';
    var mock = false;
    var projectsResource = $resource('http://localhost\\:8080/tps/project/list');

    return {
      getProjects: function () {
        var projects;
        if (mock) {
          projects = JSON.parse(localStorage.getItem(PROJECTS_STORAGE_ID) || '[]');
          if (projects.length === 0) {
            // Hack to just get some data
            projects = [
              { 'id': '1', 'name': 'SALC', 'customerName': 'Giesecke & Devrient 3S AB', 'rate': '750'},
              { 'id': '2', 'name': 'Licentio', 'customerName': 'Giesecke & Devrient 3S AB', 'rate': '750'},
              { 'id': '3', 'name': 'Slacking', 'customerName': 'Giesecke & Devrient 3S AB', 'rate': '750'}
            ];
            localStorage.setItem(PROJECTS_STORAGE_ID, JSON.stringify(projects));
          }
        } else {
          console.log('Retrieving projects from server');
          projects = projectsResource.query();
          console.log('Successfully loaded projects, object = %s', projects);
          $.each(projects).each(function (idx, el) {
            console.log('Project %s %s', idx, el);
            el.someEventHandler(function(){  
            }); 
          });
        }
        return projects;
      },
      getProjectsFromLocal: function () {
        return JSON.parse(localStorage.getItem(PROJECTS_STORAGE_ID) || '[]');
      },
      getTimeEntries: function () {
        var timeEntries = [
          { id: '1', projectId: '1', startTime: '10:00:00', endTime: '10:30:00', comment: '' },
          { id: '2', projectId: '2', startTime: '11:00:00', endTime: '11:30:00', comment: '' },
          { id: '3', projectId: '1', startTime: '13:00:00', endTime: '15:30:00', comment: '' }
        ];
        return timeEntries;
      },
      putProjects: function (projects) {
        localStorage.setItem(PROJECTS_STORAGE_ID, JSON.stringify(projects));
      }

    };
  });
