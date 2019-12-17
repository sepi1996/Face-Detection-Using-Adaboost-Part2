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
public class ClasificadorDebil {
    // Array de hiperplanos que debemos crear
    private ArrayList<Hiperplano> arrayHiperplanos = new ArrayList<Hiperplano>();
    // El mejor hiperplano
    private Hiperplano mejorHiperplano;
    // Valor de confianza para este clasificador
    private double confianzaClasificadorDebil;
    
    //Generamos numHiperplanos, hiperplanos aleaotorios.
    public ClasificadorDebil(int numHiperplanos, int[] minPuntos, int[] maxPuntos){
       for(int i = 0; i < numHiperplanos; i++){
           arrayHiperplanos.add(new Hiperplano(minPuntos, maxPuntos));
       }
       this.mejorHiperplano = null;
       this.confianzaClasificadorDebil = 0.0;
    }
    
    //Guardamos el hiperplano con menor tasa de error
    public void mejorHiperplano(){
        Hiperplano mejorHiperplano = arrayHiperplanos.get(0);
        for(int i = 1; i < arrayHiperplanos.size(); i++){
            if(mejorHiperplano.getError() > arrayHiperplanos.get(i).getError()){
                mejorHiperplano = arrayHiperplanos.get(i);
            }
        }
        
        this.confianzaClasificadorDebil = 0.5 * Math.log((1 - mejorHiperplano.getError())/mejorHiperplano.getError());
        this.mejorHiperplano = mejorHiperplano;
        this.arrayHiperplanos = null;
    }
    
    //Con este metodo le asignamos el peso correspondiente a cada hiperplano, para con la ultima llamada elegir el mejor
    public void aprendizaje(ArrayList<Cara> arrayCaras){
        for(int i = 0; i < arrayHiperplanos.size(); i++){
            int error = 0;
            for(int j = 0; j < arrayCaras.size(); j++){
                int posicionEnHiperplanos = 0;
                if(arrayHiperplanos.get(i).evaluar(arrayCaras.get(j).getData()) < 0.0){
                    posicionEnHiperplanos = -1;
                }
                else if(arrayHiperplanos.get(i).evaluar(arrayCaras.get(j).getData()) > 0.0){
                    posicionEnHiperplanos = 1;
                }
                int posicionReal = arrayCaras.get(j).getTipo();
                if(posicionReal != posicionEnHiperplanos){
                    error++;
                }
            }
            double errorHiperplano = (double) error/arrayCaras.size();
            arrayHiperplanos.get(i).setError(errorHiperplano);
        }
        mejorHiperplano();
    }
    
    public Hiperplano getmejorHiperplano(){
        return mejorHiperplano;
    }
    
    public double getconfianzaHiperplano(){
        return confianzaClasificadorDebil;
    }
    
    public int ubicacionPunto(Hiperplano h, Cara c){
        if(h.evaluar(c.getData()) < 0.0)
            return -1;
        else
            return 1;
    }
}
