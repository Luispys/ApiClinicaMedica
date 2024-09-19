package med.voll.api.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.Consulta.Consulta;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.Paciente;

@DataJpaTest // pra testar interface
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	@DisplayName("Deve devolver null quando medico cadastrado nao esta disponivel na data")//pra poder fazer uma descricao do teste
	void escolherMedicoAleatorioLivreCenario1() {
		//given
		var proximoDia= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		
		var medico = cadastrarMedico("medico", "medico@12", "143344", Especialidade.CARDIOLOGIA);
				var paciente = cadastrarPaciente("paciente", "paciente@11", "00000000000");
		 cadastrarConsulta(medico, paciente, proximoDia);
		 //when
	var medicolivre = medicoRepository.escolherMedicoAleatorioLivre(Especialidade.CARDIOLOGIA,proximoDia );
	//then
	assertThat(medicolivre).isNull(); //pra fazer o teste em si
	}

	@Test
	@DisplayName("Deve devolver medico quando ele estiver disponivel na data")//pra poder fazer uma descricao do teste
	void escolherMedicoAleatorioLivreCenario2() {
		var proximoDia= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
		
		var medico = cadastrarMedico("medico", "medico@12", "143344", Especialidade.CARDIOLOGIA);
				var paciente = cadastrarPaciente("paciente", "paciente@11", "00000000000");
		 cadastrarConsulta(medico, paciente, proximoDia);
	var medicolivre = medicoRepository.escolherMedicoAleatorioLivre(Especialidade.CARDIOLOGIA,proximoDia );
	assertThat(medicolivre).isEqualTo(medico); //pra fazer o teste em si
	}
	
	

	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
		entityManager.persist (new Consulta(null, medico, paciente, data, null));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
		var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
		entityManager.persist(medico);
		return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
		var paciente = new Paciente(dadosPaciente(nome, email, cpf));
		entityManager.persist(paciente);
		return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
		return new DadosCadastroMedico(nome, email, "61999999999", crm, especialidade, dadosEndereco());
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
		return new DadosCadastroPaciente(nome, email, "61999999999", cpf, dadosEndereco());
	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", null, null);

	}
}
