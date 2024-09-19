package ValidacoesConsultas;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import med.voll.Consulta.ConsultaRepository;
import med.voll.Consulta.DadosCancelamentoConsulta;
import med.voll.Consulta.ValidacaoException;

public class ValidarCancelamente implements ValidadorCancelamentoDeConsulta {

	@Autowired
	private ConsultaRepository repository;

	@Override
	public void validar(DadosCancelamentoConsulta dados) {
		var consulta = repository.getReferenceById(dados.idConsulta());
		var agora = LocalDateTime.now();
		var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

		if (diferencaEmHoras < 24) {
			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
		}

	}
}
