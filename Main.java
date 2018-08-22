import java.util.LinkedList;
import java.util.Scanner;

public class Main
{

    //Inicio del programa
    public static void main(String[] args)
    {
        //---Declaracion de vairables
        LinkedList<String> palabrasAIncluir;
        LinkedList<String> palabrasAEvitar;
        LinkedList<String> palabrasOpcionales;

        LinkedList<String> totalPalabras;
        int palabrasABuscar;

        //InvertedIndex = Lista de IncidentLists y palabras
        InvertedIndex invtIndx;

        //Archivo uno
        TextFile archivoUno;
        Search buscadorArchivoUno;

        //Archivo Dos
        TextFile archivoDos;
        Search buscadorArchivoDos;

        //---Datos de entrada
        palabrasAIncluir = new LinkedList<String>();
        palabrasAEvitar = new LinkedList<String>();
        palabrasOpcionales = new LinkedList<String>();

        totalPalabras = new LinkedList<String>();
        palabrasABuscar = 0;

        menu(palabrasAIncluir,palabrasAEvitar,palabrasOpcionales);

        //---Proceso

        //Si el usuario no incluye ninguna palabra que obligatoriamente tenga que estar o que NO tenga que aparecer
        //indicar que debe incluir al menos una palabra en su busqueda
        palabrasABuscar = palabrasAIncluir.size() + palabrasAEvitar.size();

        if(palabrasABuscar > 0)
        {
            //Considerar las palabras del or también
            palabrasABuscar = palabrasABuscar + palabrasOpcionales.size();

            //Inicializar invertedIndex!!!
            invtIndx = new InvertedIndex();

            //Agregar todas las palabras del query de su respectiva LinkedList en una sola linkedList
            totalPalabras.addAll(palabrasAIncluir);
            totalPalabras.addAll(palabrasAEvitar);
            totalPalabras.addAll(palabrasOpcionales);

            //1)COLLECT THE DOCUMENTS TO BE INDEXED
            archivoUno = new TextFile("archivoUno.txt",System.getProperty("user.dir"));
            buscadorArchivoUno = new Search(archivoUno, totalPalabras,0);

            archivoDos = new TextFile("archivoDos.txt",System.getProperty("user.dir"));
            buscadorArchivoDos = new Search(archivoDos,totalPalabras,1);

            //2)Tokenize the text
            //3)Linguistic Preprocessing
            //4)Crear un diccionario y una Incident List por cada palabra ingresada en el query que se enceuntre en
            //al menos alguno de los archivos
            buscadorArchivoUno.buscarPalabras(invtIndx);

            //---Datos de sailda
            invtIndx.listarInvertedIndex();

        }//Fin if 1
        else
        {
            System.out.println("Busqueda no puede realziarse si no se incluye al menos 1 palabra que deba estar o" +
                    " no deba de estar dentro de los archivos!\n");

        }//Fin else 1
        //---Fin del programa
        System.out.println("Fin del programa V3.1");

    }//Fin metodo main

    //------------------------------------------------------------------------------------------
    //Metodo que despliega menu indicando las palabras que se quieren buscar en los archivos; cuales no se quieren que
    //se incluyan; y palabras que pueden estar o no estar
    public static void menu(LinkedList<String> incluir, LinkedList<String> evitar, LinkedList<String> opcionales)
    {
        int inext = -1;
        Scanner bucky = new Scanner(System.in);
        Scanner wendell = new Scanner(System.in);
        String newWord;

        while(inext != 0)
        {
            System.out.println("\n\tINCIDENT LIST SEARCHER\n");
            System.out.println("1) Agregar palabra que debe incluir el documento"); //AND
            System.out.println("2) Agregar palabra que NO deba incluir el documento"); //NOT
            System.out.println("3) Agregar palabra que puede o no incluir el documento"); //OR
            System.out.println("0) Iniciar busqueda de archivos");
            System.out.print("Opcion: ");

            inext = bucky.nextInt();

            switch (inext)
            {
                case 0:
                    break;

                case 1:
                    System.out.print("Palabra que debe estar: ");
                    newWord = wendell.nextLine();
                    incluir.add(newWord);
                    break;

                case 2:
                    System.out.print("Palabra que NO debe estar: ");
                    newWord = wendell.nextLine();
                    evitar.add(newWord);
                    break;

                case 3:
                    System.out.print("Palabra que puede estar o no: ");
                    newWord = wendell.nextLine();
                    opcionales.add(newWord);
                    break;

                default:
                    break;

            }//Fin estructura switch

        }//Fin while 1

    }//Fin metodo menu
    //--------------------------------------------------------------------------------------------------

}//Fin clase Main
