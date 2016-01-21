var nameApp = angular.module('yagga', []);
nameApp.controller('yaggaGrepCtrl', function ($scope, $http) {
    $scope.selected = true;

    $scope.selectAll = function () {
        if ($scope.selected) {
            $scope.multipleSelect = $scope.repositories;
        } else {
            $scope.multipleSelect = [];
        }
    }

    $scope.deselectCheckbox = function () {
        $scope.selected = false;
    }

    $scope.clearFileFilter = function () {
        $scope.fileFilter = "";
    }

    $scope.clearSearchWanted = function () {
        $scope.wanted = "";
    }

    $scope.options = 1;
    $scope.optionsLabel = ['Hide options', 'Show options'];

    $scope.switchOptions = function() {
        $scope.options = ($scope.options + 1) % 2;
    }

    $scope.grep = function () {
        if ($scope.wanted == null || $scope.multipleSelect == null) {
            return false;
        }

        $scope.greps = [];
        $scope.message = "Please wait - search in progress...";

        grepRequest = {
            'wanted': $scope.wanted,
            'repositories': $scope.multipleSelect,
            'ignoreCase': $scope.ignoreCase,
            'fileFilter': $scope.fileFilter,
            'onlyFilename': $scope.onlyFilename
        }
        $http.post('grep', grepRequest).success(function (data) {
            $scope.greps = data;
            if (data.length == 0) {
                $scope.message = "No findings in selected repositories.";
            } else if (data.length >= 1001) {
                $scope.message = "More than 1001 results. Aborting...";
            } else {
                $scope.message = "";
            }
        }).error(function () {
            $scope.message = "Cannot obtain results - something has broken... sorry :(";
        });
    }

    $scope.showPreview = function (repository, file) {
        showPreviewRequest = {
            'repository': repository,
            'file': file
        }
        $http.post('annotate', showPreviewRequest).success(function (data) {
            var newWindow = window.open();
            newWindow.document.write(data);
        }).error(function () {
            alert("Error while opening: " + repository + " / " + file);
        });
    }

    $scope.escape = function (toEscape) {
        return encodeURIComponent(toEscape);
    }

    $http.get('repositories').success(function (data) {
        $scope.repositories = data;
        $scope.selectAll();
    });
});

