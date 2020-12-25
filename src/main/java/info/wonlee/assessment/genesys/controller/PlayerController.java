package info.wonlee.assessment.genesys.controller;

import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import info.wonlee.assessment.genesys.security.JwtValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@RestController
@RequestMapping(path = "/api/v1/player")
public class PlayerController {
    private final PlayerQueue playerQueue;

    public PlayerController(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        final Player registered = playerQueue.register(player);
        if (registered != null) {
            return ResponseEntity.ok(JwtValidator.generatePlayerJwt(registered));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
