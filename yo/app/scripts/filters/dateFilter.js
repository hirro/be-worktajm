'use strict';

angular.module('tpsApp')
  .filter('dateFilter', function () {
    return function (inputArray, selectedDate) {
      console.log('dateFilter %s', selectedDate);
      console.log('input array size %d', inputArray.length);
      var selectedDateInMs = new Date(selectedDate);
      _.forEach(inputArray, function(item) {
        console.log(item.id, new Date(item.startTime).toJSON());
      });
      return inputArray;
    };
  });
