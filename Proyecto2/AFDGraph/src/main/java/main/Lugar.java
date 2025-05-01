package main;

public class Lugar {
    private String nombre;
    private String tipo;  // Ejemplo: "playa", "cueva", "templo"
    private int x;        // Coordenada X
    private int y;        // Coordenada Y

    // Constructor
    public Lugar(String nombre, String tipo, int x, int y) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }

    // ================== GETTERS ==================
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // ================== MÉTODO AUXILIAR ==================
    @Override
    public String toString() {
        return String.format(
            "Lugar: %s | Tipo: %s | Posición: (%d, %d)",
            nombre, tipo, x, y
        );
    }
}