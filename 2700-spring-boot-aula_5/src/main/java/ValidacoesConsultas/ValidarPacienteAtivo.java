package ValidacoesConsultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;

@Component

public class ValidarPacienteAtivo implements ValidadorAgendamentoConsulta {
	@Autowired
private PacienteRepository pacienteRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		if(dados.idPaciente()==null) {
			return;
		}
		var pacienteEstaAtivo = pacienteRepository.findAtivoByID(dados.idMedico());
		if(!pacienteEstaAtivo) {
			throw new ValidacaoException("paciente nao esta ativo");
		}
	}

}
