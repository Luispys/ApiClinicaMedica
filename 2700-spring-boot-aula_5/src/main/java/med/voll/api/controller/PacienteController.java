package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.paciente.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.DadosListagemPaciente;
import med.voll.api.paciente.DetalhesDaAtualizacaoPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
	

	@Autowired
	private PacienteRepository pacienteRepository;
	
	
	@PostMapping
	@Transactional //para dizer que como e um metodo de escrita ele vai ter uma transacao direta com o banco
	
	//vamos por partes no parametro do metodo iremos passar o record com os dados e a classe do spring pra criar uma uri
	//vamos estanciar o paciente e depois usando o repository iremos salvar esse paciente
	//apos isso iremos criar uma uri passando o caminho com .path que no caso e a url que ela vai se dirigir e usando o buildand
	//expend pra pegar o id do paciente criado e usando o touri iremos passar esse id no lugar do id variavel do path
	//com o created iremos finalmente criar a uri em si e depois passaremos o corpo da resposta instanciando o construtor e passando o paciente
	//a uri e basicamente o endereço aonde esse registro esta sendo criado e atraves dela iremos devolver um codigo 201 por assim dizer
	// ai ele ira devolver os dados que foram criado e um cabeçalho com algumas informaçoes
	
	private ResponseEntity<DetalhesDaAtualizacaoPaciente> cadastrar (@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(dados);
		pacienteRepository.save(paciente);
//  
		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DetalhesDaAtualizacaoPaciente(paciente));
	}
	
	//bem vamos por parte vc poderia apenas fazer um findallbyid ou algo assim dos pacientes porem vc n teria controle do que volta
	//entao seria melhor fazer um record com os dados especificos, mas ai chegamos no problema de voce ter que fazer a conversao
	// de um record pra uma lista pra ele devolver a lista no fim
	//entao usa o metodo find que passa como parametro a paginacao nesse caso pra poder paginar o resultado 
	//depois agente mapeia com o .map e passa o construtor do record com lambd e isso tudo retorna uma variavel do page e depois
	// vc so retorna ela atraves do responseentity e com o .ok pra demonstrar que esta ok a transacao
	//bom lembrar que se n fosse paginar bastaria usar um .tostream.map e depois o construtor o .tolist pra converter pra uma lista 
	//isso tudo ja no retorno do metodo ate porque seria mais "simples"
	//o objeto pageable e pra usaar paginacao do spring  e se no parametro do metodo find vc usar ele o spring faz a operacao no metodo de paginacao ja
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault (size = 10, page = 1, sort = {"nome"}, direction = Direction.ASC) Pageable paginacao){
		 var page = pacienteRepository.findAllByAtivoPacienteTrue(paginacao).map( DadosListagemPaciente :: new);
		 return ResponseEntity.ok(page); 
		 }
	
	 @PutMapping
	    @Transactional
	    //pra começar o repository vai pegar os dados pelo id, e vai devolver uma variavel do paciente
	    //depois ele vai usar o metodo que esta na classe paciente atualizar passando os dados que recuperou como parametro
	    //por fim "criar" um novo paciente com os dados atraves do resposientity
	    public ResponseEntity<DetalhesDaAtualizacaoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
		 var paciente = pacienteRepository.getReferenceById(dados.id());
		 paciente.atualizar(dados);
		 return ResponseEntity.ok(new DetalhesDaAtualizacaoPaciente(paciente));
		 

	 }
	 @DeleteMapping ("/{id}")
	    @Transactional
	    public ResponseEntity<?> excluir(@PathVariable Long id) {
		 var paciente = pacienteRepository.getReferenceById(id);
		 paciente.excluir();
		 return ResponseEntity.noContent().build();//nocontent cria um objeto e o build chama ele pra resposta
 }
    
	 @GetMapping ("/{id}")
	    public ResponseEntity<?> Detalhar(@PathVariable Long id) {
		 var paciente = pacienteRepository.getReferenceById(id);
		 return ResponseEntity.ok(new DetalhesDaAtualizacaoPaciente(paciente));
}
 


}
