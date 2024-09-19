package med.voll.Consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta( 
		@NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {

}
