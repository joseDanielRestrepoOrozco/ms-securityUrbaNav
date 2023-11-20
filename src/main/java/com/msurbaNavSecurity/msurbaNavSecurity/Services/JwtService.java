package com.msurbaNavSecurity.msurbaNavSecurity.Services;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /** Se utiliza para crear y firmar un token JWT (JSON Web Token) basado en la información proporcionada del objeto User. 
     * @param theUser objeto de tipo User
     * @return se genera el token JWT como una cadena de caracteres y se retorna
     */
    public String generateToken(User theUser) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        Map<String, Object> claims = new HashMap<>();
        claims.put("_id", theUser.get_id());
        claims.put("name", theUser.getName());
        claims.put("surname", theUser.getSurname());
        claims.put("phone", theUser.getPhone());
        claims.put("email", theUser.getEmail());
        claims.put("birthdate", (theUser.getBirthdate()).toString());
        claims.put("role", theUser.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(theUser.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    /** Valida un token JWT (JSON Web Token)
     * @param token token JWT como entrada
     * @return Si la fecha de vencimiento del token ha expirado devuelve false,
     * o si la fecha de vencimiento del token es posterior a la fecha actual,
     * se considera el token es válido y se devuelve true.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Date now = new Date();
            if (claimsJws.getBody().getExpiration().before(now)) {
                return false;
            }
            return true;
        } catch (SignatureException ex) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /** Extrae información de un token JWT (JSON Web Token) y devuelve un objeto de tipo User con los datos contenidos en el token
     * @param token cadena de texto token JWT
     * @return un objeto User lleno con los datos del token JWT o si el token no es válido o la firma es incorrecta devuelve null
     */
    public User getUserFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            User user = new User();
            user.set_id((String) claims.get("_id"));
            user.setName((String) claims.get("name"));
            user.setSurname((String) claims.get("surname"));
            user.setPhone((String) claims.get("phone"));
            user.setEmail((String) claims.get("email"));
            System.out.println("Pase por aqui");
            System.out.println(user.getEmail());
           // user.setBirthdate((LocalDate) claims.get("birthdate"));
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
