package med.vol.Infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import Usuario.Usuario;

@Service

public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	//vamos la voce começa guardando numa variavel um algoritmo com a assinatura que no caso aqui e o hma mas pode ser outros, e no parametro
	//tem uma especie de senha secreta pra se usar a assinatura na api
	//apos isso no return se usa a classe jwt pra criar o token, apos isso chamamos o metodo issuer pra identificar a api dona do metodo
	//de criacao do token, com isso chamamos o metodo withsubject pra indicar qual o usuario que o token "pertence"
	//ai com expires pode determinar uma data de duraçao do token, por fim fazemos a assinatura do metodo
	//alem disso tem um metodo chamado .withclaim vc pode guardar mais informaçoes do usuario no token, como por exemplo
	//withClaim("id", usuario.getid()). algo assim

	public String geraToken(Usuario usuario) { //lembre de colocar no parametro a classe que represente o user pra poder vincular ele com o token
	        try {
	            var algoritmo = Algorithm.HMAC256(secret);
	            return JWT.create()
	                .withIssuer("api")
	                .withSubject(usuario.getLogin())
	                .withExpiresAt(dataExpiracao())
	                .sign(algoritmo);
	        } catch (JWTCreationException exception){
	            throw new RuntimeException("erro ao gerrar token jwt", exception);
	        }		
	    }

	private String getSubject (String tokenJwt) {
		 try {
		        var algoritmo = Algorithm.HMAC256(secret);
		        return JWT.require(algoritmo)
		                        .withIssuer("api")
		                        .build()
		                        .verify(tokenJwt)
		                        .getSubject();
		    } catch (JWTVerificationException exception) {
		        throw new RuntimeException("Token JWT inválido ou expirado!");
		    }
	}
	    private Instant dataExpiracao() {//pra determinar a duraçao do token
	        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	    
	}

		public Object getSubject(Object tokenJWT) {
			// TODO Auto-generated method stub
			return null;
		}
}

