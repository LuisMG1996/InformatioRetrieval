import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by luisricardo on 17/08/2018.
 */
public class Search
{
    //Atributos
    TextFile archivo;
    LinkedList<String> palabras;
    LinkedList<String> textoDescompuesto;
     //Indice del archivo en el InvertedIndex/IncidentList

    //IncidentList de  Palabras para este archivo.
    //IncidentList inlistBuscadas;
    //IncidentList inlistDescartar;

    //Constructor
    public Search(TextFile file, LinkedList<String> allWords)
    {
        archivo = file;
        palabras = allWords;

    }//Fin constructor

    //Metodos

    private void tokenizeText()
    {
        int numeroLineas;
        int numeroLineaActual;
        int numeroPalabras;
        String lineaActual;
        LinkedList textTokenized;
        StringTokenizer stoken;

        textTokenized = new LinkedList<String>();
        numeroLineaActual = 0;
        numeroPalabras = 0;

        //Contar numero de lineas en archivo
        numeroLineas = archivo.ContarLineas();
        //---------------------------------
        System.out.println(""+numeroLineas);
        //----------------------------------

        //Abrir Archivo antes de empezar la lectura de este
        archivo.Abrir();

        while (numeroLineaActual < numeroLineas)
        {
            //Leer linea actual archivo
            lineaActual = archivo.LeerString();
            //Con base en la linea actual crear objeto de tipo stringTokenizer
            stoken = new StringTokenizer(lineaActual.toLowerCase());

            //Para cada linea, mientras haya mas palabras dentro de la linea
            while (stoken.hasMoreTokens())
            {
                //Tokenize/separar cada palabra de la linea y guardarla dentro de la linked lized: textTokenized
                //Ademas de procesarla para guardarla como lowercase(minuscula)!!
                textTokenized.add(stoken.nextToken().toLowerCase());
            }//Fin while 2

            //Moverse a siguiente linea del texto
            numeroLineaActual++;

        }//Fin while 1

        
        textoDescompuesto = textTokenized;
        
    }//Fin metodo tokenized Text
    //------------------------------------------------------------------------------------------------------------------
    //Metodo que imprime contenido de una linked list
    public void listarTokenizedText(LinkedList<String> lista)
    {
        int totalPalbras = lista.size();
        int palabraActual = 0;

        while (palabraActual < totalPalbras)
        {
            System.out.println(palabraActual+")"+lista.get(palabraActual));
            palabraActual++;
        }//Fin while

    }//Fin metodo listarTokenizedText
    //------------------------------------------------------------------------------------------------------------------
    //Metodo que considerando las palabras del texto ya tokenizadas de este archivo guardadas en atributo texto descompuesto
    //las compara con las palabras contenidas en las tres LinkedList (palabrasABuscar/...AEvitar/...Opcionales).
    //1)La comparación se hace cáracter por cáracter en caso de que algun caracter no coinicda entonces se descarta palabra
    // y se continua con la siguiente;
    //
    //2)En caso de coincidencia agregar marcador a IncidentList correspondiente en el field correspondiente al archivo
    //del que se este tratando este archivo
    
    public void buscarPalabras(InvertedIndex invertedIndx)
    {
        //Numero total de palabras a buscar que fueron ingresadas por el query del usurario
        int index;

        //

        boolean palabrasSonIguales;

        String palabraActualIndex;

        //2)TOKENIZE TEXT & 3)LINGUISTIC PREPROCESSING
        tokenizeText();

        //-------------------------------------
        listarTokenizedText(textoDescompuesto);
        //-------------------------------------


        palabrasSonIguales = true;

        //4) INDEX THE DOCUMENTS FOR EACH TERM/WORD THAT MATCHES IN THIS TEXT:
        //  Ir palabra por palabra del texto descompuesto comparando con cada una de las palabras buscadas
        //      La comparación se hace cáracter por carácter en caso de que un cáracter no coincida entonces
        //      deja la palabra actual del quey y se mueve a la siguiente de las palabras buscadas en el query;
        //      en caso de no encontrar ninguna coincidencia se mueve a la siguiente del texto y vuelve a comparar con
        //      todas las palabras buscadas en el quey.
        //
        //      En caso de encontrar una coincidencia y que la palabra NO TENGA una entrada toavia para la incidentList ;
        //      crear una nueva entrada y marcar con 1 que ha sido encontrada en inverted list en la posicion correspondiente
        //      al indx atributo
        //
        //      En caso de encontrar una coincidencia y que la palabra SI TENGA una entrada; solo marcar con 1 en la posicion
        //      correspodniente con respecto al atributo indx en la incident list, lo que significa que ha sido encontrada
        //      esa palabra en este archivo

        //------------
        System.out.println("Busqueda de palabras en archivo:"+archivo.archivo.getName());
        //------------

        //Para cada Palabra del texto
        for(String wordText : textoDescompuesto) {
            

            //Para cada palabra del query
            for(String wordQuery : palabras)
            {
                //Para cada palabra del query del usuario convertirla a lowercase
                

                //----------
                //System.out.println("\n"+i+")----Palabra de quey actual: "+wordQuery);
                //System.out.println("Total de palabras restatnes en query:"+palabras.size());
                //----------

               //Si las palabras no tienen el mismo numero de caracteres no pueden ser iguales
                if(wordText.length() == wordQuery.length()) {
                    //-------
                    //System.out.println("Palabra de query SI tiene el mismo nuemro de caracteres que palabra N°:"+i);
                    //-------

                   //Verificar caracter por caracter que sean iguales
                   for(int k = 0; k < wordText.length(); k++) {
                       //Los caracteres en la posicion K de ambas palabras NO son
                       //iguales; por lo tanto las palabras no son iguales;
                       //y automaticamente se mueve a la siguiente palabra del query
                       if(wordText.charAt(k) != wordQuery.charAt(k)) {
                           //------------------
                           System.out.println("Palabra del query no es igual a la palabra del archivo por el caracter" +
                                   " en posición N°:"+k);
                           //------------------
                           //Palabras no son iguales porque alguno de los caracteres no coincido
                           palabrasSonIguales = false;
                           break;//Modificar k para forzar salida del loop actual
                       }//Fin if  2
                   }//Fin for 3

                   //SI Las palabras si fueron iguales porque todos sus caracteres son iguales
                   if(palabrasSonIguales) {
                       //------------------
                       //System.out.println("Palabra del query SI es igual a la palabra del archivo en posición N°:"+i);
                       //------------------

                       index = invertedIndx.getIndex(wordText);
                       invertedIndx.get(index).agregarCoincidencia(archivo.id);

                   }//Fin if 3

                }//Fin if 1

                //PALABRAS NO SON IGUALES PORQUE NO TIENEN EL MISMO NUMERO DE CARACTERES
                //palabrasSonIguales = false;

                //RESETEAR A TRUE QUE LAS PALABRAS SON IGUALES PARA FUTURAS ITERACIONES SOBRE EL TEXTO
                palabrasSonIguales = true;

            }//Fin for 2

        }//Fin for 1

    }//Fin meotdo buscarPalabras

    //Metodo para agregar cada una de las palabaras tokenizadas al inverted index
    private void addInvertedIndex(InvertedIndex invertedIndex){
        //For que recorre cada palabra de la lista tokenizada para operar con ella
        for(String word:textoDescompuesto){
            //Si el inverted index no contiene la palabra
            if (!invertedIndex.lista.contains(word)){
                //Creación de una lista de incidente para la palabra y adición
                invertedIndex.add(new IncidentList(word));
                //Se agrega la palabra a una lista de Strings para facilitar la comparación
                invertedIndex.lista.add(word);
            }
            //Si no, se envía un msj al usuario
            else {
                System.out.println("La palabra " + word + " ya existe en el diccionario.");
            }
        }
    }



}//Fin clase search
