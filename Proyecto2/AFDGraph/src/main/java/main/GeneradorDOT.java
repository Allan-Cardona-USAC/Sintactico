package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDOT {
    // Campos de la clase
    private String nombre;
    private String numeroCarnet;
    private String carrera;
    private String archivo;

    // Constructor para inicializar los valores
    public GeneradorDOT(String nombre, String numeroCarnet, String carrera, String archivo) {
        this.nombre = nombre;
        this.numeroCarnet = numeroCarnet;
        this.carrera = carrera;
        this.archivo = archivo;
    }

    // Método principal para generar el documento
    public void generarDocumento() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.archivo))) {
            construirHTML(writer);
            System.out.println("Documento generado exitosamente en: " + this.archivo);
        } catch (IOException e) {
            manejarError(e);
        }
    }

    // Método para construir la estructura HTML
    private void construirHTML(BufferedWriter writer) throws IOException {
        writer.write("<!DOCTYPE html>");
        writer.newLine();
        writer.write("<html lang='es'>");
        writer.newLine();
         writer.write("<head>");
        writer.newLine();
        writer.write("<meta charset='UTF-8'>");
        writer.newLine();
        writer.write("<title>DEV</title>");
        writer.newLine();
        writer.write("<link rel='stylesheet' type='text/css' href='Style.css'>");  // Enlace a la hoja de estilos
        writer.write("</head>");
        writer.newLine();
        writer.write("<body>");
        writer.write("<div class='container'>");
        writer.write("<div class='img'>");
        writer.write("<img src='dev.png' alt='Dev' class='ico'>");
        writer.write("</div>");
        escribirCarnet(writer);
         writer.write("</div>");
        writer.write("</body>");
        writer.newLine();
        writer.write("</html>");
    }

    // Método para escribir la sección del carné
    private void escribirCarnet(BufferedWriter writer) throws IOException {
        writer.write("<div class='carnet'>");
        writer.newLine();
        writer.write("<h2>DESARROLLADOR</h2>");
        writer.newLine();
        writer.write("<p><strong>Nombre:</strong> " + this.nombre + "</p>");
        writer.newLine();
        writer.write("<p><strong>Carné:</strong> " + this.numeroCarnet + "</p>");
        writer.newLine();
        writer.write("<p><strong>Carrera:</strong> " + this.carrera + "</p>");
        writer.newLine();
        writer.write("</div>");
        writer.newLine();
    }

    // Método para manejar errores
    private void manejarError(Exception e) {
        System.out.println("Error al generar el documento: " + e.getMessage());
        e.printStackTrace();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNumeroCarnet() { return numeroCarnet; }
    public void setNumeroCarnet(String numeroCarnet) { this.numeroCarnet = numeroCarnet; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public String getArchivo() { return archivo; }
    public void setArchivo(String archivo) { this.archivo = archivo; }
}
 