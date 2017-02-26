package com.rieke.bmore.squares;

import com.rieke.bmore.squares.admin.AdminService;
import com.rieke.bmore.squares.gui.SuperBowlSquaresStartup;
import com.rieke.bmore.squares.player.PlayerService;

import javax.annotation.PostConstruct;

public class SuperBowlSquares {
    private AdminService adminService;
    private PlayerService playerService;

    public SuperBowlSquares(AdminService adminService, PlayerService playerService) {
        this.adminService = adminService;
        this.playerService = playerService;
    }

    @PostConstruct
    public void start() {
        SuperBowlSquaresStartup.jettyServer.afterDeployed(adminService,playerService);
    }
}
