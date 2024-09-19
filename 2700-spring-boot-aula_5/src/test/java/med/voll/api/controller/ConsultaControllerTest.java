package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.Consulta.AgendaDeConsultas;
import med.voll.Consulta.DadosDetalhamentoConsulta;
import med.voll.api.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser //pra falar pro security que vc esta logado pra n fazer a verificacao
class ConsultaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JacksonTester< DadosDetalhamentoConsulta> jacksontester;
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> DadosDetalhamentoJson;
	
	@MockBean
	private AgendaDeConsultas agendaDeConsultas;
	
	
	@Test
	@DisplayName("testa se esta disparando erro 400")
	 void agendarCenario1() throws Exception {
	var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
	
	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	
	@Test
	@DisplayName("testa se esta disparando 200 quando esta ok")
	
	 void agendarCenario2() throws Exception {
		
		 var data = LocalDateTime.now().plusHours(1);
	        var especialidade = Especialidade.CARDIOLOGIA;

	        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
	        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);
	        
		var response = mockMvc
                .perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
                .content(jacksontester.write(new DadosDetalhamentoConsulta(2l, 5l, 2l,data)).getJson()))
                .andReturn().getResponse();
	
		  var jsonEsperado = DadosDetalhamentoJson.write(
	                dadosDetalhamento
	        ).getJson();

	assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}
	

}
