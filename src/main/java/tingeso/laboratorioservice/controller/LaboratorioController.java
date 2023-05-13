package tingeso.laboratorioservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.laboratorioservice.entity.Laboratorio;
import tingeso.laboratorioservice.service.LaboratorioService;

import java.util.List;

@RestController
@RequestMapping("/laboratorio")
public class LaboratorioController {
    @Autowired
    LaboratorioService laboratorioService;

    @GetMapping
    public ResponseEntity<List<Laboratorio>> getAllLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        if (laboratorios.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> getLaboratorioById(@PathVariable(value = "id") int id) {
        Laboratorio laboratorio = laboratorioService.getLaboratorioById(id);
        if (laboratorio == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(laboratorio);
    }

    @PostMapping()
    public ResponseEntity<Laboratorio> createLaboratorio(@RequestBody Laboratorio laboratorio) {
        Laboratorio newLaboratorio = laboratorioService.createLaboratorio(laboratorio);
        if (newLaboratorio == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(newLaboratorio);
    }
}
