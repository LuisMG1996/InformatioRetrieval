import java.util.LinkedList;

/**
 * Created by luisricardo on 17/08/2018.
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
