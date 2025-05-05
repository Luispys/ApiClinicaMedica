package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.Consulta.AgendaDeConsultas;
import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.DadosCancelamentoConsulta;
import med.voll.Consulta.DadosDetalhamentoConsulta;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired
	private AgendaDeConsultas consultas;

	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		var dadosconsulta = consultas.agendar(dados);
		return ResponseEntity.ok(dadosconsulta);
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
		consultas.cancelar(dados);
		return ResponseEntity.noContent().build();
	}
}
