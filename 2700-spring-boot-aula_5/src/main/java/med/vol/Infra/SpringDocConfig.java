package med.vol.Infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	            .components(new Components()
	                    .addSecuritySchemes("bearer-key",
	                            new SecurityScheme()
	                                    .type(SecurityScheme.Type.HTTP)
	                                    .scheme("bearer")
	                                    .bearerFormat("JWT")))
	                    .info(new Info()
	                            .title("Api para testes")
	                            .description("API com crud de paciente e medico e agendamento e cancelamento de consultas, com regras de negocios simples")
	                            .contact(new Contact()
	                                    .name("Backend")
	                                    .email("luispro.90@gmail.com"))
	                    .license(new License()
	                            .name("Apache 2.0")
	                            .url("http://voll.med/api/licenca")));
	}
}
