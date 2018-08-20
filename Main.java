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
        int palabrasABuscar;

        //Array de IncidentLists
        IncidentList inlistsBuscadas[];
        IncidentList inlistsEvitadas[];

        //Archivo uno
        TextFile archivoUno;
        Search buscadorArchivoUno;

        //Archivo Dos

        //---Datos de entrada
        palabrasAIncluir = new LinkedList<String>();
        palabrasAEvitar = new LinkedList<String>();
        palabrasOpcionales = new LinkedList<String>();
        palabrasABuscar = 0;

        menu(palabrasAIncluir,palabrasAEvitar,palabrasOpcionales);

        archivoUno = new TextFile("archivoUno.txt",System.getProperty("user.dir"));
        buscadorArchivoUno = new Search(archivoUno, palabrasAIncluir, palabrasAEvitar, palabrasOpcionales);

        //---Proceso

        //Si el usuario no incluye ninguna palabra que obligatoriamente tenga que estar o que NO tenga que aparecer
        //indicar que debe incluir al menos una palabra en su busqueda
        palabrasABuscar = palabrasAIncluir.size() + palabrasAEvitar.size();

        if(palabrasABuscar > 0)
        {
            inlistsBuscadas = new IncidentList[palabrasAIncluir.size()];
            inlistsEvitadas = new IncidentList[palabrasAEvitar.size()];

            //---Datos de sailda
            buscadorArchivoUno.buscarPalabras(inlistsBuscadas, inlistsEvitadas);

        }//Fin if 1
        else
        {
            System.out.println("Busqueda no puede realziarse si no se incluye al menos 1 palabra que deba estar o" +
                    " no deba de estar dentro de los archivos!\n");

        }//Fin else 1
        //---Fin del programa
        System.out.println("Fin del programa V3");

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
