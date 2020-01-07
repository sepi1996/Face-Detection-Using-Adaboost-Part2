# Face-Detection-Using-Adaboost-Part2
This project will be capable of distinguishing between two types of input images. For this, I will use a system of supervised machine learning.
## Introducción
En esta parte segunda parte vamos a utilizar el algoritmo de AdaBoost para implementar un programa capaz de reconocer caras en una imagen.
Para ello dispondremos del algoritmo diseñado en la práctica obligatoria a la cual le añadiremos algunos módulos más para que sea capaz de cumplir esta función.
Lo que haremos para ello será dividir está imagen más grande, en sub imágenes del tamaño utilizado para el aprendizaje, en este caso de 24x24 e ir analizando sub imagen por sub imagen
si se trata de una cara. En el caso de que efectivamente hayamos encontrado una cara, pintaremos esta sub imagen de blanco.

## Funcionamiento teorico
Para comprobar el funcionamiento de este programa, primero probaremos con imagenes del propio dataset de aprendizaje, que le serán más fáciles de detectar
Para ello le pasaremos una imagen cuyas caras tienen como tamaño 24x24. Para obtener esta imágene lo que he hecho ha sido juntar varias imágenes de las que teníamos en la base de
datos de prueba. Por tanto, el programa recibe una imagen como la siguiente.
![alt text](https://user-images.githubusercontent.com/18005114/71885767-8c45e600-312a-11ea-918b-be273c77af84.png)

Dando com resultado el siguiente:

![alt text](https://user-images.githubusercontent.com/18005114/71885840-b5ff0d00-312a-11ea-99e7-aa2c2916a7ce.png)

## Funcionamiento práctico
Ahora vamos a ver su funcionamiento con imágenes de la propia base de datos de aprendizaje, probando con imágenes completamente ajenas a nuestro programa.

![alt text](https://user-images.githubusercontent.com/18005114/71886249-666d1100-312b-11ea-9c0c-602397a07f5f.png)

Y el resultado obtenido:
![alt text](https://user-images.githubusercontent.com/18005114/71886309-7f75c200-312b-11ea-8f4b-a9c018d8a357.png)
