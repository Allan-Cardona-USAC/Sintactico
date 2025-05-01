package main;

public class Conexion {
    private final String lugarA;  // Nombre del lugar de origen
    private final String lugarB;  // Nombre del lugar destino
    private final String tipo;    // Tipo de conexión (ej: "sendero", "puente")

    // Constructor
    public Conexion(String lugarA, String lugarB, String tipo) {
        this.lugarA = lugarA;
        this.lugarB = lugarB;
        this.tipo = tipo;
    }

    // ================== GETTERS ==================
    public String getLugarA() {
        return lugarA;
    }

    public String getLugarB() {
        return lugarB;
    }

    public String getTipo() {
        return tipo;
    }

    // ================== MÉTODO AUXILIAR ==================
    @Override
    public String toString() {
        return String.format(
            "Conexión: %s -> %s | Tipo: %s",
            lugarA, lugarB, tipo
        );
    }
}