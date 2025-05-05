package med.vol.Infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import Usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenservice;
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = recuperarToken(request);
		//se o token e diferente de nulo quer dizer que o usuario esta logado por assim dizer entao precisa-se dizer pro spring isto
		//por ser stateless ele n guarda essa informaçao entao precisao forçar uma autenticacao
		//entao se ele veio com o token vc pega o usuario atraves do getsubject e atraves dele agente faz um consulta no banco pelo usuario
		if(tokenJWT != null){ 
			 var subject = tokenservice.getSubject(tokenJWT);
			 var usuario = repository.findByLogin(subject); 
			 // essa linha de baixo e pra fazer a autenticacao vc estancia o dto do spring e passa os parametros pra ele
			 //o usuario nulo(ainda nao sei a que se refere) e por fim as autoridades que ele vai ter que taa definido na classe dele
			 //por fim vc usa a classe do spring pra forçar a autenticacao
			 var authentication = new UsernamePasswordAuthenticationToken(usuario, null,usuario.getAuthorities());
			 SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		

	   
		filterChain.doFilter(request, response); //pra dizer pro spring continuar pro proximo filtro ou açao
		
	}

	private Object recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization"); //pra recuperar o token pelo cabeçalho da requisicao
		if(authorizationHeader != null) { //se o cabeçaco esta diferente de nulo ele devolve senao volta um nulo ai o security faz o resto	
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}
	

}
