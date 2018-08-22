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

}//Fin clase Inverted Index
