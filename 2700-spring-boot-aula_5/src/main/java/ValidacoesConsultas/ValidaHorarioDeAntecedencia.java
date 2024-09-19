package ValidacoesConsultas;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;

@Component
public class ValidaHorarioDeAntecedencia implements ValidadorAgendamentoConsulta{

	public void validar (DadosAgendamentoConsulta dados) {
		var dataconsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferença = Duration.between(agora, dataconsulta).toMinutes();
		if(diferença <30) {
			throw new ValidacaoException("deve ser agendado com 30 minutos de antecedencia ");
		}
	}
	
	
	}

