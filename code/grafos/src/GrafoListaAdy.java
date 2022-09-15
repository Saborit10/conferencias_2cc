import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GrafoListaAdy implements Grafo{
    /* Cantidad de Vertices */
    private int cantVert;

    /* Lista de Adyacencia */
    private List<List<Arista>> adj;

    /* Lista de Nombres de los Nodos */
    List<String> nombresVert;

    public GrafoListaAdy(){
        this.cantVert = 0;
        nombresVert = new ArrayList<>();
        adj = new LinkedList<>();
    }

    @Override
    public int insVertice(String nombre) {
        for(int i=0; i < nombresVert.size(); i++)
            if( nombresVert.get() )
        nombresVert.add(nombre);
        return nombresVert.size() - 1;
    }

    @Override
    public void insArista(String origen, String destino, float peso) {

    }

    @Override
    public void insArista(String origen, String destino) {

    }

    @Override
    public void elimVertice(String nombre) {

    }

    @Override
    public void elimArista(String origen, String destino) {

    }

    @Override
    public int buscar(String nombre) {
        return 0;
    }

    @Override
    public boolean esAdyacente(String origen, String destino) {
        return false;
    }

    @Override
    public List recorridoAmplitud(String verticeOrigen) {
        return null;
    }

    @Override
    public List recorridoProfundidad(String verticeOrigen) {
        return null;
    }
}
