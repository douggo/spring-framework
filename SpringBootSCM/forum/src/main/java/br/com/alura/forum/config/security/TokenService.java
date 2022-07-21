package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${forum.jwt.expirationDate}")
    private String expirationDate;

    @Value("${forum.jwt.secret}")
    private String secret;

    public TokenService usuarioService;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date date = new Date();
        Date dataExpiration = new Date(date.getTime() + Long.parseLong(expirationDate));
        return Jwts.builder()
                   .setIssuer("API DO FÓRUM DO ALURA")
                   .setSubject(usuario.getId().toString())
                   .setIssuedAt(dataExpiration)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

}
