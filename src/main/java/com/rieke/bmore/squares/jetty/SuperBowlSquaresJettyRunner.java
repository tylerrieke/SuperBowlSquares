package com.rieke.bmore.squares.jetty;

import com.rieke.bmore.squares.admin.AdminService;
import com.rieke.bmore.squares.gameboard.Board;
import com.rieke.bmore.squares.gameboard.Team;
import com.rieke.bmore.squares.player.PlayerService;
import com.rieke.jettylauncher.JettyRunner;

public class SuperBowlSquaresJettyRunner extends JettyRunner{

    static {
        CONTEXT_PATH = "/sbsquares";
    }

    @Override
    protected String getWebappPath() {
        return SuperBowlSquaresJettyRunner.class.getClassLoader().getResource("webapp").toString();
    }

    @Override
    protected String getWebdefaultPath() {
        return SuperBowlSquaresJettyRunner.class.getClassLoader().getResource("webapp/WEB-INF/webdefault.xml").toString();
    }

    public void afterDeployed(AdminService adminService, PlayerService playerService) {
        Board gameboard = new Board(new Team("Patriots","blue",""),new Team("Falcons","black",""));
        adminService.setGameboard(gameboard);
        playerService.setGameboard(gameboard);
        super.afterDeployed();
    }
}
