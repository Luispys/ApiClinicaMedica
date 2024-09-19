package med.voll.Consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ValidacoesConsultas.ValidadorAgendamentoConsulta;
import ValidacoesConsultas.ValidadorCancelamentoDeConsulta;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
	@Autowired
	private ConsultaRepository consultaRepository;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<ValidadorAgendamentoConsulta> validadorAgendamentoConsultas;
	
	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
	
		public DadosDetalhamentoConsulta agendar (DadosAgendamentoConsulta dados) {
			if(!pacienteRepository.existsById(dados.idPaciente())) {
				throw new ValidacaoException ("ID nao existe por favor verifique e tente novamente");
			}
			
			if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
				throw new ValidacaoException ("ID nao existe por favor verifique e tente novamente");
			}
			validadorAgendamentoConsultas.forEach(v-> v.validar(dados));
			
			var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
			var medico = escolherMedico(dados);
			if(medico ==null) {
				throw new ValidacaoException ("Nenhum medico disponivel nesta data");
			}
			var consulta = new Consulta(null,medico,paciente,dados.data(),null);
			consultaRepository.save(consulta);
			return new DadosDetalhamentoConsulta(consulta);
			
		}
		private Medico escolherMedico(DadosAgendamentoConsulta dados) {
			
			if(dados.idMedico()!= null) {
				return medicoRepository.getReferenceById(dados.idMedico());
			}
			
			if(dados.especialidade() == null) {
				throw new ValidacaoException("a especialidade e obrigatioria");
				
			}
			return medicoRepository.escolherMedicoAleatorioLivre(dados.especialidade(),dados.data());
					
		}
		public void cancelar(DadosCancelamentoConsulta dados) {
		    if (!consultaRepository.existsById(dados.idConsulta())) {
		        throw new ValidacaoException("Id da consulta informado não existe!");
		    }
		    validadoresCancelamento.forEach(v -> v.validar(dados));

		    var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		    consulta.cancelar(dados.motivo());
			System.out.println("nao sei amigo");
		}

}
