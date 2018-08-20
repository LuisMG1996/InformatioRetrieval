import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Created by luisricardo on 17/08/2018.
 */
public class Search
{
    //Atributos
    TextFile archivo;
    LinkedList<String> palabrasAbuscar;
    LinkedList<String> palabrasAevitar;
    LinkedList<String> palabrasOpcionales;
    LinkedList<String> textoDescompuesto;

    //IncidentList de  Palabras para este archivo.
    //IncidentList inlistBuscadas;
    //IncidentList inlistDescartar;

    //Constructor
    public Search(TextFile file, LinkedList<String> lisuno, LinkedList<String> lisdos, LinkedList<String> lisTres)
    {
        archivo = file;
        palabrasAbuscar = lisuno;
        palabrasAevitar = lisdos;
        palabrasOpcionales = lisTres;

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
        System.out.println(""+numeroLineas);
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
                textTokenized.add(stoken.nextToken());
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
    //Metodo que considerando las palabras del texto ya tokenizadas de este archivo guardadas en atributo texto descompuesto
    //las compara con las palabras contenidas en las tres LinkedList (palabrasABuscar/...AEvitar/...Opcionales).
    //1)La comparación se hace cáracter por cáracter en caso de que algun caracter no coinicda entonces se descarta palabra
    // y se continua con la siguiente;
    //
    //2)En caso de coincidencia agregar marcador a IncidentList correspondiente en el field correspondiente al archivo
    //del que se este tratando este archivo
    public void buscarPalabras(IncidentList[] buscadas, IncidentList[] evitadas)
    {
        textoDescompuesto = tokenizeText();
        listarTokenizedText(textoDescompuesto);

    }//Fin meotdo buscarPalabras



}//Fin clase search
