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

    @GetMapping("/lastquincena/{quincena}")
    public String getLastQuincena(@PathVariable(value = "quincena") String quincena) {
        return laboratorioService.getLastQuincena(quincena);
    }

    @GetMapping("/getVariacionGrasa/{quincena}/{codigoProveedor}/{porcentajeGrasa}")
    public double getVariacionGrasa(@PathVariable(value = "quincena") String quincena, @PathVariable(value = "codigoProveedor") String codigoProveedor, @PathVariable(value = "porcentajeGrasa") String porcentajeGrasa) {
        return laboratorioService.getVariacionGrasa(quincena, codigoProveedor, porcentajeGrasa);
    }

    @GetMapping("/getVariacionSolidosTotales/{quincena}/{codigoProveedor}/{porcentajeSolidoTOtal}")
    public double getVariacionSolidoTotal(@PathVariable(value = "quincena") String quincena, @PathVariable(value = "codigoProveedor") String codigoProveedor, @PathVariable(value = "porcentajeSolidoTOtal") String porcentajeSolidoTOtal) {
        return laboratorioService.getVariacionSolidoTotal(quincena, codigoProveedor, porcentajeSolidoTOtal);
    }

    @PostMapping()
    public ResponseEntity<Laboratorio> createLaboratorio(@RequestBody Laboratorio laboratorio) {
        Laboratorio newLaboratorio = laboratorioService.createLaboratorio(laboratorio);
        if (newLaboratorio == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(newLaboratorio);
    }
}
