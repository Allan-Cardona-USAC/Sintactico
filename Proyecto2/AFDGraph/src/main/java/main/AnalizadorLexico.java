/*package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnalizadorLexico {
    private List<Token> tokens;
    private List<Error> errores;
    private int linea;
    private int columna;
    private StringBuilder buffer;
    private int estado;
    private int posicion;
    private boolean enDeclaracionPlace;
    private boolean enDeclaracionObject;

    // Tipos de lugares y objetos válidos (según documento)
    private static final Set<String> TIPOS_LUGAR = Set.of(
        "playa", "cueva", "templo", "jungla", "montaña",
        "pueblo", "isla", "río", "volcán", "pantano"
    );
    
    private static final Set<String> TIPOS_OBJETO = Set.of(
        "tesoro", "llave", "arma", "objeto_mágico", 
        "poción", "trampa", "libro", "herramienta", 
        "bandera", "gema"
    );

    public AnalizadorLexico() {
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.linea = 1;
        this.columna = 1;
        this.buffer = new StringBuilder();
        this.estado = 0;
        this.enDeclaracionPlace = false;
        this.enDeclaracionObject = false;
    }

    public void analizar(StringBuilder entrada) {
        resetearEstado();
        while (posicion < entrada.length()) {
            char c = entrada.charAt(posicion);
            manejarCaracter(c, entrada);
            posicion++;
        }
        procesarUltimoToken();
    }

    private void manejarCaracter(char c, StringBuilder entrada) {
        switch (estado) {
            case 0: estadoInicial(c); break;
            case 1: procesarIdentificador(c); break;
            case 2: procesarCadena(c); break;
            case 3: procesarNumero(c); break;
            case 4: procesarComentario(c, entrada); break;
        }
        actualizarPosicion(c);
    }

    private void estadoInicial(char c) {
        if (Character.isLetter(c)) {
            buffer.append(c);
            estado = 1;
        } else if (c == '"') {
            estado = 2;
        } else if (Character.isDigit(c)) {
            buffer.append(c);
            estado = 3;
        } else if (c == '/') {
            estado = 4;
        } else {
            manejarSimbolo(c);
        }
    }

    private void procesarIdentificador(char c) {
        if (Character.isLetterOrDigit(c)) {
            buffer.append(c);
        } else {
            terminarToken();
            posicion--; // Reprocesar el carácter
        }
    }

    private void procesarCadena(char c) {
        if (c == '"') {
            terminarToken();
        } else {
            buffer.append(c);
        }
    }

    private void procesarNumero(char c) {
        if (Character.isDigit(c)) {
            buffer.append(c);
        } else {
            terminarToken();
            posicion--; // Reprocesar el carácter
        }
    }

    private void procesarComentario(char c, StringBuilder entrada) {
        if (c == '/') {
            // Ignorar comentario de línea
            while (posicion < entrada.length() && entrada.charAt(posicion) != '\n') {
                posicion++;
            }
            estado = 0;
        } else {
            manejarError('/' + String.valueOf(c));
        }
    }

    private void manejarSimbolo(char c) {
        switch (c) {
            case '{': agregarToken("llaveApertura"); break;
            case '}': agregarToken("llaveCierre"); break;
            case ':': agregarToken("dosPuntos"); break;
            case '(': agregarToken("ParentesisApertura"); break;
            case ')': agregarToken("ParentesisCierre"); break;
            case ',': agregarToken("coma"); break;
            case '\n':
                linea++;
                columna = 0;
                break;
            case ' ':
            case '\t':
                break; // Ignorar espacios
            default:
                manejarError(String.valueOf(c));
        }
    }

    private void terminarToken() {
        if (buffer.length() > 0) {
            String lexema = buffer.toString();
            String tipo = determinarTipoToken(lexema);
            tokens.add(new Token(lexema, linea, columna - lexema.length(), tipo));
            buffer.setLength(0);
        }
        estado = 0;
    }

    private String determinarTipoToken(String lexema) {
        // Palabras reservadas
        if (Set.of("world", "place", "connect", "object", "at", "with", "to").contains(lexema)) {
            if (lexema.equals("place")) enDeclaracionPlace = true;
            if (lexema.equals("object")) enDeclaracionObject = true;
            return "palabraReservada";
        }
        
        // Tipos de lugar u objeto
        if (enDeclaracionPlace && TIPOS_LUGAR.contains(lexema)) {
            enDeclaracionPlace = false;
            return "tipoLugar";
        }
        if (enDeclaracionObject && TIPOS_OBJETO.contains(lexema)) {
            enDeclaracionObject = false;
            return "tipoObjeto";
        }
        
        // Identificadores
        return "identificador";
    }

    private void agregarToken(String tipo) {
        tokens.add(new Token(String.valueOf(buffer), linea, columna, tipo));
        buffer.setLength(0);
    }

    private void manejarError(String caracter) {
        errores.add(new Error(
            caracter,
            "Carácter no reconocido",
            linea,
            columna
        ));
    }

    private void actualizarPosicion(char c) {
        columna++;
        if (c == '\n') {
            linea++;
            columna = 1;
        }
    }

    private void resetearEstado() {
        tokens.clear();
        errores.clear();
        buffer.setLength(0);
        linea = 1;
        columna = 1;
        estado = 0;
        posicion = 0;
    }

    private void procesarUltimoToken() {
        if (buffer.length() > 0) {
            terminarToken();
        }
    }

    // ================== GETTERS ==================
    public List<Token> getTokens() { return new ArrayList<>(tokens); }
    public List<Error> getErrores() { return new ArrayList<>(errores); }
}*/

package main;
import java.util.ArrayList;
import java.util.List;


public class AnalizadorLexico {
    private List<Token> tokens;
    private List<Error> errores;
    private int linea;
    private int columna;
    private String buffer;
    private int estado;
    private int i;
    private boolean enDeclaracionPlace; // Nuevo: contexto para validar tipos
    
    public AnalizadorLexico(){
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
        this.linea = 1;
        this.columna = 0;
        this.enDeclaracionPlace = false;
    }
    
    // ================== MÉTODOS PRINCIPALES ==================
    public void analizar(StringBuilder cadena){
        resetearEstado();
        while(this.i < cadena.length()){
            char actual = cadena.charAt(this.i);
            manejarEstado(actual);
            this.i++;
        }
    }

    private void manejarEstado(char caracter) {
        switch(this.estado){
            case 0: S0(caracter); break;
            case 1: S1(caracter); break;
            case 3: S3(caracter); break;
            case 5: S5(caracter); break; // Nuevo estado para números
            case 6: S6(caracter); break; // Estado para '('
            case 7: S7(caracter); break; 

        }
    }
    
    // ================== ESTADOS DEL ANALIZADOR ==================
    private void S0(char caracter) {
        if (caracter == '{' || caracter == '}' || caracter == ':' || caracter == ',' || caracter == '(' || caracter == ')') {
            manejarSimboloIndividual(caracter);
        } 
        else if (Character.isLetter(caracter)) {
            buffer += caracter;
            columna++;
            estado = 1;
        } 
        else if (caracter == '"') {
            buffer += caracter;
            columna++;
            estado = 3;
        } 
        else if (Character.isDigit(caracter)) {
            buffer += caracter;
            columna++;
            estado = 5;
        } 
        else if (caracter == '\n' || caracter == ' ' || caracter == '\t') {
            manejarEspacios(caracter);
        } 
        else {
            manejarError(caracter);
        }
    }

    // Estado para identificadores y palabras reservadas
    private void S1(char caracter) {
        if (Character.isLetterOrDigit(caracter)) {
            buffer += caracter;
            columna++;
        } else {
            validarPalabraReservada();
            estado = 0;
            i--; // Retroceder para reprocesar el carácter
        }
    }

    // Estado para cadenas
    private void S3(char caracter) {
        if (caracter != '"') {
            buffer += caracter;
            columna++;
        } else {
            buffer += caracter;
            agregarToken(buffer, "cadena", linea, columna);
            estado = 0;
            columna++;
        }
    }

    // Estado para números
    private void S5(char caracter) {
        if (Character.isDigit(caracter)) {
            buffer += caracter;
            columna++;
        } else {
            agregarToken(buffer, "numero", linea, columna);
            estado = 0;
            i--;
        }
    }

    // Estado para '(' en coordenadas
    private void S6(char caracter) {
        if (Character.isDigit(caracter)) {
            buffer += caracter;
            columna++;
            estado = 5;
        } else {
            manejarError(caracter);
        }
    }

    //Manejo de coma en coordenadas 
    private void S7 (char caracter ){
        agregarToken(",", "coma", linea, columna );
        columna ++;
        buffer ="";
        estado = 5 ; // volver a estado de numero para el siguiente digito 
        i--; //reprocesar el caracter actual 
    }

    // ================== MÉTODOS AUXILIARES ==================
    private void validarPalabraReservada() {
        String[] reservadas = {"world", "place", "connect", "object", "at", "with", "to"};
        for (String palabra : reservadas) {
            if (buffer.equals(palabra)) {
                agregarToken(buffer, "palabraReservada", linea, columna);
                if (palabra.equals("place")) enDeclaracionPlace = true;
                return;
            }
        }
        
        // Validar tipos después de "place" o "object"
        if (enDeclaracionPlace && !ValidadorTipos.esTipoLugarValido(buffer)) {
            errores.add(new Error(buffer, "Tipo de lugar no válido", linea, columna));
        }
        agregarToken(buffer, "identificador", linea, columna);
        enDeclaracionPlace = false;
    }

    private void manejarSimboloIndividual(char caracter) {
        String tipo = "";
        switch(caracter){
            case '{': tipo = "llaveApertura"; break;
            case '}': tipo = "llaveCierre"; break;
            case ':': tipo = "dosPuntos"; break;
            case ',': tipo = "coma"; break;
             case '(': tipo = "ParentesisApertura"; break;
            case ')': tipo = "ParentesisCierre"; break;
        }
        agregarToken(String.valueOf(caracter), tipo, linea, columna);
        columna++;
    }

    private void manejarEspacios(char caracter) {
        if (caracter == '\n') {
            linea++;
            columna = 0;
        } else if (caracter == ' ') {
            columna++;
        } else if (caracter == '\t') {
            columna += 4;
        }
    }

    private void manejarError(char caracter) {
        errores.add(new Error(String.valueOf(caracter), "Carácter no reconocido", linea, columna));
        columna++;
    }

    private void resetearEstado() {
        tokens.clear();
        errores.clear();
        buffer = "";
        estado = 0;
        i = 0;
        linea = 1;
        columna = 0;
    }

    // ================== GETTERS ==================
    public List<Token> getTokens() { return tokens; }
    public List<Error> getErrores() { return errores; }
    
    private void agregarToken(String lexema, String tipo, int linea, int columna) {
        tokens.add(new Token(lexema, linea, columna, tipo));
        buffer = "";
    }
}