import java.util.*;

public class GrafoListaAdy implements Grafo {
    /* Cantidad de Vertices */
    private int cantVert;

    /* Lista de Adyacencia */
    private List<List<Arista>> adj;

    /* Lista de Nombres de los Nodos */
    private List<String> nombresVert;

    public GrafoListaAdy() {
        this.cantVert = 0;
        nombresVert = new ArrayList<>();
        adj = new ArrayList<>();
    }

    @Override
    public int insVertice(String nombre) {
        int id = 0;
        Iterator it = nombresVert.iterator();
        while (it.hasNext()) {
            String nodo = (String) it.next();
            if (nodo.equals(nombre))
                return id;

            id++;
        }

        nombresVert.add(nombre);
        adj.add(new LinkedList<>());
        cantVert++;
        return cantVert - 1;
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
    public void elimVertice(String nombre) throws VerticeNoEncontradoException {
        int idNodo = buscar(nombre);
        List<String> tmpNombresVert = new ArrayList<>();
        List<List<Arista>> tmpAdj = new ArrayList<>();

        for (int i = 0; i < nombresVert.size(); i++) {
            if (!nombre.equals(nombresVert.get(i))) {
                tmpNombresVert.add(nombresVert.get(i));
            }
        }
        nombresVert = tmpNombresVert;
        cantVert--;

        for (int i = 0; i < nombresVert.size(); i++) {
            if (i != idNodo) {
                tmpAdj.add(new LinkedList<>());

                Iterator it = adj.get(i).iterator();
                while (it.hasNext()) {
                    Arista arista = (Arista) it.next();

                    if (arista.getDestino() != idNodo)
                        tmpAdj.get(tmpAdj.size() - 1).add(arista);
                }
            }
        }
        tmpAdj = tmpAdj;
    }

    @Override
    public void elimArista(String origen, String destino) throws VerticeNoEncontradoException {
        int idOrigen = buscar(origen);
        int idDestino = buscar(destino);

        List<Arista> tmp = new LinkedList<>();
        Iterator it = adj.get(idOrigen).iterator();

        while (it.hasNext()) {
            Arista arista = (Arista) it.next();

            if (arista.getDestino() != idDestino)
                tmp.add(arista);
        }
        adj.set(idOrigen, tmp);
    }

    public int getCantVert() {
        return cantVert;
    }

    @Override
    public int buscar(String nombre) throws VerticeNoEncontradoException {
        int id = 0;
        Iterator it = nombresVert.iterator();
        while (it.hasNext()) {
            String nodo = (String) it.next();
            if (nodo.equals(nombre))
                return id;

            id++;
        }
        throw new VerticeNoEncontradoException();
    }

    @Override
    public boolean esAdyacente(String origen, String destino) throws VerticeNoEncontradoException {
        int idOrigen = buscar(origen);
        int idDestino = buscar(destino);


        for(Arista arista: adj.get(idOrigen)){
            if (arista.getDestino() == idDestino)
                return true;            
        }
        return false;

        // Iterator it = adj.get(idOrigen).iterator();

        // while (it.hasNext()) {
        //     Arista arista = (Arista) it.next();

        //     if (arista.getDestino() == idDestino)
        //         return true;
        // }
        // return false;
    }

    @Override
    public List recorridoAmplitud(String verticeOrigen) throws VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        Queue<Integer> Q = new LinkedList<>();
        List<Integer> orden = new LinkedList<>();
        boolean[] marca = new boolean[cantVert];

        Q.add(idOrigen);
        marca[idOrigen] = true;
        while (!Q.isEmpty()) {
            int nod = Q.remove();
            orden.add(nod);

            Iterator it = adj.get(nod).iterator();
            while (it.hasNext()) {
                int idNuevoNodo = ((Arista) it.next()).getDestino();

                if (!marca[idNuevoNodo]) {
                    marca[idNuevoNodo] = true;
                    Q.add(idNuevoNodo);
                }
            }
        }
        return orden;
    }

    @Override
    public List recorridoProfundidad(String verticeOrigen) throws VerticeNoEncontradoException {
        boolean[] marca = new boolean[cantVert];
        List<Integer> orden = new LinkedList<>();

        recorridoProfundidadRecursivo(buscar(verticeOrigen), orden, marca);

        return orden;
    }

    private void recorridoProfundidadRecursivo(int idNodo, List<Integer> orden, boolean[] marca) {
        marca[idNodo] = true;
        orden.add(idNodo);

        Iterator it = adj.get(idNodo).iterator();

        while (it.hasNext()) {
            int idNuevoNodo = ((Arista) it.next()).getDestino();

            if (!marca[idNuevoNodo]) {
                recorridoProfundidadRecursivo(idNuevoNodo, orden, marca);
            }
        }
    }

    public String getNombre(int idNodo) {
        return nombresVert.get(idNodo);
    }

    public List<List<Arista>> getAdj() {
        return adj;
    }

    public List<String> getNombresVert() {
        return nombresVert;
    }

}
