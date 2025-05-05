package med.voll.api.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	Page<Paciente> findAllByAtivoPacienteTrue(Pageable paginacao);
	
	 
    @Query("""
    		
    		select p.ativo
    		from Paciente p
    		where p.id = :id
    		""")

	Boolean findAtivoByID(Long id);
}


