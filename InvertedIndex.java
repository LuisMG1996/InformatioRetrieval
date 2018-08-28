import java.util.LinkedList;

/**
 * Luis Ricardo Montes Gómez 153788
 * José Francisco Zerón Cabrea 154678
 */

public class InvertedIndex
{
    //Atributos
    //Numero Actual DE Palabras e incident lists en el InvertedIndex; recordando que por cada palabra hay una incidentList
    int numeroActualPalabras;
    LinkedList<IncidentList> lista;

    //Constructor
    public InvertedIndex()
    {
        numeroActualPalabras = 0;
        lista = new LinkedList<IncidentList>();

    }//Fin cosntructor InvertedIndex

    //Meotodos

    //Metodo que despliega cada palabra en el index y su respectiva incidentlist
    public void listarInvertedIndex()
    {
        System.out.println("\n\tINVERTED INDEX");
        System.out.println("Term \tIncident List");

        for(int i = 0; i < numeroActualPalabras; i++)
        {
            //Palabra
            System.out.print(i+")"+lista.get(i).wordOfDictionary +" :" );

            //Incident list de la palabra correspodniente
            for(int k = 0; k < lista.get(i).size(); k++)
            {
                System.out.print("\t"+lista.get(i).get(k)+" | ");

            }//Fin for 2

            //Doble salto de linea para nueva palabra
            System.out.println("\n");

        }//Fin for 1

    }//Fin metodo listarInvertedIndex
    //-------------------------------------------------------------------------------------------
    int contienePalabra(String word)
    {
        int estaContenida = -1;

        for(int i = 0; i < lista.size(); i++)
        {
            if(lista.get(i).wordOfDictionary.equals(word))
            {
                estaContenida = i;
                i = lista.size();
            }//Fin if 1

        }//Fin for 1

        return estaContenida;
    }//Fin metodo contienePalabra
    //------------------------------------------------------------------------
    //Metodo que ordena el diccionario en orden alfabetico en orden ascendente
    public void sortInvertedIndex()
    {
        IncidentList tempIL;
        IncidentList actualLowIL;
        IncidentList nextIL;
        int indxactualLowestIL;

        //Indica el numero actual  de pasadas que se van a dar
        for(int i  = 0; i < numeroActualPalabras; i++)
        {
            actualLowIL = lista.get(i);
            indxactualLowestIL =  i;

            //Comparar el objeto actual con el resto de los elementos del arreglo para encontrar el menor sin considerar
            //a los elementos anteriores que ya fueron ordenados
            for(int j = i+1; j < numeroActualPalabras; j++)
            {
                nextIL = lista.get(j);

                //Si nextIL.word es lexograficamente menor que actualLowIL
                //---------------------------
                //System.out.println(nextIL.wordOfDictionary+"=="+actualLowIL.wordOfDictionary+":"+nextIL.wordOfDictionary.compareTo(actualLowIL.wordOfDictionary));
                //---------------------------
                if(nextIL.wordOfDictionary.compareTo(actualLowIL.wordOfDictionary) < 0)
                {
                    //NUEVA PALABRA MAS BAJA encontrada

                    //Guardar su indice
                    indxactualLowestIL = j;
                    //Guardar la nueva palabra mas baja
                    actualLowIL = lista.get(indxactualLowestIL);

                    //---------------
                    //System.out.println("Nueva palabra mas pequeña:"+actualLowIL.wordOfDictionary);
                    //---------------

                }//Fin if 1

            }//Fin for 2

            //-------
            //System.out.println("i:"+i);
            //System.out.println("index elemento mas pequeño:"+indxactualLowestIL);
            //System.out.println("Palabra alfabeticamente mas pequeña:"+actualLowIL.wordOfDictionary);
            //System.out.println("Tempil:"+lista.get(i).wordOfDictionary);
            //-------

            //Hacer el swap solo si la nueva palabra Mas pequeña encontrada

            //1)Guardar contenido posicion actual en variable temporal
            tempIL = lista.get(i);
            //2)Remplazar al elemento mas bajo en la posicion actual
            lista.set(i,actualLowIL);
            //3)El contenido que estaba en la posicion donde se acaba poner el elemento mas pequeño
            //pasarla a la posicion que ahora esta disponible ya que el objeto pequeño tiene una nueva posicion
            //SI ESTE PASO NO SE HACE EL NUEVO ELEMENTO MAS PEQUEÑO QUEDARA DUPLICADO Y SE PERDERA EL ELEMENTO QUE
            //ESTABA EN LA POSICION i
            lista.set(indxactualLowestIL,tempIL);

            //----------
            //listarInvertedIndex();
            //---------

        }//Fin for 1


    }//Fin metodo sortInvertedIndex
    //------------------------------------------------------------------------
    //Metodo que dadas las palabras del query imprime que documentos cumplen con las palabras buscadas por el usuario
    public void doQuery(LinkedList<String> andWords, LinkedList<String> notWords, LinkedList<Search> docs)
    {
        int invertedMatrix[][];
        LinkedList<Integer> indxAndWords;
        LinkedList<Integer> indxNotWords;

        LinkedList<Integer> resultadoQuery;
        int resultadoColumna;

        int totalPalabras;
        int numDocsPalabraActual;
        int indxDoc;
        //Variable con un indice global para recorrer y asignar elementos de la inveredMatrix que guarda la posicion
        //de donde se quedo la matrix al acabar de procesar las palabras AND y para continuar desde esa posicion para
        //las palabras NOT
        int globalIndx;
        int numDocs;

        //Bandera que indica si hay palabras and en elquery que nisiquiera estan en el InvertedIndex
        boolean palabrasAndNotInIndex = true;

        indxAndWords = new LinkedList<Integer>();
        //Total Palabras
        totalPalabras = andWords.size()+notWords.size();
        globalIndx = 0;
        numDocs = docs.size();


        //1)Determinar los indices de las palabras AND y agregarlas a una lista

        //Para cada palabra en el indice
        for(int i = 0 ; i < numeroActualPalabras; i++)
        {
            //Para cada palabra en la lista de andWords del query
            for(int j = 0; j < andWords.size(); j++)
            {
                //Si una de las palabra en el InvertedIndex es igual a alguna de las palabras de la lista de andWords
                //,convertida a minusculas, guardar el indice del InvertedIndex en lalista de indxAndWords
                if( lista.get(i).wordOfDictionary.equals( andWords.get(j).toLowerCase() ) )
                {
                    indxAndWords.add(i);

                }//Fin if 1

            }//Fin for 2

        }//Fin for 1

        //2)Determinar los indices de las palabras NOT y agregarlas a una lista

        indxNotWords = new LinkedList<Integer>();

        //Para cada palabra en el indice
        for(int i = 0; i < numeroActualPalabras; i++)
        {
            //Para cada palabra en la lista de notWords del query
            for(int j = 0; j < notWords.size(); j++)
            {
                //Si una de las palabra en el InvertedIndex es igual a alguna de las palabras de la lista de orWords
                //,convertida a minusculas, guardar el indice del InvertedIndex en lalista de indxNotWords
                //---------------
                //System.out.println(lista.get(i).wordOfDictionary+"=="+notWords.get(j).toLowerCase());
                //-----------
                if( lista.get(i).wordOfDictionary.equals( notWords.get(j).toLowerCase() ) )
                {
                    indxNotWords.add(i);
                }//Fin if 2

            }//Fin for 4

        }//Fin for 3

        /*---------------------------------------------
        System.out.println("------------");
        System.out.println("Mostrar indices AND words:");

        for(int i = 0; i < indxAndWords.size();i++)
        {
            System.out.println(indxAndWords.get(i));
        }//Fin for 1

        System.out.println("\nMostrar indices NOT words:");
        for(int i = 0; i < indxNotWords.size();i++)
        {
            System.out.println(indxNotWords.get(i));
        }//Fin for 1

        System.out.println("------------");

        //-------------------------------------------------*/

        invertedMatrix = new int[totalPalabras][numDocs+1];

        //Inicializar toda la primera columna (id de palabra) con -1
        for(int i = 0; i < totalPalabras; i++)
        {
            invertedMatrix[i][0] = -1;
        }//FIn for 1
        //----------------------------------------------------

        //Ver si el numero de palabras en el query del and son iguales a las palabras and encontradas en el inverted index
        if(indxAndWords.size() == andWords.size() )
        {
            //++++++ Para todas las palabras and +++++++
            for (int i = 0; i < indxAndWords.size(); i++)
            {

                //En la primer posicion de esa fila; guardar el id de la palabra
                invertedMatrix[i][0] = indxAndWords.get(i);

                //Poner 1 en la posicion que corresponde al ID del archivo si aparece el Id del archivo dentro del
                //arreglo que corresponde a la palabra indicada en la lista de indxAndWord
                numDocsPalabraActual = lista.get(indxAndWords.get(i)).size();
                for(int k = 0; k < numDocsPalabraActual; k++)
                {
                    //ObtenerElID del documento que si tiene la palabra actual; este ID sirve tambien como el indx
                    //correspondiente de la columna en la invertedMatrix
                    indxDoc = lista.get(indxAndWords.get(i)).get(k);

                    //--------------------------------------------------------------
                    //System.out.println(indxAndWords.get(i)+")Palabra: "+lista.get(indxAndWords.get(i)).wordOfDictionary);
                    //System.out.println("["+i+"]["+indxDoc+"]");
                    //---------------------------------------------------------------

                    invertedMatrix[i][indxDoc] = 1;

                }//Fin for 2

                globalIndx++;

            }//Fin for 1

        }//Fin if 1
        else
        {
            //Hay palabras del query que no estan en el InvertedIndex por lo tanto no va haber ningun documento
            //que cumpla con el query
            System.out.println("RESULTADO QUERY:\nNo hay ningun documento que satisfaga la consulta indicada!");
            palabrasAndNotInIndex = false;

        }//Fin else 1

        //+++++++++++++++++++++++++++++ Logica Para todas las palabras Not del query +++++++++++++++++++++++++++++++++++

        //Inicializar toda la fila con valor de 1 por default; menos la posicion 0 que contiene el id de la palabra
        //Esto previene que palabras No existentes en los documentos puedan resultar un query erroneo.

        for(int j = globalIndx; j < totalPalabras; j++)
        {
            for (int k = 1; k < numDocs + 1; k++)
            {
                invertedMatrix[j][k] = 1;
            }//FIn for k

        }//Fin for j

        //----------------
        //System.out.println("Numero not words del query encontradas en el invertedIndex:"+indxNotWords.size());
        //----------------
        //Para cada palabra Not del query que tambien este dentro del InvertedIndex:
        for(int i = 0; i < indxNotWords.size();i++ )
        {
            //En la primer posicion de esa fila; guardar el id de la palabra
            invertedMatrix[globalIndx][0] = indxNotWords.get(i);

            //------
            //System.out.println("gLOBAL INDEX:"+globalIndx);
            //-------

            //Poner 0 en la posicion que corresponde al ID del archivo si aparece el Id del archivo dentro del
            //arreglo que corresponde a la palabra indicada en la lista de indxNotWord
            numDocsPalabraActual = lista.get(indxNotWords.get(i)).size();
            for(int j = 0;  j < numDocsPalabraActual; j++)
            {
                //ObtenerElID del documento que si tiene la palabra actual; este ID sirve tambien como el indx
                //correspondiente de la columna en la invertedMatrix
                indxDoc = lista.get(indxNotWords.get(i)).get(j);

                //--------------------------------------------------------------
                //System.out.println(indxNotWords.get(i)+")Palabra: "+lista.get(indxNotWords.get(i)).wordOfDictionary);
                //System.out.println("["+i+"]["+indxDoc+"]");
                //---------------------------------------------------------------

                invertedMatrix[globalIndx][indxDoc] = 0;

            }//Fin for 5

            globalIndx++;

        }//Fin for 3

        //+++++++++++++++++ PROCESAMIENTO DE RESULTADO: Determinar que docs si contienen el query ++++++++++++++++++++++
        System.out.print("Resultado del query: ");
        displayQuery(andWords, notWords);

        //Verificar que todas las plabras and esten en el Index
        if(palabrasAndNotInIndex)
        {
            resultadoQuery = new LinkedList<Integer>();

            //Si al multiplicar los elementos de una misma columna se obtiene 1; el documento cuyo id corresponde a
            //la posicion de esa columna cumple con el Query; de lo contrario NO CUMPLE CON EL QUERY
            for (int i = 1; i < numDocs + 1; i++) {
                //Set resultadoColumna como 1 para cada Documento/Columna; porque si anterior documento NO CUMPLE
                //y queda en 0, si se deja en 0 todos los documentos posteriores automaticamente no cumpliran condicion
                resultadoColumna = 1;

                for (int j = 0; j < totalPalabras; j++) {
                    resultadoColumna = resultadoColumna * invertedMatrix[j][i];

                }//Fin for 2

                if (resultadoColumna == 1) {

                    //System.out.println("El documento: "+docs.get(i).archivo.archivo.getName()+" cumple con el query");
                    System.out.print("El documento N° " + i + ": ");

                    //Encontrar el nombre del archivo
                    for (int j = 0; j < docs.size(); j++) {
                        //Si el id del documento actual de la linked list docs es igual al id que esta en variable i
                        //mprimir nuombre
                        if (docs.get(j).id == i) {
                            System.out.print(docs.get(j).archivo.archivo.getName());

                        }//Fin if 3

                    }//Fin for 6
                    System.out.println(" satisface el query");

                    resultadoQuery.add(i);

                }//Fin if 2

            }//Fin for 1

            /*------ Impirmir la logica de la inverted Matrix usada para ver como se determino el resultado ----------------
            for (int i = 0; i < totalPalabras; i++) {
                for (int j = 0; j < numDocs + 1; j++) {
                    System.out.print(invertedMatrix[i][j] + " ");

                }//Fin for 2

                System.out.println();

            }//Fin for 1*/
            //----------------------------------------------------------------------------------------------------------

        }//Fin verificar que todas las palabras del and esten en el index

    }//Fin metodo doQuery
    //------------------------------------------------------------------------------------------------------------------
    //Metodo que imprime el query en forma de expresion logica
    public void displayQuery(LinkedList<String> ands, LinkedList<String> nots)
    {
        //Ver si hay ands
        if(ands.size() > 0)
        {
            //SI HAY ANDS
            System.out.print("(");

            for(int i = 0; i < ands.size(); i++)
            {
                System.out.print(ands.get(i));

                //Si no es la ultima palabra del query
                if( i != ands.size()-1)
                {
                    System.out.print("&");
                }//Fin if 1.2

            }//Fin for 1

            System.out.print(") ");

        }//Fin if 1
        else
        {
            //No hay ands

        }//Fin else 1

        //Ver si hay nots
        if(nots.size() > 0)
        {
            //SI HAY NOTS
            System.out.print("& ¬(");

            for(int i = 0; i < nots.size(); i++)
            {
                System.out.print(nots.get(i));

                //Si no es la ultima palabra del query
                if( i != nots.size()-1)
                {
                    System.out.print("&");
                }//Fin if 1.2

            }//Fin for 1

            System.out.print(") ");

        }//Fin if 2
        else
        {
            //NO HAY NOTS

        }//Fin else 2

        //Fin de linea
        System.out.println();
        System.out.println();

    }//Fin metodo display Query
    //------------------------------------------------------------------------------------------------------------------


}//Fin clase Inverted Index
