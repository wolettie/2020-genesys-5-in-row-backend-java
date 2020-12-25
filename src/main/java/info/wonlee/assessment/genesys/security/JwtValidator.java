package info.wonlee.assessment.genesys.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import info.wonlee.assessment.genesys.player.Player;
import lombok.extern.slf4j.Slf4j;

/**
 * User: wonlee
 * Date: 23/12/2020
 */

@Slf4j
public class JwtValidator {
    private final static String secret = "Super Secure Random String";
    private final static Algorithm jwtAlgorithm = Algorithm.HMAC256(secret);
    private final static JWTVerifier jwtVerifier = JWT.require(jwtAlgorithm).build();
    public final static String USER_CLAIM = "user_uuid";

    public static String generatePlayerJwt(Player player) {
        final JWTCreator.Builder builder = JWT.create()
                .withClaim(USER_CLAIM, player.getUuid());
        return builder.sign(jwtAlgorithm);
    }

    public static DecodedJWT verifyJwt(String token) {
        try {
            return jwtVerifier.verify(token);
        } catch (JWTVerificationException exception) {
            final DecodedJWT invalidJWT = JWT.decode(token);
            log.warn("player uuid {} attempted to refresh token with invalid token, {}",
                    invalidJWT.getClaim(USER_CLAIM),
                    exception.getMessage());
            return null;
        }
    }}
