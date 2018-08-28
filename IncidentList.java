import java.util.LinkedList;

/**
 * Luis Ricardo Montes Gómez 153788
 * José Francisco Zerón Cabrea 154678
 */
public class IncidentList extends LinkedList<Integer>
{
    //Atributos
    String wordOfDictionary;

    //Constructor
    public IncidentList(String dictionary)
    {
        wordOfDictionary = dictionary;

    }//Fin IncidentList

    public void agregarCoincidencia(int id)
    {
        this.add(id);
    }//Fin metodo agregar coincidencia

}//Fin Clase IncidentList
