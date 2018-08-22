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

    public void agregarCoincidencia(int index)
    {
        this.add(index,1);
    }//Fin metodo agregar coincidencia

    public void agregarAusencia(int index)
    {
        this.add(index,0);
    }//Fin metodo agregar ausencia.

}//Fin Clase IncidentList
