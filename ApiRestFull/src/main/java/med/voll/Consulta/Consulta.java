package med.voll.Consulta;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.medico.Medico;
import med.voll.api.paciente.Paciente;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Consulta {

	  

	 
    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;
    
		public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime data, MotivoCancelamento motivoCancelamento) {
		super();
		this.id = id;
		this.medico = medico;
		this.paciente = paciente;
		this.data = data;
		this.motivoCancelamento = motivoCancelamento;
	}

		
	   

	    public void cancelar(MotivoCancelamento motivo) {
	        this.motivoCancelamento = motivo;
	    }




		public MotivoCancelamento getMotivoCancelamento() {
			return motivoCancelamento;
		}




		public Long getId() {
			return id;
		}




		public Medico getMedico() {
			return medico;
		}




		public Paciente getPaciente() {
			return paciente;
		}




		public LocalDateTime getData() {
			return data;
		}


}
