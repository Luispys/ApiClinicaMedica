package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DetalhesDaAtualizacaoMedico(Long id, String nome, String email, String telefone, Endereco endereco,
			String crm, Especialidade especialidade) {
		
		public DetalhesDaAtualizacaoMedico(Medico medico) {
			this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getEndereco(),
					medico.getCrm(), medico.getEspecialidade());

		}
	
}
