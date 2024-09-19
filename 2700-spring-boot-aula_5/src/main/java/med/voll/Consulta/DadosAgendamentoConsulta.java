package med.voll.Consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.medico.Especialidade;

public record DadosAgendamentoConsulta(
	Long idMedico,

    @NotNull
    Long idPaciente,

    @NotNull
    @Future  //pra indicar que a data precisar estar no futuro
    @JsonFormat(pattern = "dd/MM/yyyy HH::MM") //pra formatar o formato da data
    LocalDateTime data,

    Especialidade especialidade) {
}
