package tingeso.laboratorioservice.service;

import com.ctc.wstx.evt.WstxEventReader;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tingeso.laboratorioservice.entity.Laboratorio;
import tingeso.laboratorioservice.repository.LaboratorioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LaboratorioService {
    @Autowired
    LaboratorioRepository laboratorioRepository;

    private final Logger logg = LoggerFactory.getLogger(LaboratorioService.class);
    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Laboratorio getLaboratorioById(int id) {
        return laboratorioRepository.findById(id).orElse(null);
    }

    public Laboratorio createLaboratorio(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }

    public Laboratorio getLaboratorioByProveedorYQuincena(String proveedor, String quincena) {
        return laboratorioRepository.findByProveedorAndQuincena(proveedor, quincena);
    }

    @Generated
    public String guardarDatosLab(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (fileName != null){
            if (!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de analisis del Laboratiorio guardado");
                }
                catch (IOException e){
                    logg.error("Error", e);
                }
            }
            return "Archivo de analisis del Laboratiorio guardado";
        }
        else {
            return "No se guardo el Archivo de analisis del Laboratiorio";
        }
    }

    @Generated
    public void leerCsv(String direccion, String quincena){
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(direccion));
            StringBuilder temp = new StringBuilder();
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDatoDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2],quincena);
                    temp.append("\n").append(bfRead);
                }
            }
            System.out.println("Archivo de analisis del Laboratiorio leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo de analisis del Laboratiorio");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarDatoDB(String proveedor, String porcentajeGrasa, String porcentajeSolidoTotal, String quincena){
        Laboratorio newData = new Laboratorio();
        newData.setProveedor(proveedor);
        newData.setPorcentajeGrasa(porcentajeGrasa);
        newData.setPorcentajeSolidoTotal(porcentajeSolidoTotal);
        newData.setQuincena(quincena);
        createLaboratorio(newData);
    }


    public double getVariacionSolidoTotal(String quincenaActual,
                                          String proveedor,
                                          String porcentajeSolidos) {
        double solidosAnterior;
        double solidosActual = Integer.parseInt(porcentajeSolidos);
        String quincenaAnterior = getLastQuincena(quincenaActual);
        String porcentajeSolidosAnterior;
        try{
            porcentajeSolidosAnterior = getLaboratorioByProveedorYQuincena(
                    proveedor,
                    quincenaAnterior).getPorcentajeSolidoTotal();

            solidosAnterior = Integer.parseInt(porcentajeSolidosAnterior);
        }catch (Exception e){
            solidosAnterior = solidosActual;
        }
        double variacion = solidosAnterior - solidosActual;
        if (variacion < 0) {
            variacion = 0;
        }
        return variacion;
    }

    public double getVariacionGrasa(String quincenaActual,
                                    String proveedor,
                                    String porcentajeGrasa) {
        double grasaAnterior;
        double grasaActual = Integer.parseInt(porcentajeGrasa);
        String quincenaAnterior = getLastQuincena(quincenaActual);
        String porcentajeGrasaAnterior;
        try{
            porcentajeGrasaAnterior = this.getLaboratorioByProveedorYQuincena(
                    proveedor,
                    quincenaAnterior).getPorcentajeGrasa();
            grasaAnterior = Integer.parseInt(porcentajeGrasaAnterior);
        }catch (Exception e){
            grasaAnterior = grasaActual;
        }
        double variacion = grasaAnterior - grasaActual;
        if (variacion < 0) {
            variacion = 0;
        }
        return variacion;
    }

    public String getLastQuincena(String quincena){
        String quincenaAnterior = "";
        String anioActual= quincena.split("/")[0];
        String mesActual= quincena.split("/")[1];
        String qActual= quincena.split("/")[2];
        if(qActual.equals("Q1")){
            if(mesActual.equals("01")){
                anioActual = Integer.toString(Integer.parseInt(anioActual) - 1);
                mesActual = "12";
            }
            else{
                mesActual = Integer.toString(Integer.parseInt(mesActual));
                if ((mesActual.length() == 1) || (mesActual.equals("10"))) {
                    mesActual = Integer.toString(Integer.parseInt(mesActual) - 1);
                    mesActual = "0" + mesActual;
                }else{
                    mesActual = Integer.toString(Integer.parseInt(mesActual) - 1);
                }
            }
            quincenaAnterior = anioActual + "/" + mesActual + "/" + "Q2";
        }
        else if(qActual.equals("Q2")){
            quincenaAnterior = anioActual + "/" + mesActual + "/" + "Q1";
        }
        return quincenaAnterior;
    }

    public String guardar(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null){
            if ((!file.isEmpty()) && (fileName.toUpperCase().equals("DATA.TXT"))){
                try{
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de analisis del Laboratiorio guardado");
                }
                catch (IOException e){
                    logg.error("Error", e);
                }
            }
            return "Archivo de analisis del Laboratiorio guardado";
        }else {
            return "No se guardo el Archivo de analisis del Laboratiorio";
        }
    }

    public void leerArchivo(String direccion, String quincena){
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(direccion));
            StringBuilder temp = new StringBuilder();
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDatoDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2],quincena);
                    temp.append("\n").append(bfRead);
                }
            }
            System.out.println("Archivo de analisis del Laboratiorio leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo de analisis del Laboratiorio");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
}
