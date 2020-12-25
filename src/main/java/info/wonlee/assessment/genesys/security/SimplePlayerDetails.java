package info.wonlee.assessment.genesys.security;

import info.wonlee.assessment.genesys.player.Player;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class SimplePlayerDetails extends User {
    @Getter
    private final Player player;

    public SimplePlayerDetails(Player player, String password, Collection<? extends GrantedAuthority> authorities) {
        super(player.getName(), password, authorities);
        this.player = player;
    }
}
