package main;

public class Objeto {
    private String nombre;
    private String tipo;       // Ejemplo: "tesoro", "llave", "libro"
    private String ubicacion;  // Puede ser coordenadas "(x,y)" o nombre de lugar

    // Constructor
    public Objeto(String nombre, String tipo, String ubicacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    // ================== GETTERS ==================
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    // ================== MÉTODO AUXILIAR ==================
    @Override
    public String toString() {
        return String.format(
            "Objeto: %s | Tipo: %s | Ubicación: %s",
            nombre, tipo, ubicacion
        );
    }
}