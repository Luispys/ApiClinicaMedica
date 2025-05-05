package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import med.voll.api.paciente.DetalhesDaAtualizacaoPaciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("medicos") //lembrar sempre que o requestmapping se trata da url 
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional 
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) { //requestbody para o spring pegar os dados do corpo da requisicao feita
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
    //@Secured("ROLE_ADMIN") pra deixar so pra quer tiver a role admin usar o metodo
    @GetMapping ("/{id}")
    public ResponseEntity<?> Detalhar(@PathVariable Long id) {
	 var medico = repository.getReferenceById(id);
	 return ResponseEntity.ok(new DetalhesDaAtualizacaoMedico(medico));
}



}
