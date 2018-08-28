import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by luisricardo on 17/08/2018.
 */
public class Search
{
    //Atributos
    TextFile archivo;
    LinkedList<String> textoDescompuesto;
    int id; //Indice del archivo en el InvertedIndex/IncidentList

    //IncidentList de  Palabras para este archivo.
    //IncidentList inlistBuscadas;
    //IncidentList inlistDescartar;

    //Constructor
    public Search(TextFile file, int num)
    {
        id = num;
        archivo = file;

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
        System.out.println("\nBusqueda de palabras en archivo :"+archivo.archivo.getName());
        //System.out.println("N° total parrafos: "+numeroLineas);
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
    //Metodo que considerando todas las palabras del texto ya tokenizadas de este archivo añade las nuevas palabras al
    //inverted index; si ya estan creadas solo añade en la linked List el Id del archivo que tabien contiene esa palabra
    //pero No crea una nueva entrada
    public void addPalabrasInvertedIndex(InvertedIndex invertedIndx)
    {
        //Numero total de palabras a buscar que fueron ingresadas por el query del usurario
        int totalWordsQuery;
        int totalWordsText;

        //
        String actualWordText;
        String actualWordCopy;
        char actualCharText;
        char actualCharCopy;
        boolean palabrasSonIguales;

        String palabraActualIndex;
        LinkedList<String> palabras;

        //2)TOKENIZE TEXT & 3)LINGUISTIC PREPROCESSING
        textoDescompuesto = tokenizeText();

        //-------------------------------------
        listarTokenizedText(textoDescompuesto);
        //-------------------------------------

        //LinkedList con las mismas palabras de tod0o el texto para crear el InvertedIndex
        palabras = new LinkedList<String>();
        palabras.addAll(textoDescompuesto);

        totalWordsText = textoDescompuesto.size();
        palabrasSonIguales = true;

        //Variable que guarda -1 si todavia no hay una palabra en el InvertedIndex / Si si ya hay una palanra en el
        //inverted index regresa la posicion donde esta
        int posInvertedIndx;

        //4) INDEX THE DOCUMENTS FOR EACH TERM/WORD THAT MATCHES IN THIS TEXT:
        //  Ir palabra por palabra del texto descompuesto comparando con cada una de las palabras del arreglo PALABRAS
        //      (QUE SOLO ES UN ARREGO DIFERENTE PERO QUE CONTIENE TOODAS LAS PALABRAS DEL TEXTO COMPLETO )
        //
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

        //Para cada Palabra del texto
        for(int i = 0; i < totalWordsText; i++)
        {
            actualWordText = textoDescompuesto.get(i);

            //Para cada palabra de la copia del texto original
            for(int j = 0; j < palabras.size(); j++)
            {
                //Para cada palabra de la copia del texto original convertirla a lowercase
                actualWordCopy = palabras.get(j).toLowerCase();

                //----------
                //System.out.println("\n"+i+")----Palabra actual de la copia: "+actualWordCopy+"(indx:"+j+") ----");
                //System.out.println("Total de palabras restatnes en lista Copia:"+palabras.size());
                //----------

               //Si las palabras no tienen el mismo numero de caracteres no pueden ser iguales
                if(actualWordText.length() == actualWordCopy.length())
                {
                    //-------
                    //System.out.println("Palabra del texto copia SI tiene el mismo nuemro de caracteres que palabra N° del texto orig:"+i);
                    //-------

                   //Verificar caracter por caracter que sean iguales
                   for(int k = 0; k < actualWordText.length(); k++)
                   {
                       actualCharCopy = actualWordCopy.charAt(k);
                       actualCharText = actualWordText.charAt(k);

                       //Los caracteres en la posicion K de ambas palabras NO son
                       //iguales; por lo tanto las palabras no son iguales;
                       //y automaticamente se mueve a la siguiente palabra del query
                       if( ( (int)actualCharCopy ) != ((int) actualCharText ))
                       {
                           //------------------
                           //System.out.println("Palabra del texto copia no es igual a la palabra del archivo por el caracter" +
                           //        " en posición N°:"+k);
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
                       //System.out.println("Palabra del texto copia SI es igual a la palabra del archivo en posición N°:"+i);
                       //------------------

                       //Verificar si ya se habia agregado en el inverted idex una entrada para esa palabra.
                       //Si el indice es mayor a -1 entonces se esta regresando la posicion donde ya hay una entrada
                       //dentro del InvertedIndex
                       posInvertedIndx = invertedIndx.contienePalabra(actualWordText);

                       if( posInvertedIndx != -1)
                       {
                           //--------------
                           //System.out.println("Ya hay una entrada en el diccionario para esa palabra!");
                           //--------------
                           //Ya hay una entrada en el diccionario para esa palabra

                           //1)Marcar que para este documento si se encontro la palabra
                           //-------------------------------------------------
                           //System.out.println("Lista N°:"+posInvertedIndx);
                           //-------------------------------------------------
                           invertedIndx.lista.get(posInvertedIndx).agregarCoincidencia(id);

                           //2)Eliminar de la lista de palabras de la copia del texto original la palabra que se acaba
                           // de encontrar para optimizar proximas busquedas en lo que quede del archivo
                           //----------
                           //System.out.println("Total palabras texto copia:"+palabras.size()+" remover todas las palabras:"+actualWordText);
                           //----------
                           removeAllEqualTo(actualWordText,palabras);

                       }//Fin if 4
                       else
                       {
                           //--------------
                           //System.out.println("TODAVIA NO hay una entrada en el diccionario!");
                           //--------------
                           //No hay ninguna entrada en el diccionario para esa palabra

                           //1)Crear entrada en el indice para esta palabra
                           invertedIndx.lista.add(new IncidentList(actualWordText));

                           //2)Aumentar en uno el numero de palabras actuales en el inverted Index
                           invertedIndx.numeroActualPalabras = invertedIndx.numeroActualPalabras+1;

                           //3)Marcar que para este documento si se encontro la palabra
                           //Recordad que add hace append del nuevo elemento al final de la lista; por lo tanto
                           //hacer el marcado en esa posicion
                           invertedIndx.lista.getLast().agregarCoincidencia(id);

                           //4)Eliminar de la lista de palabras del texto de copia la palabra que se acaba de encontrar
                           //para optimizar proximas busquedas en lo que quede del archivo
                           //----------
                           //System.out.println("Total palabras texto copia:"+palabras.size()+" remover todas las palabras::"+actualWordText);
                           //----------
                           removeAllEqualTo(actualWordText,palabras);

                           //5)Fuerza salida del for 4 que continue iterando sobre cada palabra del invertedIndx
                           // y evitar que elimine automaticamente todas las palabras del query del user!!!!IMPORTANTE
                           //l = invertedIndx.numeroActualPalabras;

                       }//Fin else 4

                       ////////
                       //SI YA NO HAY MAS PALABRAS EN QUERY ENTONCES FINALIZAR DE BUSCAR EN EL ARCHIVO
                       if(palabras.size() == 0)
                       {
                           //--------------------------------------------------
                           //System.out.println("Se han encontrado todas las palabras del archivo copia por lo tanto; ya no tiene" +
                           //        " sentido continuar con analisis de archivo");
                           //-------------------------------------------------

                           //l = invertedIndx.numeroActualPalabras; //Fuerza salida del for 4 que itera sobre cada palabra del invertedIndx
                           i = totalWordsText; //Fuerza la salida del ciclo de For 1 que itera sobre cada palabra del archivo

                       }//Fin if 6(4.2)

                       //CASO ESPECIAL PRIMER PALABRA DEL INVERTED INDEX
                       //Si El numero actual de palabras en el invertedIndex es 0 (CASO INICIAL) entonces automaticamente
                       //Hacer mismo pasos de cuando todavia no hay ninguna estrada en el diccionario. Es un caso especial
                       //ya que como numero actual de palabras empieza en 0 ENTONCES PROGRAMA NO ENTRA AL  FOR #4
                       if(invertedIndx.numeroActualPalabras == 0)
                       {
                           //--------------
                           //System.out.println("PRIMERA entrada en el diccionario!! estaba vacio");
                           //--------------
                           //No hay ninguna entrada en el diccionario para esa palabra

                           //1)Crear entrada en el indice para esta palabra
                           invertedIndx.lista.add(new IncidentList(actualWordText));

                           //2)Aumentar en uno el numero de palabras actuales en el inverted Index
                           invertedIndx.numeroActualPalabras = invertedIndx.numeroActualPalabras+1;

                           //3)Marcar que para este documento si se encontro la palabra
                           //Recordad que add hace append del nuevo elemento al final de la lista; por lo tanto
                           //hacer el marcado en esa posicion
                           invertedIndx.lista.getLast().agregarCoincidencia(id);

                           //4)Eliminar de la lista de palabras de querys la palabra que se acaba de encontrar
                           //para optimizar proximas busquedas en lo que quede del archivo
                           //----------
                           //System.out.println("Total palabras texto copia:"+palabras.size()+" remover todas las palabras:::"+actualWordText);
                           //----------
                           removeAllEqualTo(actualWordText,palabras);

                       }//Fin if 5

                   }//Fin if 3

                }//Fin if 1

                //RESETEAR A TRUE QUE LAS PALABRAS SON IGUALES PARA FUTURAS ITERACIONES SOBRE EL TEXTO
                palabrasSonIguales = true;

            }//Fin for 2

        }//Fin for 1

    }//Fin meotdo buscarPalabras

    //------------------------------------------------------------------------------------------------------------------
    //Remover de la linked list todas las palabras de la linked lsit que sean iguales a la palabra que se meta como primer
    //parametro
    public void removeAllEqualTo(String word, LinkedList<String> lista)
    {
        for(int i = 0; i < lista.size(); i++)
        {
            //----------------------
            //System.out.println(lista.get(i)+"=="+word+":");
            //-----------------------
            if(lista.get(i).equals(word))
            {
                lista.remove(i);

                //Si se hizo una eliminación los elementos se recorren una posicion a la izquierda; por lo tanto para
                //evitar saltar un elemento disminuir en uno el indice cada que se elimina un elemento
                i--;

            }//FIn if 1

        }//Fin for 1

    }//Fin metodo

}//Fin clase search
