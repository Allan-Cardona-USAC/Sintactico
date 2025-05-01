package main;

public class Token {
    private String lexema;
    private int linea;
    private int columna;
    private String tipo;  // Tipos posibles: cadena, número, palabraReservada, 
                          // coordenada, tipoLugar, tipoConexion, tipoObjeto, etc.

    public Token(String lexema, int linea, int columna, String tipo) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Token: " + tipo + " | Lexema: " + lexema + " | Línea: " + linea + " | Columna: " + columna;
    }

    // ================== GETTERS ==================
    public String getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
}
