package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class HS256TokenManager {

	private final static String secretKey = "wMM0PrXgslcREZFTjnbQGIhyIKTQN4I0VF8me4VIRBzsm1DsjUNlE0tw9PPE";
	
    public static SecretKey convertSecretKey(String secretKey) {
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(secretKey);

        return new SecretKeySpec(apiKeySecretBytes, "HMACSHA256");
    }

    public static String convertStringSecretKey(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String createAccessToken(String subject, int tokenExpirationMsec){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, tokenExpirationMsec);
        Date expiryDate =  calendar.getTime();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(convertSecretKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean verifyAccessToken(String jwtToken) throws Exception{

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(convertSecretKey(secretKey))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return true;
    }
}
