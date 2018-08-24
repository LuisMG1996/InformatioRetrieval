import java.util.LinkedList;

public class InvertedIndex extends LinkedList<IncidentList> {

    //Atributos
    //Numero Actual DE Palabras e incident lists en el InvertedIndex; recordando que por cada palabra hay una incidentList
    int numeroActualPalabras;
    LinkedList<String> lista;

    //Constructor
    public InvertedIndex()
    {
        numeroActualPalabras = 0;

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
            System.out.print(i+")"+this.get(i).wordOfDictionary +" :" );

            //Incident list de la palabra correspodniente
            for(int k = 0; k < this.get(i).size(); k++)
            {
                System.out.print("\t"+this.get(i).get(k)+" | ");

            }//Fin for 2

            //Doble salto de linea para nueva palabra
            System.out.println("\n");

        }//Fin for 1

    }//Fin metodo listarInvertedIndex

    public int getIndex(String word){
        int index = -1;
        for (IncidentList incident: this){
            if (incident.wordOfDictionary.compareTo(word) == 0){
                index = this.indexOf(incident);
            }
        }
        return index;
    }

}//Fin clase Inverted Index
