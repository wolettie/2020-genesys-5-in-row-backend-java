package info.wonlee.assessment.genesys.player;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public interface PlayerQueue {
    Player register(Player player);
    Player findByUuid(String uuid);
    void playerDisconnected(Player player);
}
