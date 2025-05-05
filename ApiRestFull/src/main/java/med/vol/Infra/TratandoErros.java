package med.vol.Infra;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.Consulta.ValidacaoException;

@RestControllerAdvice
public class TratandoErros {
	@ExceptionHandler(EntityNotFoundException.class)//aqui e pra indicar que vai lidar com excessoes e como parametro ele recebe o tipo de excessao
	
	public ResponseEntity Erro404() {
		return ResponseEntity.notFound().build();
		
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity Erro400(MethodArgumentNotValidException ex) { //no parametro do metodo vc joga a excessao assim da pra receber ela
		//e com isso saber aonde deu erro pra poder devolver na mensagem de erro
		var erros = ex.getFieldErrors(); //ai como a funcao ja diz ela pega os campos que deram algum erro
		return ResponseEntity.badRequest().body(erros.stream().map(DadosDosErros::new).toList()); //por fim vc joga ela no corpo da requisicao e constroi um corpo pra mnensagem	
		//depois que vc constroi esse corpo voce converte ele e mapeia, ai no mapeamento vc passa os dados do record que ira devolver a mensagem
		//por fim converte pra uma lista ser devolvida
	}
	
	 @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity tratarErroBadCredentials() {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	    }

	    @ExceptionHandler(AuthenticationException.class)
	    public ResponseEntity tratarErroAuthentication() {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
	    }

	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity tratarErroAcessoNegado() {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity tratarErro500(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
	    }
	    @ExceptionHandler(ValidacaoException.class)
	    public ResponseEntity TratandoErroRegradeNegoci(ValidacaoException ex) {
	    	
	    	return ResponseEntity.badRequest().body(ex.getMessage());
	    }	
	public record DadosDosErros(String campo, String mensagem) {
		//um record que como so vai ser usado aqui da pra vir junto na classe
		//ele recebe como parametro o campo que deu erro e a mensagem de erro
		//ai vc cria um construtor com a classe fielderror pra localizar o campo com erro
		
		public DadosDosErros(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
		
	}
}
