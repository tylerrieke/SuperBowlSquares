app.controller('PlayerRegistration', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.name = "";
    $scope.display = "";
    $scope.editing = false;
    $scope.errorMessage = "";
    $scope.errorCount = 0;
    $scope.editingCount = 0;
    $scope.nameEditing = false;
    $scope.displayEditing = false;
    $scope.maxNameLength = 20;
    $scope.maxDisplayLength = 4;
    $scope.minDisplayLength = 2;

    var getPlayer = function() {
        var onPage = ($location.path()=="/player-registration");

        if(onPage) {
            $http.get("/sbsquares/player").success(function (response) {
                var gameboard = response.gameboard;
                var player = response.player;
                if(!$scope.editing) {
                    $scope.name = player.name;
                    $scope.display = player.display;
                }

                if (gameboard.state === "PICKING" || gameboard.state === "PAUSED") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player';
                    return;
                } else if(gameboard.state === "CLOSED") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player-closed';
                    return;
                }
            });
        }
        $timeout(getPlayer, (onPage?501:2000));
    };
    getPlayer();

    $scope.updatePlayer = function(field) {
        if(field=='name') {
            if($scope.showNameLengthError()) {
                return;
            }
            $scope.nameEditing = false;
        } else {
            if($scope.showDisplayLengthError()) {
                return;
            }
            $scope.displayEditing = false;
        }
        var editingNum = $scope.editingCount;
        $http.get("/sbsquares/player/update?name="+$scope.name+"&display="+$scope.display).success(function (response) {
            if(response.error) {
                $scope.errorMessage = response.error;
                $scope.errorCount++;
                var errorNum = $scope.errorCount;
                $timeout(function(){clearError(errorNum)}, 3000);
            }
            $scope.editing = (editingNum != $scope.editingCount);
        }).error(function() {
            $scope.editing = (editingNum != $scope.editingCount);
        });
    };

    var clearError = function(errorNum) {
        if($scope.errorCount==errorNum) {
            $scope.errorMessage = "";
        }
    };

    $scope.setEditing = function(field) {
        if(field=='name') {
            $scope.nameEditing = true;
        } else {
            $scope.displayEditing = true;
        }

        $scope.editingCount++;
        $scope.editing = true;
    };

    $scope.doBlur = function($event){
        var target = $event.target;
        target.blur();
    }

    $scope.showNameLengthError = function() {
        return !$scope.name || $scope.name.length>$scope.maxNameLength;
    }

    $scope.showDisplayLengthError = function() {
        return !$scope.display || $scope.display.length>$scope.maxDisplayLength || $scope.display.length<$scope.minDisplayLength;
    }
}]);


