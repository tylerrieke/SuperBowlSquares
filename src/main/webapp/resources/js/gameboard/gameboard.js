app.controller('Gameboard', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.timesUpValue=0;
    $scope.numberOfRows = 10;
    $scope.numberOfColumns = 10;
    $scope.teamX = "Home";
    $scope.teamY = "Away";
    $scope.players = [];
    $scope.playerIps = [];

    $scope.squares = new Array($scope.numberOfRows);
    for(var i = 0; i < $scope.numberOfRows;i++) {
        $scope.squares[i] = new Array($scope.numberOfColumns);
        for(var j =0; j<$scope.numberOfColumns;j++) {
            $scope.squares[i][j] = {displayName:"",winner:false};
        }
    }

    var getGameboard = function() {
        var onPage = ($location.path()=="/board");

        if(onPage) {
            $http.get("/sbsquares/board").success(function (response) {
                var gameboard = response.gameboard;
                var playSound = false;
                $scope.teamX = gameboard.teamX;
                $scope.teamY = gameboard.teamY;

                if (gameboard.state === "REGISTRATION") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/board-registration';
                    return;
                }

                syncPlayers(gameboard.players);

                for (var i = 0; i < $scope.squares.length; i++) {
                    for (var j = 0; j < $scope.squares[i].length; j++) {
                        var oldSquare = $scope.squares[i][j];
                        var newSquare = gameboard.squares[i][j];
                        if (newSquare.owner) {
                            if(newSquare.owner.display!=oldSquare.displayName) {
                                playSound = true;
                            }
                            oldSquare.displayName = newSquare.owner.display
                        } else {
                            oldSquare.displayName = "";
                        }
                    }
                }

                if (playSound) {
                    playCorrectSound();
                }
            });
        }
        $timeout(getGameboard, (onPage?501:2000));
    };
    getGameboard();

    var incorrectSound = null;

    var playIncorrectSound = function() {
        if(!incorrectSound) {
            incorrectSound = soundManager.createSound({
                url: 'resources/sounds/IncorrectBuzzer.mp3'
            });
        }
        incorrectSound.play();
    }

    var correctSound = null;

    var playCorrectSound = function() {
        if(!correctSound) {
            correctSound = soundManager.createSound({
                url: 'resources/sounds/correctAnswer.mp3'
            });
        }
        correctSound.play();
    }

    var syncPlayers = function(newPlayerList) {
        var toAdd = [];
        var toRemove = [];
        var newPlayerIps = [];
        for(var i=0;i<newPlayerList.length;i++) {
            var index = $scope.playerIps.indexOf(newPlayerList[i].connection.ip);
            if(index==-1) {
                toAdd.push(newPlayerList[i]);
            } else {
                var player = $scope.players[index];
                player.name = newPlayerList[i].name;
                player.display = newPlayerList[i].display;
                player.active = newPlayerList[i].active;
            }
            newPlayerIps.push(newPlayerList[i].connection.ip);
        }
        for(var i=0;i<$scope.playerIps.length;i++) {
            if(newPlayerIps.indexOf($scope.playerIps[i])==-1) {
                toRemove.push($scope.playerIps[i]);
            }
        }
        for(var i=$scope.playerIps.length-1; i>=0; i--) {
            if(toRemove.indexOf($scope.playerIps[i])!=-1) {
                $scope.playerIps.splice(i,1);
                $scope.players.splice(i,1);
            }
        }
        for(var i = 0; i < toAdd.length;i++) {
            $scope.playerIps.push(toAdd[i].connection.ip);
            $scope.players.push(toAdd[i]);
        }
    }
}]);


