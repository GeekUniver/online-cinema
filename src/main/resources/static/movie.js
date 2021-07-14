angular.module('app', []).controller('movieController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/onlineCinema/api/v1';

     $scope.fillTable = function () {
         $http.get(contextPath + '/movies')
             .then(function (response) {
                 console.log(response);
                 $scope.MoviesList = response.data;
             });
     };

    $scope.submitCreateNewMovie = function () {
        $http.post(contextPath + '/movies', $scope.newMovie)
            .then(function (response) {
                $scope.newMovie = null;
                $scope.fillTable();
            });
    };

    $scope.deleteMovieById = function (movieId) {
        $http.delete(contextPath + '/movies/' + movieId)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.fillTable();
});