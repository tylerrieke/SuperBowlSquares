package com.rieke.bmore.squares.admin;

import com.rieke.bmore.squares.player.InvalidPlayerException;
import com.rieke.bmore.squares.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/mark", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> makePick(
            @RequestParam(value = "x") int x,
            @RequestParam(value = "y") int y,
            @RequestParam(value = "ip", required = false) String ip,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.setSquareOwner(x,y,ip);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> setState(
            @RequestParam(value = "state") String state,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.setState(state);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/skip", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> setSkip(
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.getGameboard().rotatePlayer();
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{ips}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> setOrder(
            @PathVariable("ips") String ips,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.setPlayerOrder(Arrays.asList(ips.split(",")));
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/blacklist", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> setBlacklist(
            @RequestParam(value = "ip") String ip,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.setBlackListed(ip);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> activatePlayer(
            @RequestParam(value = "ip") String ip,
            HttpServletRequest request) throws InvalidPlayerException {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        adminService.activatePlayer(ip);
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }

}

