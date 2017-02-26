app.controller('HostRegistration', ['$scope', '$http', '$timeout', '$location', function($scope, $http, $timeout, $location) {

    $scope.title = "Super Bowl Squares";
    $scope.players = [];
    $scope.playerIps = [];

    var getPlayers= function() {
        var onPage = ($location.path()=="/host-registration");

        if(onPage) {
            $http.get("/sbsquares/player/all").success(function (response) {
                if (response.state !== "REGISTRATION") {
                    $scope.active = false;
                    window.location = '/sbsquares/#/host';
                    return;
                }

                syncPlayers(response.players);

            });
        }
        $timeout(getPlayers, (onPage?501:2000));
    };
    getPlayers();

    $scope.startPicking = function() {
        $http.get("/sbsquares/admin/state?state=PICKING").success(function (response) {});
    };

    $scope.setBlacklisted = function(player) {
        $http.get("/sbsquares/admin/blacklist?ip="+player.connection.ip).success(function (response) {});
    };

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


