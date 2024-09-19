package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Usuario.DadosLogin;
import Usuario.Usuario;
import jakarta.validation.Valid;
import med.vol.Infra.DadosTokenJwt;
import med.vol.Infra.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenservice;
	
	@PostMapping  //vamos la primeiro geramos o token que nada mais e que os dados(login e senha no caso)
	//pra isso se instancia uma classe do spring e passa esses dados
	//depois atraves de authentication manager agente valida esse token 
	public ResponseEntity<?> Login (@RequestBody @Valid DadosLogin dados) {
		var Authenticatontoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autentica = manager.authenticate(Authenticatontoken);
		var tokenJwt = tokenservice.geraToken((Usuario) autentica.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJwt(tokenJwt)); //no response vc chama o metodo de geratoken e passa como parametro um
		//usuario, nesse exemplo o authenticationmanager guarda um usuario entao da pra passar ele e usa o getprincipal pra pegar o usuario
		//ai depois e so lembrar de fazer um cast pra nao dar erro
		
	}

}
