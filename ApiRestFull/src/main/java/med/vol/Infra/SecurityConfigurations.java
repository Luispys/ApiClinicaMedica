package med.vol.Infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	@Autowired
	private SecurityFilter securityFilter;
 //nas versoes mais antigas do spring se faz assim
//	@Bean //pra devolver a classe pro spring assim ele a carrega e faz a injeçao das dependecias da mesma
//	public SecurityFilterChain securityfilter(HttpSecurity http) throws Exception { //pra dizer pro spring que a autenticao e stateless pq e rest
//		return http.csrf().disable()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and().build();
//	}
	//a partir da versao 3.1 do spring o metodo pra transformar a autenticao em stateless começou a ser feito assim
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf(csrf -> csrf.disable()).
	    		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and().authorizeHttpRequests()
	            .requestMatchers(HttpMethod.POST, "/login").permitAll()
	            .requestMatchers( "/v3/api-docs/**\", \"/swagger-ui.html\", \"/swagger-ui/**").permitAll()
	            .anyRequest().authenticated()
//	            .requestMatchers(HttpMethod.POST, "/login").permitAll()  pra por exemplo um adm ter acesso a algumas coisas exclusivas
//	            .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
//	            .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
	            .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)//linha mt importa pra definir ordem dos
	            //filtros que o spring vai realizar, entao primeiro vem o que foi criado pra ver se o usuario esta autenticado e depois
	            //vem o do sring
	            .build();
	    //a partir da versao 3.2 do spring o codigo fica assim cheio de lambda
//	    // http.csrf(csrf -> csrf.disable())
//        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .authorizeHttpRequests(req -> {
//            req.requestMatchers("/login").permitAll();
//            req.anyRequest().authenticated();
//        })
//    .build();
	}
	@Bean//aqui e pra ensinar pro spring a injetar a classe authenticationManager pro controller usar ela
	public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean //fala pro spring qual hashing de senha ele vai utilizar
	public PasswordEncoder passwaordencoder() {
		return new BCryptPasswordEncoder();
	}
}
