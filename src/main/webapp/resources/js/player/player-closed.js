app.controller('PlayerClosed', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.name = "";
    $scope.display = "";
    $scope.teamX = "Home";
    $scope.teamY = "Away";
    $scope.squares = [];

    var getPlayer = function() {
        var onPage = ($location.path()=="/player-closed");

        if(onPage) {
            $http.get("/sbsquares/player/squares").success(function (response) {
                $scope.state = response.state;
                $scope.teamX = response.teamX.name;
                $scope.teamY = response.teamY.name;

                if ($scope.state === "PICKING" || $scope.state === "PAUSED") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player';
                    return;
                } else if($scope.state === "REGISTRATION") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player-registration';
                    return;
                }
                syncSquares(response.squares);
            });
        }
        $timeout(getPlayer, (onPage?3000:6000));
    };
    getPlayer();

    var syncSquares = function(newSquares) {
        var toRemove=[];
        var toAdd=[];
        for(var j=0;j<$scope.squares.length;j++) {
            var found = false;
            for(var i=0;i<newSquares.length;i++) {
                if(newSquares[i].x==$scope.squares[j].x && newSquares[i].y==$scope.squares[j].y) {
                    found=true;
                    break
                }
            }
            if(!found) {
                toRemove.push(i);
            }
        }
        for(var i=toRemove.length-1;i>=0;i--) {
            $scope.squares.splice(toRemove[i],1);
        }
        for(var i=0;i<newSquares.length;i++) {
            var found = false;
            for(var j=0;j<$scope.squares.length;j++) {
                if(newSquares[i].x==$scope.squares[j].x && newSquares[i].y==$scope.squares[j].y) {
                    found=true;
                    break
                }
            }
            if(!found) {
                toAdd.push(newSquares[i]);
            }
        }
        for(var i=0;i<toAdd.length;i++) {
            $scope.squares.push(toAdd[i]);
        }

    };
}]);


