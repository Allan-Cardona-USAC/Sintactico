package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterfazGrafica extends JFrame {
    private JButton btnAnalizar, btnGraficar, btnReporteTokens, btnReporteErroreSin,btnReporteErrores, btnLimpiar, btnDatos;
    private JComboBox<String> comboMapas;
    private AnalizadorLexico analizador;
    private String fileContent;
    private HashMap<String, Mapa> mapaMap;
    private JTextArea areaInput, areaDot, areaSelected;
    private List<Error> erroresSintacticos = new ArrayList<>();

    public InterfazGrafica() {
        super("Generador de Mapas Narrativos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(10, 10));

        // Panel superior de controles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(Color.GRAY);
        btnAnalizar = crearBoton("Cargar Archivo");
        btnGraficar = crearBoton("Generar Mapa");
        btnReporteTokens = crearBoton("Reporte Tokens");
        btnReporteErrores = crearBoton("Reporte Errores");
        btnReporteErroreSin = crearBoton("Errores Sintactico");
        btnLimpiar = crearBoton("Limpiar Textos");
        btnDatos = crearBoton("Desarrollador");
        comboMapas = new JComboBox<>();
        comboMapas.setPreferredSize(new Dimension(150, 25));
        controlPanel.add(btnAnalizar);
        controlPanel.add(new JLabel("Mapa:"));
        controlPanel.add(comboMapas);
        controlPanel.add(btnGraficar);
        controlPanel.add(btnReporteTokens);
        controlPanel.add(btnReporteErrores);
        controlPanel.add(btnReporteErroreSin);
        controlPanel.add(btnLimpiar);
        controlPanel.add(btnDatos);
        
        add(controlPanel, BorderLayout.NORTH);

        // Panel central con split pane
        areaInput = new JTextArea();
        areaInput.setEditable(false);
        areaInput.setBackground(Color.WHITE);
        areaInput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollInput = new JScrollPane(areaInput);

        areaDot = new JTextArea();
        areaDot.setEditable(false);
        areaDot.setBackground(Color.WHITE);
        areaDot.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollDot = new JScrollPane(areaDot);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollInput, scrollDot);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        // Panel inferior para texto seleccionado
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.GRAY);
        areaSelected = new JTextArea(3, 40);
        areaSelected.setEditable(false);
        areaSelected.setBackground(Color.WHITE);
        bottomPanel.add(new JLabel("Texto seleccionado:"), BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(areaSelected), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Inicialización
        analizador = new AnalizadorLexico();
        mapaMap = new HashMap<>();

        // Listeners
        btnAnalizar.addActionListener(e -> analizarArchivo());
        btnGraficar.addActionListener(e -> generarMapa());
        btnReporteTokens.addActionListener(e -> generarReporteTokens());
        btnReporteErrores.addActionListener(e -> generarReporteErrores());
        btnReporteErroreSin.addActionListener(e -> mostrarErroresSintacticos() );
        btnLimpiar.addActionListener(e -> limpiarTextos());
        btnDatos.addActionListener(e -> mostrarInformacion());
        areaInput.addCaretListener(e -> {
            String sel = areaInput.getSelectedText();
            areaSelected.setText(sel != null ? sel : "");
        });
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(255, 165, 0)); // naranja
        btn.setForeground(Color.WHITE);
        return btn;
    }
    

    private void analizarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                
                fileContent = sb.toString();
                areaInput.setText(fileContent);
                
          
               analizador.analizar(new StringBuilder(fileContent));
                mapaMap = parseMapas(fileContent);

                comboMapas.removeAllItems();
                mapaMap.keySet().forEach(comboMapas::addItem);
                JOptionPane.showMessageDialog(this,
                mapaMap.isEmpty() ? "No se encontraron mapas" : "Archivo analizado correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }      
    }
   

    private HashMap<String, Mapa> parseMapas(String content) {
        HashMap<String, Mapa> mapas = new HashMap<>();
        Pattern pattern = Pattern.compile("world\\s*\"([^\"]*)\"\\s*\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String nombreMapa = matcher.group(1);
            String bloque = matcher.group(2);
            Mapa mapa = new Mapa(nombreMapa);
            // parse places
            Pattern placePattern = Pattern.compile("place\\s+(\\w+):(\\w+)\\s+at\\s+\\((\\d+),(\\d+)\\)");
            Matcher placeM = placePattern.matcher(bloque);
            while (placeM.find()) {
                mapa.agregarLugar(new Lugar(
                    placeM.group(1), placeM.group(2),
                    Integer.parseInt(placeM.group(3)), Integer.parseInt(placeM.group(4))));
            }
            // parse connects
            Pattern connP = Pattern.compile("connect\\s+(\\w+)\\s+to\\s+(\\w+)\\s+with\\s+\"([^\"]*)\"");
            Matcher connM = connP.matcher(bloque);
            while (connM.find()) {
                mapa.agregarConexion(new Conexion(
                    connM.group(1), connM.group(2), connM.group(3)));
            }
            // parse objects
            Pattern objP = Pattern.compile("object\\s+\"([^\"]*)\":(\\w+)\\s+at\\s+((\\w+)|(\\((\\d+),(\\d+)\\)))");
            Matcher objM = objP.matcher(bloque);
            while (objM.find()) {
                mapa.agregarObjeto(new Objeto(
                    objM.group(1), objM.group(2), objM.group(3)
                ));
            }
            mapas.put(nombreMapa, mapa);
        }
        return mapas;
    }

    private void generarMapa() {
        if (comboMapas.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay mapas para graficar.");
            return;
        }
        String nombreMapa = (String) comboMapas.getSelectedItem();
        Mapa mapa = mapaMap.get(nombreMapa);
        if (mapa == null) {
            JOptionPane.showMessageDialog(this, "Mapa no encontrado.");
            return;
        }
        StringBuilder dotBuilder = new StringBuilder();
        dotBuilder.append("digraph \"").append(nombreMapa).append("\" {\n")
           .append("    rankdir=LR;\n")
           .append("    label=\"Mapa: ").append(nombreMapa).append("\";\n")
           .append("    labelloc=t;\n")
           .append("    node [style=filled];\n\n");
        dotBuilder.append("    // ========== LUGARES ==========\n");
        for (Lugar lugar : mapa.getLugares()) {
            String f = ValidadorTipos.obtenerFormaLugar(lugar.getTipo());
            String c = ValidadorTipos.obtenerColorLugar(lugar.getTipo());
            dotBuilder.append(String.format(
                "    \"%s\" [shape=%s, fillcolor=%s, label=\"%s\\n(%s)\"];\n",
                lugar.getNombre(), f, c, lugar.getNombre(), lugar.getTipo()));
        }
        dotBuilder.append("\n    // ========== CONEXIONES ==========\n");
        for (Conexion con : mapa.getConexiones()) {
            String e = ValidadorTipos.obtenerEstiloConexion(con.getTipo());
            String col = ValidadorTipos.obtenerColorConexion(con.getTipo());
            dotBuilder.append(String.format(
                "    \"%s\" -> \"%s\" [label=\"%s\", style=%s, color=%s];\n",
                con.getLugarA(), con.getLugarB(), con.getTipo(), e, col));
        }
        dotBuilder.append("\n    // ========== OBJETOS ==========\n");
        for (Objeto obj : mapa.getObjetos()) {
            String f = ValidadorTipos.obtenerFormaObjeto(obj.getTipo());
            String c = ValidadorTipos.obtenerColorObjeto(obj.getTipo());
            dotBuilder.append(String.format(
                "    \"%s\" [shape=%s, fillcolor=%s, label=\"%s\"];\n",
                obj.getNombre(), f, c, obj.getNombre()));
            String raw = obj.getUbicacion().trim();
            if (!raw.startsWith("(")) {
                dotBuilder.append(String.format(
                    "    \"%s\" -> \"%s\" [style=dotted, label=\"en\"];\n",
                    raw.replaceAll("[()]", ""), obj.getNombre()));
            }
        }
        dotBuilder.append("}\n");
        String dotText = dotBuilder.toString();
        areaDot.setText(dotText);

        // Generar archivos DOT y PDF, abrir PDF
        try {
            String baseName = nombreMapa.replaceAll("\\s+", "_");
            File dotFile = new File(baseName + ".dot");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(dotFile))) {
                bw.write(dotText);
            }
            File pdfFile = new File(baseName + ".pdf");
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpdf", dotFile.getName(), "-o", pdfFile.getName());
            Process p = pb.start();
            p.waitFor();
            if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
                JOptionPane.showMessageDialog(this, "Mapa generado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al generar el PDF. Verifique Graphviz",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al generar archivos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarTextos() {
        areaDot.setText("");
        areaSelected.setText("");
    }
     private void mostrarInformacion() {
         GeneradorDOT generador = new GeneradorDOT(
            "Allán Cardona",
            "2022-00181",
            "Ingeniería en Sistemas",
            "mi_carnet.html"
        );
        
        generador.generarDocumento();
         System.out.println("Info del desarrollador  ");
    }

    private void generarReporteTokens() {
        List<Token> tokens = analizador.getTokens();
        if(tokens.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron tokens.");
            return;
        }
        String[] cols = {"Tipo","Lexema","Línea","Columna"};
        Object[][] data = new Object[tokens.size()][4];
        for(int i=0;i<tokens.size();i++){
            Token tk = tokens.get(i);
            data[i] = new Object[]{tk.getTipo(),tk.getLexema(),tk.getLinea(),tk.getColumna()};
        }
        showReportTable("Reporte de Tokens", data, cols);
    }

    private void generarReporteErrores() {
        List<Error> errs = analizador.getErrores();
        if(errs.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron errores.");
            return;
        }
        String[] cols = {"Caracter","Descripción","Línea","Columna"};
        Object[][] data = new Object[errs.size()][4];
        for(int i=0;i<errs.size();i++){
            Error er = errs.get(i);
            data[i] = new Object[]{er.getCaracter(),er.getDescripcion(),er.getLinea(),er.getColumna()};
        }
        showReportTable("Reporte de Errores", data, cols);
    }
        // Método para mostrar errores en una tabla
   /* private void mostrarErrores() {
        List<Error> errores = analizador.getErrores();
        if(errores.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron errores Sintacticos.");
            return;
        }
         String[] cols = {"Lexema", "Descripción", "Línea", "Columna"};
            Object[][] data = new Object[errores.size()][4];
    
            for (int i = 0; i < errores.size(); i++) {
                Error err = errores.get(i);
                data[i][0] = err.getCaracter();
                data[i][1] = err.getDescripcion();
                data[i][2] = err.getLinea();
                data[i][3] = err.getColumna();
        
        }
        showReportTable("Reporte de Errores", data, cols);
        }*/
    
 

// Método para mostrar errores sintácticos
private void mostrarErroresSintacticos() {
    String[] columnas = {"Lexema", "Descripción", "Línea", "Columna"};
    Object[][] datos = new Object[erroresSintacticos.size()][4];
    
    for (int i = 0; i < erroresSintacticos.size(); i++) {
        Error error = erroresSintacticos.get(i);
        datos[i][0] = error.getCaracter();
        datos[i][1] = error.getDescripcion();
        datos[i][2] = error.getLinea();
        datos[i][3] = error.getColumna();
    }
    
    showReportTable("Errores Sintácticos", datos, columnas);
}
    private void showReportTable(String title, Object[][] data, String[] columns){
        JTable table = new JTable(data,columns);
        JScrollPane sp = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JDialog dlg = new JDialog(this, title, true);
        dlg.add(sp);
        dlg.setSize(600,400);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica().setVisible(true));
    }
}


