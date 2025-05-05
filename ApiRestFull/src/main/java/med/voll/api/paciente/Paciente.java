package med.voll.api.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

	@Table (name="medico")
	@Entity(name="Medico")
	@Getter  //anotacao do lombok pra gerar os getters
	@NoArgsConstructor  //pra gerar construtor sem argumento
	@AllArgsConstructor  //construtor com todos os argumentos
	@EqualsAndHashCode(of ="id")   //hashcode com id como parametro
	public class Paciente {
		@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String nome;
		

		private String cpf;
		private String email;
	    private String telefone;
		@Embedded
		private Endereco endereco;
		private boolean ativopaciente;
		
		public Paciente (DadosCadastroPaciente dados) {
			this.ativopaciente = true;
			this.email=dados.email();
			this.cpf=dados.cpf();
			this.telefone=dados.telefone();
			this.endereco = new Endereco(dados.endereco());
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}
		public  Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelefone() {
			return telefone;
		}

		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
		}

		public void atualizar(@Valid DadosAtualizacaoPaciente dados) {
			  if (dados.nome() != null) {
		            this.nome = dados.nome();
		        }
		        if (dados.telefone() != null) {
		            this.telefone = dados.telefone();
		        }
		        if (dados.endereco() != null) {
		            this.endereco.atualizarInformacoes(dados.endereco());
		        }
			
		}

		public void excluir() {
			this.ativopaciente = false;
			
		}
		
}
