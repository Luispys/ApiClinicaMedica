package ValidacoesConsultas;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;

@Component
public class ValidaHorarioFuncionamento implements ValidadorAgendamentoConsulta {

	public void validar (DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var doming = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesdaabertura = dataConsulta.getHour()<7;
		var depoisquefecha = dataConsulta.getHour()>18;
		if(doming || antesdaabertura || depoisquefecha) {
			throw new ValidacaoException("fora do horario de atendimento");
		}
	}
}
