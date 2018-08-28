import java.util.LinkedList;

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
        System.out.println("Palabra \tIncident List");

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
    public void doQuery(LinkedList<String> andWords, LinkedList<String> orWords, LinkedList<String> notWords, int numDocs)
    {
        int invertedMatrix[][];
        LinkedList<Integer> indxAndWords;
        LinkedList<Integer> indxOrWords;
        LinkedList<Integer> indxNotWords;

        LinkedList<Integer> resultadoQuery;
        int resultadoColumna;

        int totalPalabras;
        int numDocsPalabraActual;
        int indxDoc;
        int globalIndx; //Variable con un indice global para recorrer y asignar elementos de la inveredNatrix


        indxAndWords = new LinkedList<Integer>();
        totalPalabras = andWords.size()+orWords.size()+notWords.size();
        globalIndx = 0;

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
            //Para cada palabra en la lista de orWords del query
            for(int j = 0; j < notWords.size(); j++)
            {
                //Si una de las palabra en el InvertedIndex es igual a alguna de las palabras de la lista de orWords
                //,convertida a minusculas, guardar el indice del InvertedIndex en lalista de indxNotWords
                if( lista.get(i).wordOfDictionary.equals( notWords.get(j).toLowerCase() ) )
                {
                    indxNotWords.add(i);
                }//Fin if 2

            }//Fin for 4

        }//Fin for 3

        //----------------------------
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

        //----------------------------

        invertedMatrix = new int[totalPalabras][numDocs+1];

        if(indxAndWords.size() == andWords.size() )
        {
            //Para todas las palabras and
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
                    System.out.println(indxAndWords.get(i)+")Palabra: "+lista.get(indxAndWords.get(i)).wordOfDictionary);
                    System.out.println("["+i+"]["+indxDoc+"]");
                    //---------------------------------------------------------------

                    invertedMatrix[i][indxDoc] = 1;

                }//Fin for 2

                globalIndx++;

            }//Fin for 1

            //Para todas las palabras Not
            for(int i = 0; i < indxNotWords.size();i++ )
            {
                //En la primer posicion de esa fila; guardar el id de la palabra
                invertedMatrix[globalIndx][0] = indxNotWords.get(i);

                //Inicializar toda la fila con valor de 1 por default; menos la posicion 0 que contiene el id de la palabra
                for(int k = 1; k < numDocs+1; k++)
                {
                    invertedMatrix[globalIndx][k] = 1;
                }//FIn for 4

                //------
                System.out.println("gLOBAL INDEX:"+globalIndx);
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
                    System.out.println(indxNotWords.get(i)+")Palabra: "+lista.get(indxNotWords.get(i)).wordOfDictionary);
                    System.out.println("["+i+"]["+indxDoc+"]");
                    //---------------------------------------------------------------

                    invertedMatrix[globalIndx][indxDoc] = 0;

                }//Fin for 5

                globalIndx++;

            }//Fin for 3

            resultadoQuery = new LinkedList<Integer>();

            //Si al multiplicar los elementos de una misma columna se obtiene 1; el documento cuyo id corresponde a
            //la posicion de esa columna cumple con el Query; de lo contrario NO CUMPLE CON EL QUERY
            for(int i = 1; i < numDocs+1; i++)
            {
                //Set resultadoColumna como 1 para cada Documento/Columna; porque si anterior documento NO CUMPLE
                //y queda en 0, si se deja en 0 todos los documentos posteriores automaticamente no cumpliran condicion
                resultadoColumna = 1;

                for (int j = 0; j < totalPalabras; j++)
                {
                    resultadoColumna = resultadoColumna*invertedMatrix[j][i];

                }//Fin for 2

                if(resultadoColumna == 1)
                {
                    System.out.println("El documento N° "+i+" cumple con el query");
                    resultadoQuery.add(i);

                }//Fin if

            }//Fin for 1

        }//Fin if 1
        else
        {
            //Hay palabras del query que no estan en el InvertedIndex por lo tanto no va haber ningun documento
            //que cumpla con el query
            System.out.println("RESULTADO QUERY:\nNo hay ningun documento que satisfaga la consulta indicada!");

        }//Fin else 1

        //------------------
        for(int i = 0; i < totalPalabras; i++ )
        {
            for(int j = 0;  j < numDocs+1; j++)
            {
                System.out.print(invertedMatrix[i][j]+" ");

            }//Fin for 2

            System.out.println();

        }//Fin for 1

        //------------------

    }//Fin metodo doQuery


}//Fin clase Inverted Index
