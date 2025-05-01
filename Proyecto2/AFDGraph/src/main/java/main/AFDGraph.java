/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.SwingUtilities;

/**
 *
 * @author allan
 */
public class AFDGraph {

    public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> {
            InterfazGrafica interfaz = new InterfazGrafica();
            interfaz.setVisible(true);
            mostrarMensajeInicial(); // Nuevo mensaje inicial
        });
    }

    // Método para mostrar mensaje de bienvenida
    private static void mostrarMensajeInicial() {
        System.out.println("==============================================");
        System.out.println("  Generador de Mapas Narrativos - Proyecto 2  ");
        System.out.println("==============================================");
        System.out.println("Instrucciones:");
        System.out.println("1. Use el boton 'Cargar Archivo' para seleccionar su .lfp");
        System.out.println("2. Seleccione un mapa del menu desplegable");
        System.out.println("3. Genere el grafico con el boton 'Generar Mapa'\n");
    }
}


