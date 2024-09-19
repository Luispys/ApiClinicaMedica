package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DetalhesDaAtualizacaoPaciente(Long id, String nome, String email, String telefone, Endereco endereco)  {
	public DetalhesDaAtualizacaoPaciente (Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());

    }
}
