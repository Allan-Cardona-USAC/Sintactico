package main;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidadorTipos {
    // ================== LUGARES ==================
    private static final Map<String, String> FORMAS_LUGARES = new HashMap<>();
    private static final Map<String, String> COLORES_LUGARES = new HashMap<>();
    private static final Set<String> LUGARES_VALIDOS = Set.of(
        "playa", "cueva", "templo", "jungla", "montaña", 
        "pueblo", "isla", "río", "volcán", "pantano"
    );

    // ================== OBJETOS ==================
    private static final Map<String, String> FORMAS_OBJETOS = new HashMap<>();
    private static final Map<String, String> COLORES_OBJETOS = new HashMap<>();
    private static final Set<String> OBJETOS_VALIDOS = Set.of(
        "\\uD83C\\uDF81 tesoro", "llave", "arma", "objeto_mágico", 
        "poción", "trampa", "libro", "herramienta", "bandera", "gema"
    );

    // ================== CONEXIONES ==================
    private static final Map<String, String> ESTILOS_CONEXIONES = new HashMap<>();
    private static final Map<String, String> COLORES_CONEXIONES = new HashMap<>();
    private static final Set<String> CONEXIONES_VALIDAS = Set.of(
        "puente", "sendero", "carretera", "nado", "lancha", "teleférico"
    );

    // ================== INICIALIZACIÓN ESTÁTICA ==================
    static {
        inicializarLugares();
        inicializarObjetos();
        inicializarConexiones();
    }

    // ================== MÉTODOS DE INICIALIZACIÓN ==================
    private static void inicializarLugares() {
        // Formas
        FORMAS_LUGARES.put("playa", "ellipse");
        FORMAS_LUGARES.put("cueva", "box");
        FORMAS_LUGARES.put("templo", "octagon");
        FORMAS_LUGARES.put("jungla", "parallelogram");
        FORMAS_LUGARES.put("montaña", "triangle");
        FORMAS_LUGARES.put("pueblo", "house");
        FORMAS_LUGARES.put("isla", "invtriangle");
        FORMAS_LUGARES.put("río", "hexagon");
        FORMAS_LUGARES.put("volcán", "doublecircle");
        FORMAS_LUGARES.put("pantano", "trapezium");

        // Colores
        COLORES_LUGARES.put("playa", "lightblue");
        COLORES_LUGARES.put("cueva", "gray");
        COLORES_LUGARES.put("templo", "gold");
        COLORES_LUGARES.put("jungla", "forestgreen");
        COLORES_LUGARES.put("montaña", "sienna");
        COLORES_LUGARES.put("pueblo", "burlywood");
        COLORES_LUGARES.put("isla", "lightgoldenrod");
        COLORES_LUGARES.put("río", "deepskyblue");
        COLORES_LUGARES.put("volcán", "orangered");
        COLORES_LUGARES.put("pantano", "darkseagreen");
    }

    private static void inicializarObjetos() {
        // Formas
        FORMAS_OBJETOS.put("tesoro", "box3d");
        FORMAS_OBJETOS.put("llave", "pentagon");
        FORMAS_OBJETOS.put("arma", "diamond");
        FORMAS_OBJETOS.put("objeto_mágico", "component");
        FORMAS_OBJETOS.put("poción", "cylinder");
        FORMAS_OBJETOS.put("trampa", "hexagon");
        FORMAS_OBJETOS.put("libro", "note");
        FORMAS_OBJETOS.put("herramienta", "folder");
        FORMAS_OBJETOS.put("bandera", "tab");
        FORMAS_OBJETOS.put("gema", "egg");

        // Colores
        COLORES_OBJETOS.put("tesoro", "gold");
        COLORES_OBJETOS.put("llave", "lightsteelblue");
        COLORES_OBJETOS.put("arma", "orangered");
        COLORES_OBJETOS.put("objeto_mágico", "violet");
        COLORES_OBJETOS.put("poción", "plum");
        COLORES_OBJETOS.put("trampa", "crimson");
        COLORES_OBJETOS.put("libro", "navajowhite");
        COLORES_OBJETOS.put("herramienta", "darkkhaki");
        COLORES_OBJETOS.put("bandera", "white");
        COLORES_OBJETOS.put("gema", "deepskyblue");
    }

    private static void inicializarConexiones() {
        // Estilos
        ESTILOS_CONEXIONES.put("puente", "solid");
        ESTILOS_CONEXIONES.put("sendero", "dotted");
        ESTILOS_CONEXIONES.put("carretera", "dashed");
        ESTILOS_CONEXIONES.put("nado", "solid");
        ESTILOS_CONEXIONES.put("lancha", "dashed");
        ESTILOS_CONEXIONES.put("teleférico", "solid");

        // Colores
        COLORES_CONEXIONES.put("puente", "black");
        COLORES_CONEXIONES.put("sendero", "gray");
        COLORES_CONEXIONES.put("carretera", "saddlebrown");
        COLORES_CONEXIONES.put("nado", "darkgray");
        COLORES_CONEXIONES.put("lancha", "deepskyblue");
        COLORES_CONEXIONES.put("teleférico", "blue");
    }

    // ================== MÉTODOS PÚBLICOS ==================
    public static boolean esTipoLugarValido(String tipo) {
        return LUGARES_VALIDOS.contains(tipo.toLowerCase());
    }

    public static boolean esTipoObjetoValido(String tipo) {
        return OBJETOS_VALIDOS.contains(tipo.toLowerCase());
    }

    public static boolean esTipoConexionValido(String tipo) {
        return CONEXIONES_VALIDAS.contains(tipo.toLowerCase());
    }

    public static String obtenerFormaLugar(String tipo) {
        return FORMAS_LUGARES.getOrDefault(tipo.toLowerCase(), "ellipse");
    }

    public static String obtenerColorLugar(String tipo) {
        return COLORES_LUGARES.getOrDefault(tipo.toLowerCase(), "white");
    }

    public static String obtenerFormaObjeto(String tipo) {
        return FORMAS_OBJETOS.getOrDefault(tipo.toLowerCase(), "box");
    }

    public static String obtenerColorObjeto(String tipo) {
        return COLORES_OBJETOS.getOrDefault(tipo.toLowerCase(), "white");
    }

    public static String obtenerEstiloConexion(String tipo) {
        return ESTILOS_CONEXIONES.getOrDefault(tipo.toLowerCase(), "solid");
    }

    public static String obtenerColorConexion(String tipo) {
        return COLORES_CONEXIONES.getOrDefault(tipo.toLowerCase(), "black");
    }
}