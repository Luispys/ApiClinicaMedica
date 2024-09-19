package ValidacoesConsultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.Consulta.ConsultaRepository;
import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;

@Component
public class ValidaPacienteConsultaMesmaData implements ValidadorAgendamentoConsulta{
	
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		
		var primeiroHorario = dados.data().withHour(7);
		var ultimoHorario = dados.data().withHour(18);
		var pacientePossuiConsultaNoDia = consultaRepository.existsByPacienteAndDataBetween(dados.idPaciente(),primeiroHorario,ultimoHorario,dados.data());
				
				if((boolean) pacientePossuiConsultaNoDia) {
					throw new ValidacaoException("ja possui uma consulta agendada neste dia");
				}
		}

}
