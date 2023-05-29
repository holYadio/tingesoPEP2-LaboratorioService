package tingeso.laboratorioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.laboratorioservice.entity.Laboratorio;

import java.util.List;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {

    List<Laboratorio> findByProveedor(String proveedor);

    Laboratorio findByProveedorAndQuincena(String proveedor,
                                                  String quincena);
}
