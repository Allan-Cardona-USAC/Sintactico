/*package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private List<Error> errores;
    private HashMap<String, Mapa> mundos;
    private Mapa mapaActual;
    private int posicion;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        this.errores = new ArrayList<>();
        this.mundos = new HashMap<>();
        this.posicion = 0;
    }

    public HashMap<String, Mapa> analizar() {
        while (posicion < tokens.size()) {
            if (tokenActual() != null && "world".equals(tokenActual().getLexema())) {
                MUNDO();
            } else {
                agregarError("Se esperaba declaración 'world'", tokenActual());
                posicion++;
            }
        }
        return mundos;
    }

    private void MUNDO() {
        Token worldToken = consumir("palabraReservada", "world", "Se esperaba 'world'");
        if (worldToken == null) return;

        Token nombreToken = consumir("cadena", "Se esperaba nombre del mundo");
        if (nombreToken == null) return;

        mapaActual = new Mapa(nombreToken.getLexema().replaceAll("\"", ""));
        
        Token llaveApertura = consumir("llaveApertura", "{", "Se esperaba '{'");
        if (llaveApertura == null) return;

        ELEMENTOS();

        Token llaveCierre = consumir("llaveCierre", "}", "Se esperaba '}'");
        if (llaveCierre == null) return;

        mundos.put(mapaActual.getNombre(), mapaActual);
    }

    private void ELEMENTOS() {
        while (posicion < tokens.size()) {
            Token actual = tokenActual();
            if (actual == null) break;

            switch (actual.getLexema()) {
                case "place":
                    LOCACION();
                    break;
                case "connect":
                    CONEXION();
                    break;
                case "object":
                    OBJETO();
                    break;
                case "}":
                    return; // Fin del mundo
                default:
                    agregarError("Elemento no reconocido: " + actual.getLexema(), actual);
                    posicion++;
                    break;
            }
        }
    }

    private void LOCACION() {
        Token placeToken = consumir("palabraReservada", "place", "Se esperaba 'place'");
        if (placeToken == null) return;

        Token nombre = consumir("identificador", "Nombre de lugar inválido");
        if (nombre == null) return;

        Token dosPuntos = consumir("dosPuntos", ":", "Se esperaba ':'");
        if (dosPuntos == null) return;

        Token tipo = consumir("tipoLugar", "Tipo de lugar no válido");
        if (tipo == null) return;

        Token atToken = consumir("palabraReservada", "at", "Se esperaba 'at'");
        if (atToken == null) return;

        Token parentesisApertura = consumir("ParentesisApertura", "(", "Se esperaba '('");
        if (parentesisApertura == null) return;

        Token x = consumir("numero", "Coordenada X inválida");
        if (x == null) return;

        Token coma = consumir("coma", ",", "Se esperaba ','");
        if (coma == null) return;

        Token y = consumir("numero", "Coordenada Y inválida");
        if (y == null) return;

        Token parentesisCierre = consumir("ParentesisCierre", ")", "Se esperaba ')'");
        if (parentesisCierre == null) return;

        mapaActual.agregarLugar(new Lugar(
            nombre.getLexema(),
            tipo.getLexema(),
            Integer.parseInt(x.getLexema()),
            Integer.parseInt(y.getLexema())
        ));
    }

    private void CONEXION() {
        Token connectToken = consumir("palabraReservada", "connect", "Se esperaba 'connect'");
        if (connectToken == null) return;

        Token origen = consumir("identificador", "Lugar origen inválido");
        if (origen == null) return;

        Token toToken = consumir("palabraReservada", "to", "Se esperaba 'to'");
        if (toToken == null) return;

        Token destino = consumir("identificador", "Lugar destino inválido");
        if (destino == null) return;

        Token withToken = consumir("palabraReservada", "with", "Se esperaba 'with'");
        if (withToken == null) return;

        Token tipo = consumir("cadena", "Tipo de conexión inválido");
        if (tipo == null) return;

        if (!mapaActual.existeLugar(origen.getLexema())) {
            agregarError("Lugar no existe: " + origen.getLexema(), origen);
        }
        if (!mapaActual.existeLugar(destino.getLexema())) {
            agregarError("Lugar no existe: " + destino.getLexema(), destino);
        }

        mapaActual.agregarConexion(new Conexion(
            origen.getLexema(),
            destino.getLexema(),
            tipo.getLexema().replaceAll("\"", "")
        ));
    }

    private void OBJETO() {
        Token objectToken = consumir("palabraReservada", "object", "Se esperaba 'object'");
        if (objectToken == null) return;

        Token nombre = consumir("cadena", "Nombre de objeto inválido");
        if (nombre == null) return;

        Token dosPuntos = consumir("dosPuntos", ":", "Se esperaba ':'");
        if (dosPuntos == null) return;

        Token tipo = consumir("tipoObjeto", "Tipo de objeto inválido");
        if (tipo == null) return;

        Token atToken = consumir("palabraReservada", "at", "Se esperaba 'at'");
        if (atToken == null) return;

        if (tokenActual() != null && "(".equals(tokenActual().getLexema())) {
            consumir("ParentesisApertura", "(", "Se esperaba '('");
            Token x = consumir("numero", "Coordenada X inválida");
            Token coma = consumir("coma", ",", "Se esperaba ','");
            Token y = consumir("numero", "Coordenada Y inválida");
            consumir("ParentesisCierre", ")", "Se esperaba ')'");
            
            mapaActual.agregarObjeto(new Objeto(
                nombre.getLexema().replaceAll("\"", ""),
                tipo.getLexema(),
                "(" + x.getLexema() + "," + y.getLexema() + ")"
            ));
        } else {
            Token ubicacion = consumir("identificador", "Ubicación inválida");
            mapaActual.agregarObjeto(new Objeto(
                nombre.getLexema().replaceAll("\"", ""),
                tipo.getLexema(),
                ubicacion.getLexema()
            ));
        }
    }

    private Token consumir(String tipoEsperado, String valorEsperado, String mensajeError) {
        if (posicion >= tokens.size()) {
            agregarError(mensajeError + " (fin de archivo)", null);
            return null;
        }
        
        Token token = tokens.get(posicion);
        if (token.getTipo().equals(tipoEsperado) ){
            if (token.getLexema().equals(valorEsperado)) {
                posicion++;
                return token;
            } else {
                agregarError(mensajeError, token);
                posicion++;
                return null;
            }
        } else {
            agregarError(mensajeError, token);
            posicion++;
            return null;
        }
    }

    private Token consumir(String tipoEsperado, String mensajeError) {
        if (posicion >= tokens.size()) {
            agregarError(mensajeError + " (fin de archivo)", null);
            return null;
        }
        
        Token token = tokens.get(posicion);
        if (token.getTipo().equals(tipoEsperado)) {
            posicion++;
            return token;
        } else {
            agregarError(mensajeError, token);
            posicion++;
            return null;
        }
    }

    private Token tokenActual() {
        return (posicion < tokens.size()) ? tokens.get(posicion) : null;
    }

    private void agregarError(String mensaje, Token token) {
        errores.add(new Error(
            (token != null) ? token.getLexema() : "EOF",
            "Error Sintáctico: " + mensaje,
            (token != null) ? token.getLinea() : -1,
            (token != null) ? token.getColumna() : -1
        ));
    }

    public List<Error> getErrores() {
        return new ArrayList<>(errores);
    }
}*/

/*
package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private List<Error> errores;
    private HashMap<String, Mapa> mundos;
    private Mapa mapaActual;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        this.errores = new ArrayList<>();
        this.mundos = new HashMap<>();
    }

    public HashMap<String, Mapa> analizar() {
        try {
            INICIO();
            return mundos;
        } catch (Exception e) {
            return mundos;
        }
    }

    private void INICIO() {
        if (!tokens.isEmpty()) MUNDOS();
    }

    private void MUNDOS() {
        while (!tokens.isEmpty()) {
            MUNDO();
            if (!tokens.isEmpty() && tokens.get(0).getTipo().equals("coma")) {
                tokens.remove(0); // Consumir coma
            }
        }
    }

    private void MUNDO() {
        try {
            consumir("palabraReservada", "world", "Se esperaba 'world'");
            Token nombre = consumir("cadena", "Nombre de mundo inválido");
            consumir("llaveApertura", "{", "Se esperaba '{'");
            
            mapaActual = new Mapa(nombre.getLexema().replaceAll("\"", ""));
            LOCACIONES();
            CONEXIONES();
            OBJETOS();
            
            mundos.put(mapaActual.getNombre(), mapaActual);
            consumir("llaveCierre", "}", "Se esperaba '}'");
            
        } catch (Exception e) {
            errores.add(new Error("", "Estructura de mundo incompleta", -1, -1));
        }
    }

    private void LOCACIONES() {
        while (!tokens.isEmpty() && tokens.get(0).getLexema().equals("place")) {
            LOCACION();
        }
    }

    private void LOCACION() {
        try {
            consumir("palabraReservada", "place", "Se esperaba 'place'");
            Token nombre = consumir("identificador", "Nombre de lugar inválido");
            consumir("dosPuntos", ":", "Se esperaba ':'");
            Token tipo = consumir("tipoLugar", "Tipo de lugar inválido");
            consumir("palabraReservada", "at", "Se esperaba 'at'");
            consumir("ParentesisApertura", "(", "Se esperaba '('");
            
            Token x = consumir("numero", "Coordenada X inválida");
            consumir("coma", ",", "Se esperaba ','");
            Token y = consumir("numero", "Coordenada Y inválida");
            consumir("ParentesisCierre", ")", "Se esperaba ')'");
            
            mapaActual.agregarLugar(new Lugar(
                nombre.getLexema(),
                tipo.getLexema(),
                Integer.parseInt(x.getLexema()),
                Integer.parseInt(y.getLexema())
            ));
            
        } catch (Exception e) {
            errores.add(new Error("", "Estructura de lugar incompleta", -1, -1));
        }
    }

    private void CONEXIONES() {
        while (!tokens.isEmpty() && tokens.get(0).getLexema().equals("connect")) {
            CONEXION();
        }
    }

    private void CONEXION() {
        try {
            consumir("palabraReservada", "connect", "Se esperaba 'connect'");
            Token origen = consumir("identificador", "Lugar origen inválido");
            consumir("palabraReservada", "to", "Se esperaba 'to'");
            Token destino = consumir("identificador", "Lugar destino inválido");
            consumir("palabraReservada", "with", "Se esperaba 'with'");
            Token tipo = consumir("cadena", "Tipo de conexión inválido");
            
            mapaActual.agregarConexion(new Conexion(
                origen.getLexema(),
                destino.getLexema(),
                tipo.getLexema().replaceAll("\"", "")
            ));
            
        } catch (Exception e) {
            errores.add(new Error("", "Estructura de conexión incompleta", -1, -1));
        }
    }

    private void OBJETOS() {
        while (!tokens.isEmpty() && tokens.get(0).getLexema().equals("object")) {
            OBJETO();
        }
    }

    private void OBJETO() {
        try {
            consumir("palabraReservada", "object", "Se esperaba 'object'");
            Token nombre = consumir("cadena", "Nombre de objeto inválido");
            consumir("dosPuntos", ":", "Se esperaba ':'");
            Token tipo = consumir("tipoObjeto", "Tipo de objeto inválido");
            consumir("palabraReservada", "at", "Se esperaba 'at'");
            
            if (tokens.get(0).getLexema().equals("(")) {
                consumir("ParentesisApertura", "(", "Se esperaba '('");
                Token x = consumir("numero", "Coordenada X inválida");
                consumir("coma", ",", "Se esperaba ','");
                Token y = consumir("numero", "Coordenada Y inválida");
                consumir("ParentesisCierre", ")", "Se esperaba ')'");
                mapaActual.agregarObjeto(new Objeto(
                    nombre.getLexema().replaceAll("\"", ""),
                    tipo.getLexema(),
                    "(" + x.getLexema() + "," + y.getLexema() + ")"
                ));
            } else {
                Token ubicacion = consumir("identificador", "Ubicación inválida");
                mapaActual.agregarObjeto(new Objeto(
                    nombre.getLexema().replaceAll("\"", ""),
                    tipo.getLexema(),
                    ubicacion.getLexema()
                ));
            }
        } catch (Exception e) {
            errores.add(new Error("", "Estructura de objeto incompleta", -1, -1));
        }
    }

    private Token consumir(String tipo, String valor, String mensajeError) throws Exception {
        if (tokens.isEmpty()) {
            errores.add(new Error("EOF", mensajeError, -1, -1));
            throw new Exception(mensajeError);
        }
        
        Token token = tokens.remove(0);
        if (!token.getTipo().equals(tipo) || !token.getLexema().equals(valor)) {
            errores.add(new Error(token.getLexema(), mensajeError, token.getLinea(), token.getColumna()));
            throw new Exception(mensajeError);
        }
        return token;
    }

    private Token consumir(String tipo, String mensajeError) throws Exception {
        if (tokens.isEmpty()) {
            errores.add(new Error("EOF", mensajeError, -1, -1));
            throw new Exception(mensajeError);
        }
        
        Token token = tokens.remove(0);
        if (!token.getTipo().equals(tipo)) {
            errores.add(new Error(token.getLexema(), mensajeError, token.getLinea(), token.getColumna()));
            throw new Exception(mensajeError);
        }
        return token;
    }

    public List<Error> getErrores() {
        return new ArrayList<>(errores);
    }
}*/