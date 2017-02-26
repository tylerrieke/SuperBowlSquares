package com.rieke.bmore.squares.gameboard;

import com.rieke.bmore.squares.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class GameboardController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> getGameBoard(
            HttpServletRequest request) {
        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("gameboard", adminService.getGameboard());
        return new ResponseEntity<>(responseMap,
                HttpStatus.OK);
    }
}
