import java.util.ArrayList;
import java.util.Iterator;
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
        adj = new ArrayList<>();
    }

    @Override
    public int insVertice(String nombre) {
        int id = 0;
        Iterator it = nombresVert.iterator();
        while( it.hasNext() ){
            String nodo = (String) it.next();
            if( nodo.equals(nombre) )
                return id;

            id++;
        }

        nombresVert.add(nombre);
        adj.add(new LinkedList<>());
        return nombresVert.size() - 1;
    }

    @Override
    public void insArista(String origen, String destino, float peso) {
        int idOrigen = insVertice(origen);
        int idDestino = insVertice(destino);

        Arista arista = new Arista(idDestino, peso);

        adj.get(idOrigen).add(arista);
    }

    @Override
    public void insArista(String origen, String destino) {
        insArista(origen, destino, 0);
    }

    @Override
    public void elimVertice(String nombre) {

    }

    @Override
    public void elimArista(String origen, String destino) throws VerticeNoEncontradoException {
        int idOrigen = buscar(origen);
        int idDestino = buscar(destino);

        List<Arista> tmp = new LinkedList<>();
        Iterator it = adj.get(idOrigen).iterator();

        while( it.hasNext() ){
            Arista arista = (Arista) it.next();

            if( arista.getDestino() != idDestino )
                tmp.add(arista);
        }
        adj.set(idOrigen, tmp);
    }

    @Override
    public int buscar(String nombre) throws VerticeNoEncontradoException {
        int id = 0;
        Iterator it = nombresVert.iterator();
        while( it.hasNext() ){
            String nodo = (String) it.next();
            if( nodo.equals(nombre) )
                return id;

            id++;
        }
        throw new VerticeNoEncontradoException();
    }

    @Override
    public boolean esAdyacente(String origen, String destino) throws VerticeNoEncontradoException {
        int idOrigen = buscar(origen);
        int idDestino = buscar(destino);

        Iterator it = adj.get(idOrigen).iterator();

        while( it.hasNext() ){
            Arista arista = (Arista) it.next();

            if( arista.getDestino() == idDestino )
                return true;
        }
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
