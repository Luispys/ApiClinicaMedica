package ValidacoesConsultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.Consulta.DadosAgendamentoConsulta;
import med.voll.Consulta.ValidacaoException;
import med.voll.api.medico.MedicoRepository;

@Component

public class ValidaMedicoAtivo implements ValidadorAgendamentoConsulta {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public void validar (DadosAgendamentoConsulta dados) {
		if(dados.idMedico()==null) {
			return;
		}
		var medicoEstaAtivo = medicoRepository.findAtivoByID(dados.idMedico());
		if(!medicoEstaAtivo) {
			throw new ValidacaoException("medico nao esta ativo");
		}
	}

}

