package tingeso.laboratorioservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tingeso.laboratorioservice.entity.Laboratorio;
import tingeso.laboratorioservice.repository.LaboratorioRepository;

import java.util.List;

@Service
public class LaboratorioService {
    @Autowired
    LaboratorioRepository laboratorioRepository;

    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Laboratorio getLaboratorioById(int id) {
        return laboratorioRepository.findById(id).orElse(null);
    }

    public Laboratorio createLaboratorio(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }
}
