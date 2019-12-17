/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2siOptativa;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dviejo
 */
public class Practica2SI {

    private String rutaDir;
    private File []files;
    private int NUM_ITERACIONES; 
    private int NUM_CLASIFICADORES;
    private boolean VERBOSE;

    private double testRate;

    private ArrayList<Cara> listaAprendizaje;
    private ArrayList<Cara> listaTest;
    
    //Pepe
    private Hiperplano miHiperplano = new Hiperplano();
    private int[] minPoints = new int[miHiperplano.getDIMENSION()];
    private int[] maxPoints = new int[miHiperplano.getDIMENSION()];
    

    public Practica2SI()
    {
        rutaDir = "";
        testRate = 0.3;
	NUM_ITERACIONES = 12;
	NUM_CLASIFICADORES = 900;
        VERBOSE = false;
    }
    
    public void init()
    {
        int cont;
	int aciertos, clase;
	System.out.println("Sistemas Inteligentes. Segunda practica");
		
	getFileNames(rutaDir+"cara/");
	listaAprendizaje = new ArrayList<Cara>();
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont],1));
		}
	}
	getFileNames(rutaDir+"noCara/");
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont], -1));
		}
	}
	System.out.println(listaAprendizaje.size()+ " imágenes encontradas");
		
	//inicializamos las listas
	listaTest = new ArrayList<Cara>();
		
	//Separamos los conjuntos de aprendizaje y test
	CrearConjuntoAprendizajeyTest();
	System.out.println(listaAprendizaje.size()+" imagenes para aprendizaje, "+listaTest.size()+" imagenes para el test ("+(testRate*100)+"%)");

	//Comenzamos el aprendizaje
	long t1 = System.currentTimeMillis();
        //TODO Aquí debéis poner vuestra llamada al método de entrenamiento de AdaBoost
        
        //PEPE
        encontrarPuntosMinimos();
        encontrarPuntosMaximos();
        ClasificadorFuerte clasificadorFuerte = new ClasificadorFuerte();
        clasificadorFuerte.adaBoost(listaAprendizaje, minPoints, maxPoints, NUM_CLASIFICADORES, NUM_ITERACIONES, listaTest);
	long t2 = System.currentTimeMillis();
        
	long time;
        
	time = t2 - t1;
	System.out.println("Tiempo empleado en el aprendizaje: "+((float)time/1000f)+" segundos");
	System.out.println("Número de clasificadores encontrados: " + clasificadorFuerte.getNumClasificadorFuerte()); 
	//Test final
        if(VERBOSE)
        {
            aciertos = 0;
            for(Cara c: listaAprendizaje)
            {
                clase = clasificadorFuerte.posicionEnHiperplanoCara(c);         
                if(clase == c.getTipo())
                    aciertos++;
            }
            System.out.println("APRENDIZAJE. Tasa de aciertos: "+((float)aciertos/(float)(listaAprendizaje.size())*100.0f)+"%");
        }
        
	//Comprobamos el conjunto de test
	aciertos = 0;
	for(Cara c: listaTest)
	{
                
		clase = clasificadorFuerte.posicionEnHiperplanoCara(c);
                   
                if(clase == c.getTipo())
			aciertos++;
                //else{
                  //  System.out.println(c.getTipo());
                //}

	}
	System.out.println("TEST. Tasa de aciertos: "+((float)aciertos/(float)(listaTest.size())*100.0f)+"%");

        //Optativo
        getFileNames(rutaDir+"prueba/");
        Cara imagen = null;
        File ruta = null;
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			imagen = new Cara(files[cont]);
                        ruta = files[cont];
		}
	}
        
        Cara particionImagen;
        int ancho = imagen.getancho();
        int alto = imagen.getalto();
        int posicionArrayUnidimensional = 0;
        //Este array almacenara los pixeles donde estan las caras, y por tanto deben pintarse.
        ArrayList<Integer> posicionCaras= new ArrayList<Integer>();
        
        for(int i = 0; i < alto; i++){
            for(int j = 0; j < ancho; j++){      
                if(j < ancho - 24 && i < alto -24){
                    particionImagen = null;
                    particionImagen = new Cara(imagen.getData(), posicionArrayUnidimensional, ancho);
                    if(clasificadorFuerte.posicionEnHiperplanoCara(particionImagen) == 1){
                        System.out.println("cara");
                        posicionCaras.add(posicionArrayUnidimensional);
                        System.out.println(posicionArrayUnidimensional);

                    } 
                }
                 posicionArrayUnidimensional++;
            }   
            posicionArrayUnidimensional++;
        }
        imagen.CrearCara(posicionCaras, ruta);
        
        
    }
    
    /**
     * Selecciona al azar un subconjunto para Test. El resto compondrá el conjunto de aprendizaje
     */
    private void CrearConjuntoAprendizajeyTest()
    {
    	int totalTam = listaAprendizaje.size();
	int tamTest = (int)(totalTam * testRate);
	int cont;
	Random rnd = new Random(System.currentTimeMillis());
		
	for(cont=0;cont<tamTest;cont++)
	{
            listaTest.add(listaAprendizaje.remove(rnd.nextInt(totalTam)));
            totalTam--;
	}
    }
    
    public void getFileNames(String ruta)
    {
    	File directorio = new File(ruta);
	if (!directorio.isDirectory())
            throw new RuntimeException("La ruta debe ser un directorio");
	ImageFilter filtro = new ImageFilter();
	files = directorio.listFiles(filtro);
    }

	public void setRuta(String r)
	{
		rutaDir = r;
	}
	
	public void setRate(double t)
	{
		testRate = t;
	}
	
	public void setNumIteraciones(int t)
	{
		NUM_ITERACIONES = t;
	}
	public void setNumClasificadores(int c)
	{
		NUM_CLASIFICADORES = c;
	}

	public void setVerbose(boolean v)
	{
		VERBOSE = v;
	}
	
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		int cont;
		Practica2SI programa;
		String option;
		boolean maluso = true;
		int paso = 2;
		
		programa = new Practica2SI();

		for(cont = 0; cont < args.length; cont+=paso)
		{
			option = args[cont];
			if(option.charAt(0) == '-')
			{
				switch(option.charAt(1))
				{
				case 'd':
					programa.setRuta(args[cont+1]);
					paso = 2;
                                        maluso = false;
					break;
				case 't':
					programa.setRate(Double.parseDouble(args[cont+1]));
					paso = 2;
					break;
				case 'T':
					programa.setNumIteraciones(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'c':
					programa.setNumClasificadores(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'v':
					programa.setVerbose(true);
					paso = 1;
					break;
				default:
					maluso = true;
				}
			}
			else maluso = true;
		}
		
		if(!maluso)
			programa.init();
		else
		{
			System.out.println("Lista de parametros incorrecta");
			System.out.println("Uso: java Practica2SI -d ruta [-t testrate] [-T maxT] [-c numClasificadores] [-v]");
		}
    }
    
    //PEPE
    public void encontrarPuntosMinimos(){
        
        for(int i = 0; i < minPoints.length; i++){
            int min = Integer.MAX_VALUE;
            for(int j = 0; j <  listaAprendizaje.size(); j++){
                if(listaAprendizaje.get(j).getValor(i) < min){
                     minPoints[i] = listaAprendizaje.get(j).getValor(i);
                }
            }          
        }
    }
    public void encontrarPuntosMaximos(){
       
        for(int i = 0; i < maxPoints.length; i++){
            int max = Integer.MIN_VALUE;
            for(int j = 0; j <  listaAprendizaje.size(); j++){
                if(listaAprendizaje.get(j).getValor(i) > max){
                     maxPoints[i] = listaAprendizaje.get(j).getValor(i);
                }
            }
        }
    }
    
}
