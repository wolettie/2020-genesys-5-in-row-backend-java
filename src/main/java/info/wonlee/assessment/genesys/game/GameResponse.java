package info.wonlee.assessment.genesys.game;

import lombok.Builder;
import lombok.Data;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Data
@Builder
public class GameResponse {
    private Game game;
    private String errorMessage;
}
