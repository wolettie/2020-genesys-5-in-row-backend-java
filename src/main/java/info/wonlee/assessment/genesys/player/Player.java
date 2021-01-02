package info.wonlee.assessment.genesys.player;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Data
public class Player {
    private String uuid;

    private String name;

    @EqualsAndHashCode.Exclude
    private LocalDateTime lastCheckedIn = LocalDateTime.now();
}
