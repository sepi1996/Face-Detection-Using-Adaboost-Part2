 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2siOptativa;
import java.util.ArrayList;
/**
 *
 * @author perez
 */
public class Hiperplano {
    private static final int DIMENSION = 576;
    // Tipos de caras
    private static int maxValue = 1;
    private static int minValue = -1;
    // Vector de puntos
    

    private ArrayList<Integer> puntos;
    private ArrayList<Double> vector;
    
    private double C;
    // Tasa de error de ese hiperplano
    private double error;
    
    
    public Hiperplano(){
        //Nada
    }
  
    /*
    Generamos el hiperplano aleatorio entre los puntos indicados por parametro
    */
    public Hiperplano(int[] minPuntos, int[] maxPuntos){
        C = 0.0;
        puntos = new ArrayList<Integer>();
        vector = new ArrayList<Double>();
        //Generamos el punto y el vector aleatorio
        for(int i = 0; i < DIMENSION; i++){
            puntos.add((int)(Math.random() * (maxPuntos[i] - minPuntos[i])) + minPuntos[i]);
            vector.add((Math.random() * (maxValue - minValue)) + minValue);
        }
        // Normalizamos el vector
        double normalizar = 0;
        for(int i = 0; i < DIMENSION; i++)
            normalizar += vector.get(i);
        for(int i = 0; i < DIMENSION; i++)
            vector.set(i, vector.get(i)/normalizar);
        for(int i = 0; i < DIMENSION; i++)
            C += puntos.get(i) * vector.get(i);
    }
    
     public double evaluar(int[] imagen){
        double result = 0;
        for(int i = 0; i < imagen.length; i++){
            result += vector.get(i) * imagen[i];
        }     
        return result - C;
    }
    
    public int getDIMENSION(){
        return DIMENSION;
    }
    public double getError(){
        return error;
    }
    public void setError(double error){
        this.error = error;
    }
    
}
