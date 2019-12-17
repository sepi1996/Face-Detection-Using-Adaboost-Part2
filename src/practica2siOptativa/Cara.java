package practica2siOptativa;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 * Clase cara. Representa un ejemplo para el aprendizaje.
 * @author dviejo
 */
public class Cara {
	private int []data;
	private int tipo;
        private int ancho, alto;
        
        //PEPE
        private double peso;
        
        public void setpeso(double peso){
            this.peso = peso;
        }
        public double getpeso(){
            return peso;
        }

        public int getalto(){
            return alto;
        }

        public int getancho(){
            return ancho;
        }
	
        /**
         * Lee la información de una imagen desde un fichero. Le asigna el tipo
         * que se recibe como parametro
         * @param fcara fichero de entrada
         * @param tipo 1 = cara; -1 = nocara
         */
        public Cara(File fcara, int tipo)
	{
		int mask = 0x000000FF;
		int cont;
		BufferedImage bimage;
		try
		{
			bimage = ImageIO.read(fcara);
			data = bimage.getRGB(0, 0, bimage.getWidth(), bimage.getHeight(), null, 0, bimage.getWidth());
			//Asumiendo que la imagen ya está en escala de grises pero en formato color, 
			//convertimos ARGB en un único valor
			for(cont=0;cont<data.length;cont++){
                            data[cont] = data[cont] & mask;
                            //System.out.println(data[cont]);
                        }
                        
                        
                        
				 
			this.tipo = tipo;
                        this.peso = 0;//Al inicio todas las imagenes tienen el mismo peso
                        ancho = bimage.getWidth();
                        alto = bimage.getHeight();
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
        
         public Cara(File fcara)
	{
		int mask = 0x000000FF;
		int cont;
		BufferedImage bimage;
		try
		{
			bimage = ImageIO.read(fcara);
			data = bimage.getRGB(0, 0, bimage.getWidth(), bimage.getHeight(), null, 0, bimage.getWidth());
			//Asumiendo que la imagen ya está en escala de grises pero en formato color, 
			//convertimos ARGB en un único valor
			for(cont=0;cont<data.length;cont++){
                            data[cont] = data[cont] & mask;
                            //System.out.println(data[cont]);
                        }
                        
                        
				
                        //System.out.println(cont);
			//this.tipo = tipo;
                        ancho = bimage.getWidth();
                        alto = bimage.getHeight();
                        this.peso = 0;//Al inicio todas las imagenes tienen el mismo peso
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
        
        public Cara(int arraypixeles[], int posicion, int ancho){
            int posReal = 0;
            data = new int[576];
            for(int i = posicion; i <posicion + (24 * ancho); i = i + ancho){
                for(int j = 0; j < 24; j++){
                    data[posReal] = arraypixeles[i+j];
                    //System.out.println(data[posReal]);
                    posReal++;
                    
                }
            }
        }
        
         
	
	public int []getData()
	{
		return data;
	}
	
	public int getTipo()
	{
		return tipo;
	}
	
	public void setTipo(int newtipo)
	{
		tipo = newtipo;
	}
        
        public int getValor(int index){
            return data[index];
        }
        
        public void CrearCara(ArrayList<Integer> posicionCaras, File ruta){
            int mask = 0x000000FF;
            int cont;
            BufferedImage bimage;
            Color myWhite = new Color(255, 255, 255); // Color white
            int rgb = myWhite.getRGB();
            try
            {
                bimage = null;
                    bimage = ImageIO.read(ruta);
                    data = bimage.getRGB(0, 0, bimage.getWidth(), bimage.getHeight(), null, 0, bimage.getWidth());
                    //Asumiendo que la imagen ya está en escala de grises pero en formato color, 
                    //convertimos ARGB en un único valor
                    for(cont=0;cont<data.length;cont++){
                        data[cont] = data[cont] & mask;
                        //System.out.println(data[cont]);
                    }
                    
                    System.out.println(bimage.getHeight());
                    System.out.println( bimage.getWidth());
                    
                    for(int k = 0; k < posicionCaras.size(); k++ ){
                        for (int j = posicionCaras.get(k) / bimage.getWidth(); j < 24 + (posicionCaras.get(k) / bimage.getWidth()); j++){
                            for (int i = posicionCaras.get(k) % bimage.getWidth(); i < posicionCaras.get(k) % bimage.getWidth() + 24; i++){
                                if(i < bimage.getWidth() && j < bimage.getHeight()){
                                    bimage.setRGB(i, j, rgb);
                                }
                            }
                        }   

                    }
                   
                    File outputfile = new File("saved.png");
		    ImageIO.write(bimage, "gif", outputfile);

            } catch (IOException e)
            {
                    System.out.println(e.getMessage());
            }       
            
            
            

            
        }
        
        
           
}
