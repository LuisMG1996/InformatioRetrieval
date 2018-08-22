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
    int indx; //Indice del archivo en el InvertedIndex/IncidentList

    //IncidentList de  Palabras para este archivo.
    //IncidentList inlistBuscadas;
    //IncidentList inlistDescartar;

    //Constructor
    public Search(TextFile file, LinkedList<String> allWords, int id)
    {
        indx = id;
        archivo = file;
        palabras = allWords;

    }//Fin constructor

    //Metodos

    public LinkedList<String> tokenizeText()
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
            stoken = new StringTokenizer(lineaActual);

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


        return textTokenized;
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
        int totalWordsQuery;
        int totalWordsText;

        //
        String actualWordText;
        String actualWordQuery;
        char actualCharText;
        char actualCharQuery;
        boolean palabrasSonIguales;

        String palabraActualIndex;

        //2)TOKENIZE TEXT & 3)LINGUISTIC PREPROCESSING
        textoDescompuesto = tokenizeText();
        //-------------------------------------
        listarTokenizedText(textoDescompuesto);
        //-------------------------------------

        //totalWordsQuery = palabras.size();
        totalWordsText = textoDescompuesto.size();
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
        for(int i = 0; i < totalWordsText; i++)
        {
            actualWordText = textoDescompuesto.get(i);

            //Para cada palabra del query
            for(int j = 0; j < palabras.size(); j++)
            {
                //Para cada palabra del query del usuario convertirla a lowercase
                actualWordQuery = palabras.get(j).toLowerCase();

                //----------
                System.out.println("\n"+i+")----Palabra de quey actual: "+actualWordQuery+"(indx:"+j+") ----");
                System.out.println("Total de palabras restatnes en query:"+palabras.size());
                //----------

               //Si las palabras no tienen el mismo numero de caracteres no pueden ser iguales
                if(actualWordText.length() == actualWordQuery.length())
                {
                    //-------
                    System.out.println("Palabra de query SI tiene el mismo nuemro de caracteres que palabra N°:"+i);
                    //-------

                   //Verificar caracter por caracter que sean iguales
                   for(int k = 0; k < actualWordText.length(); k++)
                   {
                       actualCharQuery = actualWordQuery.charAt(k);
                       actualCharText = actualWordText.charAt(k);

                       //Los caracteres en la posicion K de ambas palabras NO son
                       //iguales; por lo tanto las palabras no son iguales;
                       //y automaticamente se mueve a la siguiente palabra del query
                       if( ( (int)actualCharQuery ) != ((int) actualCharText ))
                       {
                           //------------------
                           System.out.println("Palabra del query no es igual a la palabra del archivo por el caracter" +
                                   " en posición N°:"+k);
                           //------------------
                           //Palabras no son iguales porque alguno de los caracteres no coincido
                           palabrasSonIguales = false;
                           k = actualWordText.length();//Modificar k para forzar salida del loop actual

                       }//Fin if  2

                   }//Fin for 3

                   //SI Las palabras si fueron iguales porque todos sus caracteres son iguales
                   if(palabrasSonIguales)
                   {
                       //------------------
                       System.out.println("Palabra del query SI es igual a la palabra del archivo en posición N°:"+i);
                       //------------------

                       //Verificar si ya hay una entrada en el diccionario del InvertedIndx para esa palabra
                       for(int l = 0; l < invertedIndx.numeroActualPalabras; l++)
                       {
                           //Obtener la palabra actual del index
                           palabraActualIndex = invertedIndx.lista.get(l).wordOfDictionary;

                           //Verificar si ya se habia agregado en el inverted idex una entrada para esa palabra
                           if(palabraActualIndex.equals(actualWordText))
                           {
                               //--------------
                               System.out.println("Ya hay una entrada en el diccionario!");
                               //--------------
                               //Ya hay una entrada en el diccionario para esa palabra

                               //1)Marcar que para este documento si se encontro la palabra
                               invertedIndx.lista.get(l).agregarCoincidencia(indx);

                               //2)Eliminar de la lista de palabras de querys la palabra que se acaba de encontrar
                               //para optimizar proximas busquedas en lo que quede del archivo
                               //----------
                               System.out.println("Total palabras query:"+palabras.size()+" palabra a remover de palabras en posicion:"+j);
                               //----------
                               palabras.remove(j);

                               //3)Fuerza salida del for 4 que itera sobre cada palabra del invertedIndx
                               // y evitar que elimine automaticamente todas las palabras del query del user!!!!IMPORTANTE
                               l = invertedIndx.numeroActualPalabras;

                           }//Fin if 4
                           else
                           {
                               //--------------
                               System.out.println("TODAVIA NO hay una entrada en el diccionario!");
                               //--------------
                               //No hay ninguna entrada en el diccionario para esa palabra

                               //1)Crear entrada en el indice para esta palabra
                               invertedIndx.lista.add(new IncidentList(actualWordText));

                               //2)Aumentar en uno el numero de palabras actuales en el inverted Index
                               invertedIndx.numeroActualPalabras = invertedIndx.numeroActualPalabras+1;

                               //3)Marcar que para este documento si se encontro la palabra
                               //Recordad que add hace append del nuevo elemento al final de la lista; por lo tanto
                               //hacer el marcado en esa posicion
                               invertedIndx.lista.getLast().agregarCoincidencia(indx);

                               //4)Eliminar de la lista de palabras de querys la palabra que se acaba de encontrar
                               //para optimizar proximas busquedas en lo que quede del archivo
                               //----------
                               System.out.println("Total palabras query:"+palabras.size()+" palabra a remover de palabras en posicion:"+j);
                               //----------
                               palabras.remove(j);

                               //5)Fuerza salida del for 4 que continue iterando sobre cada palabra del invertedIndx
                               // y evitar que elimine automaticamente todas las palabras del query del user!!!!IMPORTANTE
                               l = invertedIndx.numeroActualPalabras;

                           }//Fin else 4

                           ////////
                           //SI YA NO HAY MAS PALABRAS EN QUERY ENTONCES FINALIZAR DE BUSCAR EN EL ARCHIVO
                           if(palabras.size() == 0)
                           {
                               //--------------------------------------------------
                               System.out.println("Se han encontrado todas las palabras del query por lo tanto; ya no tiene" +
                                       " sentido continuar con analisis de archivo");
                               //-------------------------------------------------

                               l = invertedIndx.numeroActualPalabras; //Fuerza salida del for 4 que itera sobre cada palabra del invertedIndx
                               i = totalWordsText; //Fuerza la salida del ciclo de For 1 que itera sobre cada palabra del archivo

                           }//Fin if 6(4.2)

                       }//Fin for 4

                       //CASO ESPECIAL PRIMER PALABRA DEL INVERTED INDEX
                       //Si El numero actual de palabras en el invertedIndex es 0 (CASO INICIAL) entonces automaticamente
                       //Hacer mismo pasos de cuando todavia no hay ninguna estrada en el diccionario. Es un caso especial
                       //ya que como numero actual de palabras empieza en 0 ENTONCES PROGRAMA NO ENTRA AL  FOR #4
                       if(invertedIndx.numeroActualPalabras == 0)
                       {
                           //--------------
                           System.out.println("PRIMERA entrada en el diccionario!! estaba vacio");
                           //--------------
                           //No hay ninguna entrada en el diccionario para esa palabra

                           //1)Crear entrada en el indice para esta palabra
                           invertedIndx.lista.add(new IncidentList(actualWordText));

                           //2)Aumentar en uno el numero de palabras actuales en el inverted Index
                           invertedIndx.numeroActualPalabras = invertedIndx.numeroActualPalabras+1;

                           //3)Marcar que para este documento si se encontro la palabra
                           //Recordad que add hace append del nuevo elemento al final de la lista; por lo tanto
                           //hacer el marcado en esa posicion
                           invertedIndx.lista.getLast().agregarCoincidencia(indx);

                           //4)Eliminar de la lista de palabras de querys la palabra que se acaba de encontrar
                           //para optimizar proximas busquedas en lo que quede del archivo
                           //----------
                           System.out.println("Total palabras query:"+palabras.size()+" palabra a remover de palabras en posicion:"+j);
                           //----------
                           palabras.remove(j);

                       }//Fin if 5

                       ///

                   }//Fin if 3

                }//Fin if 1

                //PALABRAS NO SON IGUALES PORQUE NO TIENEN EL MISMO NUMERO DE CARACTERES
                //palabrasSonIguales = false;

                //RESETEAR A TRUE QUE LAS PALABRAS SON IGUALES PARA FUTURAS ITERACIONES SOBRE EL TEXTO
                palabrasSonIguales = true;

            }//Fin for 2

        }//Fin for 1

    }//Fin meotdo buscarPalabras



}//Fin clase search
