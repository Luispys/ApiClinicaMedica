package ValidacoesConsultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;
import med.voll.api.medico.MedicoRepository;

@Component
public class ValidarMedicoComConsultaMarcada implements ValidadorAgendamentoConsulta {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		var medicoPossuiConsultaNoMesmoHorario = medicoRepository.existsByMedicoIDAndData(dados.idMedico());
		if((boolean) (medicoPossuiConsultaNoMesmoHorario = true)) {
			throw new ValidacaoException("medico indisponivel nesta data");
		}
	}

}
