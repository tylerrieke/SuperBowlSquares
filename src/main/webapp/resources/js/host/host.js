app.controller('Host', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.timesUpValue=0;
    $scope.numberOfRows = 10;
    $scope.numberOfColumns = 10;
    $scope.teamX = "Home";
    $scope.teamY = "Away";
    $scope.state = "REGISTRATION";
    $scope.players = [""];
    $scope.playerDNs = [""];
    $scope.activePlayerDN = "";
    $scope.states = ["REGISTRATION","PICKING","PAUSED","CLOSED"];

    $scope.squares = new Array($scope.numberOfRows);
    for(var i = 0; i < $scope.numberOfRows;i++) {
        $scope.squares[i] = new Array($scope.numberOfColumns);
        for(var j =0; j<$scope.numberOfColumns;j++) {
            $scope.squares[i][j] = {displayName:"",winner:false,x:i,y:j};
        }
    }

    var getGameboard = function() {
        var onPage = ($location.path()=="/host");

        if(onPage) {
            $http.get("/sbsquares/board").success(function (response) {

                var gameboard = response.gameboard;

                if (gameboard.state === "REGISTRATION") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/host-registration';
                    return;
                }


                var playSound = false;
                $scope.teamX = gameboard.teamX;
                $scope.teamY = gameboard.teamY;
                $scope.state = gameboard.state;
                $scope.activePlayerDN = (gameboard.activePlayer?gameboard.activePlayer.display:"");
                syncPlayers(gameboard.players);

                for (var i = 0; i < $scope.squares.length; i++) {
                    for (var j = 0; j < $scope.squares[i].length; j++) {
                        var oldSquare = $scope.squares[i][j];
                        var newSquare = gameboard.squares[i][j];
                        if (newSquare.owner) {
                            oldSquare.displayName = newSquare.owner.display
                        } else {
                            oldSquare.displayName = "";
                        }
                    }
                }
            });
        }
        $timeout(getGameboard, (onPage?501:2000));
    };
    getGameboard();

    $scope.makeMark = function(player,x,y) {
        var ip = (!player?null:$scope.players[$scope.playerDNs.indexOf(player)].connection.ip);
        $http.get("/sbsquares/admin/mark?x="+x+"&y="+y+(ip?"&ip="+ip:"")).success(function (response) {
        });
    };

    $scope.setState = function(state) {
        $scope.state = state;
        $http.get("/sbsquares/admin/state?state="+state).success(function (response) {
        });
    };

    $scope.activatePlayer = function(player) {
        $http.get("/sbsquares/admin/activate?ip="+player.connection.ip).success(function (response) {});
    };

    var syncPlayers = function(newPlayerList) {
        var toAdd = [];
        var toRemove = [];
        var newPlayerDNs = [];
        for(var i=0;i<newPlayerList.length;i++) {
            if($scope.playerDNs.indexOf(newPlayerList[i].display)==-1) {
                toAdd.push(newPlayerList[i]);
            }
            newPlayerDNs.push(newPlayerList[i].display);
        }
        for(var i=1;i<$scope.playerDNs.length;i++) {
            if(newPlayerDNs.indexOf($scope.playerDNs[i])==-1) {
                toRemove.push($scope.playerDNs[i]);
            }
        }
        for(var i=$scope.playerDNs.length-1; i>=0; i--) {
            if(toRemove.indexOf($scope.playerDNs[i])!=-1) {
                $scope.playerDNs.splice(i,1);
                $scope.players.splice(i,1);
            }
        }
        for(var i = 0; i < toAdd.length;i++) {
            $scope.playerDNs.push(toAdd[i].display);
            $scope.players.push(toAdd[i]);
        }
    }
}]);


