package main;

/**
 *
 * @author allan
 */
//Clase del error caracter no reconocido 
public class Error {
    private String caracter;
    private String descripcion;
    private int linea;
    private int columna;
    
    public Error(String caracter, String descripcion, int linea, int columna){
        this.caracter = caracter;
        this.descripcion= descripcion;
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public String toString(){
        return( this.descripcion+" Linea: "+linea+" Columna: "+columna);
    }
    //Getters
    public String getCaracter(){
    return this.caracter;
    }
    public String getDescripcion(){
    return this.descripcion;
    }
    public int getLinea(){
    return this.linea;
    }   
    public int getColumna(){
    return this.columna;
    }
}

