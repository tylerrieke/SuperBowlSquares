package com.rieke.bmore.squares.player;

import com.rieke.bmore.squares.admin.AdminService;
import com.rieke.bmore.squares.gameboard.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerFactory playerFactory;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> getPlayer(
            HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("player", playerService.getPlayer(getIpAddr(request)));
        Board gameboard = playerService.getGameboard();
        responseMap.put("gameboard",gameboard);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/squares", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> getPlayerSquares(
            HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        Player player = playerService.getPlayer(getIpAddr(request));
        responseMap.put("squares", player.squares());
        Board gameboard = playerService.getGameboard();
        responseMap.put("teamX",gameboard.getTeamX());
        responseMap.put("teamY",gameboard.getTeamY());
        responseMap.put("state",gameboard.getState());
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> getAll(
            HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("players",adminService.getOrderedPlayers());
        responseMap.put("state",playerService.getGameboard().getState());
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/mark", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> makePick(
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        try {
            playerService.makePick(getIpAddr(request), x, y);
        } catch (InvalidPlayerException e) {
            responseMap.put("error","Square already taken");
        }
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> update(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "display") String display,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        playerService.update(getIpAddr(request),name,display);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/skip", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> skip(
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        playerService.getGameboard().rotatePlayer(getIpAddr(request));
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (null != ip && !"".equals(ip.trim())
                && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (null != ip && !"".equals(ip.trim())
                && !"unknown".equalsIgnoreCase(ip)) {
            // get first ip from proxy ip
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
