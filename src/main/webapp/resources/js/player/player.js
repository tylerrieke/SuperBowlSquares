app.controller('Player', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.numberOfRows = 10;
    $scope.numberOfColumns = 10;
    $scope.teamX = "Home";
    $scope.teamY = "Away";
    $scope.name = "Name";
    $scope.homeScores = [0,1,2,3,4,5,6,7,8,9];
    $scope.awayScores = [0,1,2,3,4,5,6,7,8,9];
    $scope.homeScore = 0;
    $scope.awayScore = 0;
    $scope.activePlayer = false;
    $scope.state = "PICKING";
    $scope.errorMessage = "";

    $scope.squares = new Array($scope.numberOfRows);
    for(var i = 0; i < $scope.numberOfRows;i++) {
        $scope.squares[i] = new Array($scope.numberOfColumns);
        for(var j =0; j<$scope.numberOfColumns;j++) {
            $scope.squares[i][j] = {available:true};
        }
    }

    var getGameboardPlayer = function() {
        var onPage = ($location.path()=="/player");

        if(onPage) {
            $http.get("/sbsquares/player").success(function (response) {
                var gameboard = response.gameboard;
                var player = response.player;
                $scope.teamX = gameboard.teamX;
                $scope.teamY = gameboard.teamY;
                $scope.name = player.name;
                $scope.activePlayer = player.active;
                $scope.state = gameboard.state;

                if ($scope.state === "REGISTRATION") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player-registration';
                    return;
                } else if(gameboard.state === "CLOSED") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/player-closed';
                    return;
                }

                for (var i = 0; i < $scope.squares.length; i++) {
                    for (var j = 0; j < $scope.squares[i].length; j++) {
                        $scope.squares[i][j].available = !gameboard.squares[i][j].player;
                    }
                }
            });
            $timeout(getGameboardPlayer, (onPage?501:2000));
        }
    };
    getGameboardPlayer();

    $scope.homeScoreSelected = function(score) {
        $scope.homeScore = score;
    };

    $scope.awayScoreSelected = function(score) {
        $scope.awayScore = score;
    };

    $scope.makeMark = function() {
        $http.get("/sbsquares/player/mark?x="+$scope.homeScore+"&y="+$scope.awayScore).success(function (response) {
            $scope.errorMessage = response.error;
        });
    };

    $scope.skip = function() {
        $http.get("/sbsquares/player/skip").success(function (response) {
            $scope.errorMessage = response.error;
        });
    };

    $scope.isPicking = function() {
        return $scope.activePlayer && $scope.state=="PICKING";
    };
}]);


