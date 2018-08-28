import java.io.*;

/**
 * Luis Ricardo Montes Gómez 153788
 * José Francisco Zerón Cabrea 154678
 */
public class TextFile
{
    //Atributos
    FileReader fileRader;           //Lector de archivo
    FileWriter fileWriter;          //Escritor de archivo
    BufferedReader bufferedReader;  //Buffer lectura
    BufferedWriter bufferedWriter;  //Buffer escritura

    String nombreAbsoluto;
    File archivo;

    public TextFile(String nombre, String direccion)
    {
        nombreAbsoluto = (direccion+"/"+nombre);
        archivo = new File(nombreAbsoluto);

    }//Fin constructor

    //Metodos

    //Crea un nuevo File vacio en la direccion especificada y con el nombre dado
    public void Crear()
    {
        try
        {
            archivo.createNewFile();
            //Objeto para escribir texto (caracteres) en un otputStream
            fileWriter = new FileWriter(archivo);
            bufferedWriter = new BufferedWriter(fileWriter);

        }//Fin try
        catch (IOException ioe)
        {
            System.out.println("No se pudo crear el archivo: \n");
            ioe.printStackTrace();

        }//Fin catch

    }//Fin metodo Crear
    //--------------------------------------------------------------------
    //Metodo que abre el archivo para que cuando se siga leyendo continue
    //desde donde se quedo en la ultima lectura
    public void Abrir()
    {
        try
        {
            fileRader = new FileReader(archivo);
            //Objeto que lee solo caracterse del strem de entrada guardandolo en buffer
            bufferedReader = new BufferedReader(fileRader);

        }//Fin try
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }//Fin catch

    }//Fin metodo Abrir
    //--------------------------------------------------------------------
    //Metodo que lee solo una linea. Aqui no se abre ni se cierra el archivo ya que para
    //que la lectura pueda ser CONTINUAMENTE (linea X linea), el archivo se abre de forma global
    //llamando al metodo Abrir() antes de este metodo.
    public String Leer()
    {
        String linea;

        try
        {

            //Leer solo una linea(hasta encontrar \n)
            if( (linea = bufferedReader.readLine() ) !=  null )
            {

            }//Fin else 1
            else
            {
                linea = null;

            }//Fin else 1

            //Cerrar Archivo
            //bufferedReader.close();

        }//Fin try
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            linea = null;
        }//Fin catch

        return linea;

    }//Fin metodo Leer
    //--------------------------------------------------------------------
    //Metodo que lee un entero de Archivo
    public int LeerInt()
    {
        String linea;
        int entero = 0;

        linea = Leer();

        if(linea != null)
        {
            entero = Integer.parseInt(linea);
        }//Fin if 1

        return entero;

    }//Fin metodo LeerInt
    //--------------------------------------------------------------------
    //Metodo que lee una String
    public String LeerString()
    {
        String cadena;

        cadena = Leer();

        if(cadena == null)
        {
            cadena = "";
        }//Fin if 1

        return cadena;

    }//Finmetod Leer String
    //--------------------------------------------------------------------
    //Metodo que escribe solo una linea. IMPORTANTE: despues de finalizar
    //escritura llamara a metodo FinalizarEscritura(), si no no se escribira
    //nada en el archivo
    public void Escribir(String linea)
    {
        try
        {
            bufferedWriter.write(linea);    //Escribir cadena
            bufferedWriter.newLine();       //Escribir salto de linea

        }//Fin try
        catch (IOException ioe)
        {
            ioe.printStackTrace();

        }//Fin catch

    }//Fin metodo Escribir
    //------------------------------------------------------------------------
    //Escribir entero en File
    public void EscribirInt(int entero)
    {
        Escribir(""+entero);

    }//Fin clase Escribir Int
    //------------------------------------------------------------------------
    //Escribir String en File
    public void EscribirString(String cadena)
    {
        Escribir(cadena);
    }//Fin clase EscribirString
    //-----------------------------------------------------------------------
    //Metodo que se pone una vez que ha finalizado la escritura del archcvio
    //para cerrar el buffer de escritura
    public void FinalizarEscritura()
    {
        try
        {
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }//Fin metodo Finalizar Escritura
    //-----------------------------------------------------------------------
    //Metodo que cuetna el numero de lineas que hay en archivo
    public int ContarLineas()
    {
        int numeroLineas = 0;

        try
        {
            fileRader = new FileReader(archivo);
            //Objeto que lee solo caracterse del strem de entrada guardandolo en buffer
            bufferedReader = new BufferedReader(fileRader);

            while( bufferedReader.readLine() != null)
            {
                numeroLineas++;
            }//Fin while

            //Cerrar Archivo
            bufferedReader.close();

        }//Fin try
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }//Fin catch

        return numeroLineas;

    }//Fin metodo ContarLineas
    //---------------------------------------------------------------------------------------------------------------


}//Fin clase Text Field
