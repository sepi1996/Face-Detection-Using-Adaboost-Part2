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
public class ClasificadorFuerte {
    
    private ArrayList<ClasificadorDebil> arrayClasificadoresDebiles;
     
    // Constructor
    public ClasificadorFuerte() 
    {
        this.arrayClasificadoresDebiles = new ArrayList<ClasificadorDebil>();
    }
    
    public int getNumClasificadorFuerte(){
        return arrayClasificadoresDebiles.size();
    }
    
    public void adaBoost(ArrayList<Cara> arrayAprendizaje, int[] minPoints, int[] maxPoints, int numClasificadoresDebiles, int numIteracionesClasificadorFuerte, ArrayList<Cara> arrayTest){
        //variables a utilizar
         ClasificadorDebil conjuntoClasificadoresDebiles = null;
        //Inicializamos el vector de pesos
        //Primer paso del algoritmo adaBoost
        for(int i = 0; i < arrayAprendizaje.size(); i++){
            arrayAprendizaje.get(i).setpeso((double) 1.0/arrayAprendizaje.size());
        }
        
        //Segundo paso del algoritmo adaBoost
        for(int i = 0; i < numIteracionesClasificadorFuerte; i++){
            //Step 1
            //Generamos el clasificador debil. Primero generamos "numClasificadoresDebiles", clasificadores debiles
            //El objetivo de este clasificador, sera clasificar bien lo que antes estaba mal
            conjuntoClasificadoresDebiles = new ClasificadorDebil(numClasificadoresDebiles, minPoints, maxPoints);
            
            //De todos los generados debemos elegir el mejor. Para ello primero los entrenamos, y finalmente nos quedamos con el mejor
            conjuntoClasificadoresDebiles.aprendizaje(arrayAprendizaje);
            
            //La llamada anterior, habra guardado el mejor hiperplanoDebil, en la variable "mejorHiperplano", de la clase ClasificadorDebil. Vamos a alamcenar este clasificador
            arrayClasificadoresDebiles.add(conjuntoClasificadoresDebiles);
            
            //Step 2
            //Calculamos confianza para ese clasificador:
            double valorConfianza = conjuntoClasificadoresDebiles.getconfianzaHiperplano();
            
            //Step 3
            //Actualizamos la distribucion de peso
            //Z es una constante de normalizacion
            double z = 0.0;
            //Actualizamos el array de pesos
            for(int j = 0; j < arrayAprendizaje.size() - 1; j++){
                Cara c = arrayAprendizaje.get(j);
                // Si es correcto
                if(arrayAprendizaje.get(j).getTipo() != conjuntoClasificadoresDebiles.ubicacionPunto(conjuntoClasificadoresDebiles.getmejorHiperplano(), c)){
                     arrayAprendizaje.get(j).setpeso(c.getpeso() * (Math.pow(Math.E,valorConfianza)));
                }
                //Si no lo es
                else{
                    arrayAprendizaje.get(j).setpeso(c.getpeso() * (Math.pow(Math.E,-valorConfianza)));
                }    
            }
            
            for(int j = 0; j < arrayAprendizaje.size(); j++){
                z = z + arrayAprendizaje.get(i).getpeso();
            }
            for (Cara f : arrayAprendizaje) f.setpeso(f.getpeso() / z);
            
            int aciertosaprendizaje = valorarClasificador(arrayAprendizaje);
            
            System.out.println("Iteración " + (i + 1) + "del aprendizaje: " + aciertosaprendizaje + "/" + arrayAprendizaje.size() + " (" + (100.0 * aciertosaprendizaje/arrayAprendizaje.size()) + "%)");
            
            int aciertostest = valorarClasificador(arrayTest);
            
            System.out.println("Iteración " + (i + 1) + "del test: " + aciertostest + "/" + arrayTest.size() + " (" + (100.0 * aciertostest/arrayTest.size()) + "%)");
            
            //Si es perfecto terminamos
            if(aciertosaprendizaje == arrayAprendizaje.size()){
                i = numIteracionesClasificadorFuerte;               
            }
                
            
        }
        //System.out.println("El mejor ha botenido: " + this.valorarClasificador(arrayAprendizaje)+ "  " + aciertosMejorHiperplano + " aciertos sobre " + arrayAprendizaje.size() + " imagenes.");
    }
    
    
    public int valorarClasificador(ArrayList<Cara> arrayAprendizaje){
        int aciertos = 0;
        for(int i = 0; i < arrayAprendizaje.size(); i++){
            if( posicionEnHiperplanoCara(arrayAprendizaje.get(i)) == arrayAprendizaje.get(i).getTipo()){
                aciertos++;
            }
        }
        return aciertos;
    }
    
    public int posicionEnHiperplanoCara(Cara c){ 
        double resultadoMejoresHiperplanos = 0.0;
        for(int i = 0; i < arrayClasificadoresDebiles.size(); i++){
            resultadoMejoresHiperplanos += arrayClasificadoresDebiles.get(i).getconfianzaHiperplano() * arrayClasificadoresDebiles.get(i).getmejorHiperplano().evaluar(c.getData());
        }
        if(resultadoMejoresHiperplanos < 0.0)
            return -1;
        else
            return 1;
    }
}
