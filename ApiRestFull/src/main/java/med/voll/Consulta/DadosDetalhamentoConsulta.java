package med.voll.Consulta;

import java.time.LocalDateTime;

import med.voll.api.medico.Especialidade;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data ) {

	public DadosDetalhamentoConsulta(Consulta consulta) {
	
	this(consulta.getId(),consulta.getMedico().getId(),consulta.getPaciente().getId(),consulta.getData());
	}


		
	//usando a anotaccao jsonalias da pra dar apelidos pros campos da requisicao entao quando vier do cliente o corpo nao precisa ser
	//o mesmo nome que esta no record
//	public record DadosCompra(
//		    @JsonAlias({“produto_id”, “id_produto”}) Long idProduto,
//		    @JsonAlias({“data_da_compra”, “data_compra”}) LocalDate dataCompra
		
}
