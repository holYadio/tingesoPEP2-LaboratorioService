package tingeso.laboratorioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.laboratorioservice.entity.Laboratorio;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {

}
