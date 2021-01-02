package info.wonlee.assessment.genesys.controller;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import info.wonlee.assessment.genesys.security.JwtValidator;
import info.wonlee.assessment.genesys.security.SimplePlayerDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/player")
public class PlayerController {
    private final PlayerQueue playerQueue;

    public PlayerController(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    private Player fromPrincipal(Principal principal) {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        final SimplePlayerDetails details = (SimplePlayerDetails) token.getPrincipal();
        return details.getPlayer();
    }

    /**
     *
     * @param player player object with only name filled in, other field must not be exist
     * @return JWT string
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        if (player.getName() == null || player.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("name is empty");
        }
        final Player registered = playerQueue.register(player);
        if (registered != null) {
            return ResponseEntity.ok(JwtValidator.generatePlayerJwt(registered));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/quit")
    public ResponseEntity<Game> quit(Principal principal) {
        final Player player = fromPrincipal(principal);
        log.info("player {} is disconnected", player.getName());
        return ResponseEntity.ok(playerQueue.playerDisconnected(player));
    }
}
