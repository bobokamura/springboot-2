package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long>, MedicoRepositoryTest {

    @Query(value = "SELECT NEW med.voll.api.domain.medico.DadosListaMedicos(" +
            "m.id, " +
            "m.nome, " +
            "m.email, " +
            "m.especialidade, " +
            "m.crm) " +
            "FROM Medico m " +
            "WHERE m.ativo = true")
    Page<DadosListaMedicos> findAllMedicosPage(Pageable pageable);

    Optional<Medico> findByIdAndAtivoTrue(Long id);
}
