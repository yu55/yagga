var App = angular.module('yagga', []);
App.controller('yaggaPreviewCtrl', function ($scope, $location, $http) {
    $scope.params = $location.search();

    annotateRequest = {
        'repository': 'a',
        'file': 'b'
    }

    $http.post('annotate', $scope.params).success(function (data) {
        $scope.annotate = data;
    }).error(function (status) {
        alert("error: " + status);
    });

});

App.directive('prettify', ['$compile', '$timeout', function ($compile, $timeout) {
    return {
        restrict: 'E',
        scope: {
            target: '='
        },
        link: function (scope, element, attrs) {
            var template = element.html();
            var templateFn = $compile(template);
            var update = function () {
                $timeout(function () {
                    var compiled = templateFn(scope).html();
                    var prettified = prettyPrintOne('<pre>' + compiled + '</pre>');
                    element.html(prettified);
                }, 0);
            }
            scope.$watch('target', function () {
                update();
            }, true);
            update();
        }
    };
}]);