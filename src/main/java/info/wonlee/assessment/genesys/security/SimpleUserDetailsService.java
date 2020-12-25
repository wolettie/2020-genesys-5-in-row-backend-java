package info.wonlee.assessment.genesys.security;

import info.wonlee.assessment.genesys.player.Player;
import info.wonlee.assessment.genesys.player.PlayerQueue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Service
public class SimpleUserDetailsService implements UserDetailsService {
    private final PlayerQueue playerQueue;

    public SimpleUserDetailsService(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        final Player player = playerQueue.findByUuid(uuid);
        if (player != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority("PLAYER");
            List<GrantedAuthority> authorityList = Arrays.asList(new GrantedAuthority[]{authority});
            return new SimplePlayerDetails(player, randomString(), authorityList);
        } else {
            return null;
        }
    }

    private static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 16;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
