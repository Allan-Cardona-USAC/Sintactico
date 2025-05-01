package main;
import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private String nombre;
    private List<Lugar> lugares;
    private List<Conexion> conexiones;
    private List<Objeto> objetos;

    // Constructor
    public Mapa(String nombre) {
        this.nombre = nombre;
        this.lugares = new ArrayList<>();
        this.conexiones = new ArrayList<>();
        this.objetos = new ArrayList<>();
    }
    public boolean existeLugar(String nombreLugar) {
        return lugares.stream()
            .anyMatch(lugar -> lugar.getNombre().equalsIgnoreCase(nombreLugar));
    }   
    // ================== MÉTODOS PARA AGREGAR ELEMENTOS ==================
    public void agregarLugar(Lugar lugar) {
        this.lugares.add(lugar);
    }

    public void agregarConexion(Conexion conexion) {
        this.conexiones.add(conexion);
    }

    public void agregarObjeto(Objeto objeto) {
        this.objetos.add(objeto);
    }

    // ================== GETTERS ==================
    public String getNombre() {
        return nombre;
    }

    public List<Lugar> getLugares() {
        return new ArrayList<>(lugares); // Devuelve copia para mantener encapsulamiento
    }

    public List<Conexion> getConexiones() {
        return new ArrayList<>(conexiones);
    }

    public List<Objeto> getObjetos() {
        return new ArrayList<>(objetos);
    }

    // ================== MÉTODO AUXILIAR ==================
    @Override
    public String toString() {
        return "Mapa: " + nombre + 
               "\nLugares: " + lugares.size() +
               "\nConexiones: " + conexiones.size() + 
               "\nObjetos: " + objetos.size();
    }
}