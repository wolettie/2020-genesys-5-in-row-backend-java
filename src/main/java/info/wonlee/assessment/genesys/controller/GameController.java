package info.wonlee.assessment.genesys.controller;

import info.wonlee.assessment.genesys.game.Game;
import info.wonlee.assessment.genesys.game.GameResponse;
import info.wonlee.assessment.genesys.game.evaluator.IllegalMoveException;
import info.wonlee.assessment.genesys.game.manager.GameManager;
import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.security.SimplePlayerDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@RestController
@RequestMapping(path = "/api/v1/game")
public class GameController {
    private final GameManager gameManager;

    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private Player fromPrincipal(Principal principal) {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        final SimplePlayerDetails details = (SimplePlayerDetails) token.getPrincipal();
        return details.getPlayer();
    }

    @GetMapping(path = "/status")
    public ResponseEntity<Game> gameStatus(Authentication principal) {
        final Player player = fromPrincipal(principal);
        final Game game = gameManager.gameStatus(player);
        return ResponseEntity.ok(game);
    }

    @PostMapping(path = "/dropDisc")
    public ResponseEntity<GameResponse> dropDisc(@RequestBody int column, Authentication authentication) {
        try {
            final Game game = gameManager.dropDisc(null, column);
            return ResponseEntity.ok(GameResponse.builder().game(game).build());
        } catch (IllegalMoveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GameResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    @PostMapping(path = "/quit")
    public ResponseEntity<Game> quit(Principal principal) {
        final Player player = fromPrincipal(principal);
        return ResponseEntity.ok(gameManager.playerDisconnected(player));
    }

}
