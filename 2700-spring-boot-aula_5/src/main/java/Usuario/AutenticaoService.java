package Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //pra dizer que a classe executa um serviço quando o spring inicializar o processo
 //pra diser pro spring que ela e uma classe de autenticacao pro spring vc implementa a classe userdetalis
public class AutenticaoService implements UserDetailsService  {
	@Autowired
	private UsuarioRepository usuarioRepository;
	//quando agente tentar fazer autenticacao o spring chama essa classe e ela carrega o usuario por um dado/
	//com o repository agente carrega o dado do usario pra fazer essa autenticacao
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByLogin(username);
	}

}
