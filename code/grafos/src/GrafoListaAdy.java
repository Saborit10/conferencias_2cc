import estructuras.Arista;
import estructuras.AristaExt;
import estructuras.Camino;
import estructuras.ConjuntoDisjunto;
import excepciones.CicleDetectedException;
import excepciones.CicloNegativoException;
import excepciones.VerticeNoEncontradoException;

import java.util.*;

public class GrafoListaAdy implements Grafo {
    /* Constante que representa el valor infinito */
    private final static float INF = Float.MAX_VALUE;

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
    public int buscar(String nombre) throws VerticeNoEncontradoException {
        int id = 0;
        for(String nodo: nombresVert){
            if (nodo.equals(nombre))
                return id;
            id++;
        }
        throw new VerticeNoEncontradoException();
    }

    @Override
    public int insVertice(String nombre) {
        try{
            int id = buscar(nombre);
            return id;
        } catch (VerticeNoEncontradoException e) {
            nombresVert.add(nombre);
            adj.add(new LinkedList<>());
            cantVert++;
            return cantVert - 1;
        }
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
        insArista(origen, destino, 1);
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

        for (int i = 0; i < cantVert; i++) {
            if (i != idNodo) {
                tmpAdj.add(new LinkedList<>());

                for (Arista arista: adj.get(i)){
                    int idDestino = arista.getDestino();

                    if (idDestino != idNodo){
                        if(idDestino >= idNodo){
                            arista.setDestino(idDestino - 1);
                        }
                        tmpAdj.get(tmpAdj.size() - 1).add(arista);
                    }
                }
            }
        }
        adj = tmpAdj;
        cantVert--;
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

    @Override
    public boolean esAdyacente(String origen, String destino) throws VerticeNoEncontradoException {
        int idOrigen = buscar(origen);
        int idDestino = buscar(destino);

        for(Arista arista: adj.get(idOrigen)){
            if (arista.getDestino() == idDestino)
                return true;            
        }
        return false;
    }

    @Override
    public List<Integer> recorridoAmplitud(String verticeOrigen) throws VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        Queue<Integer> Q = new LinkedList<>();
        List<Integer> orden = new LinkedList<>();
        boolean[] marca = new boolean[cantVert];

        Q.add(idOrigen);
        marca[idOrigen] = true;
        while (!Q.isEmpty()) {
            int nod = Q.remove();
            orden.add(nod);

            for (Arista arista: adj.get(nod)){
                int idNuevoNodo = arista.getDestino();

                if (!marca[idNuevoNodo]) {
                    marca[idNuevoNodo] = true;
                    Q.add(idNuevoNodo);
                }
            }
        }
        return orden;
    }

    @Override
    public List<Integer> recorridoProfundidad(String verticeOrigen) throws VerticeNoEncontradoException {
        boolean[] marca = new boolean[cantVert];
        List<Integer> orden = new LinkedList<>();

        recorridoProfundidadRecursivo(buscar(verticeOrigen), orden, marca);
        return orden;
    }
    
    
    private void recorridoProfundidadRecursivo(int idNodo, List<Integer> orden, boolean[] marca) {
        marca[idNodo] = true;
        orden.add(idNodo);
        
        for(Arista arista: adj.get(idNodo)){
            int idNuevoNodo = arista.getDestino();
            
            if (!marca[idNuevoNodo]) {
                recorridoProfundidadRecursivo(idNuevoNodo, orden, marca);
            }
        }
    }
    
    /**
     * Algoritmo de Dijkstra en O(|V|^2)
     * @param verticeOrigen Es el vertice desde donde se calculan los caminos minimos
     * @return Retorna un arreglo de tipo float con las distancias desde verticeOrigen hasta cada uno de los nodos.
     * @throws VerticeNoEncontradoException
     * Tested on: https://dmoj.uclv.cu/problem/bparty
     */
    @Override
    public float[] caminoMConPesosPositivosGrafoDenso(String verticeOrigen) throws VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        float[] dist = new float[cantVert];
        boolean[] marca = new boolean[cantVert];

        for(int i=0; i < cantVert; i++)
            dist[i] = INF;
        dist[idOrigen] = 0;

        for (int i=0; i < cantVert; i++){
            int nod = -1;

            for(int j=0; j < cantVert; j++){
                if( !marca[j] && (nod == -1 || dist[j] < dist[nod]) )
                    nod = j;
            }
            marca[nod] = true;

            for(Arista arista: adj.get(nod)){
                int destino = arista.getDestino();
                float peso = arista.getPeso();

                if( dist[destino] > dist[nod] + peso )
                    dist[destino] = dist[nod] + peso;
            }
        }
        return dist;
    }

    @Override
    public float[] caminoMConPesosPositivos(String verticeOrigen) throws VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        float[] dist = new float[cantVert];
        PriorityQueue<Camino> Q = new PriorityQueue<>();

        for(int i=0; i < cantVert; i++)
            dist[i] = INF;
        dist[idOrigen] = 0;

        Q.add(new Camino(idOrigen, 0f));
        while( !Q.isEmpty() ){
            int nod = Q.peek().getDestino();

            if( dist[nod] < Q.peek().getPeso() ){
                Q.poll();
                continue;
            }
            Q.poll();

            for(Arista arista: adj.get(nod)){
                int destino = arista.getDestino();
                float peso = arista.getPeso();

                if( dist[destino] > dist[nod] + peso ){
                    dist[destino] = dist[nod] + peso;
                    Q.add(new Camino(destino, dist[destino]));
                }
            }
        }
        return dist;
    }

    int gradoSalida(String vertice) throws VerticeNoEncontradoException {
        int idNodo = buscar(vertice);

        return adj.get(idNodo).size();
    }

    int gradoEntrada(String vertice) throws VerticeNoEncontradoException {
        int idNodo = buscar(vertice);

        int grado = 0;
        for(int i=0; i < cantVert; i++){
            for(Arista arista: adj.get(i)){
                if( arista.getDestino() == idNodo )
                    grado++;
            }
        }
        return grado;
    }

    @Override
    public float[] caminoMConPesosNegativos(String verticeOrigen) throws VerticeNoEncontradoException, CicloNegativoException {
        int idOrigen = buscar(verticeOrigen);
        float[] dist = new float[cantVert];

        for(int i=0; i < cantVert; i++)
            dist[i] = INF;
        dist[idOrigen] = 0;

        for(int fase=1; fase<=cantVert; fase++){
            for(int nodo=0; nodo < cantVert; nodo++){
                for(Arista arista: adj.get(nodo)){
                    int destino = arista.getDestino();
                    float peso = arista.getPeso();

                    if( dist[destino] > dist[nodo] + peso ){
                        dist[destino] = dist[nodo] + peso;

                        if( fase == cantVert )
                            throw new CicloNegativoException();
                    }
                }
            }
        }
        return dist;
    }

    public List<Integer> ordenTopologico() throws CicleDetectedException {
        int[] grado = new int[cantVert];
        boolean[] marca = new boolean[cantVert];

        for(int nod=0; nod < cantVert; nod++)
            for(Arista arista: adj.get(nod))
                grado[arista.getDestino()]++;

        Queue<Integer> Q = new LinkedList<>();
        for(int nod=0; nod < cantVert; nod++)
            if( grado[nod] == 0 )
                Q.add(nod);

        List<Integer> orden = new ArrayList<>();
        while( !Q.isEmpty() ){
            int nod = Q.poll();
            orden.add(nod);

            marca[nod] = true;
            for(Arista arista: adj.get(nod)){
                int destino = arista.getDestino();
                grado[destino]--;

                if( grado[destino] == 0 && !marca[destino] )
                    Q.add(destino);
            }
        }

        if( orden.size() != cantVert )
            throw new CicleDetectedException();

        return orden;
    }

    @Override
    public float[] caminoMAciclico(String verticeOrigen) throws CicleDetectedException, VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        float[] dist = new float[cantVert];

        for(int i=0; i < cantVert; i++)
            dist[i] = INF;
        dist[idOrigen] = 0;

        List<Integer> orden = ordenTopologico();
        for(Integer nod: orden){
            for(Arista arista: adj.get(nod)){
                int destino = arista.getDestino();
                float peso = arista.getPeso();

                if( dist[destino] > dist[nod] + peso )
                    dist[destino] = dist[nod] + peso;
            }
        }
        return dist;
    }
    
    public long[] cantCaminosGrafoDirigidoSinCiclos(String verticeOrigen) throws VerticeNoEncontradoException, CicleDetectedException {
        int idOrigen = buscar(verticeOrigen);
        long[] cant = new long[cantVert];
        
        /* En aristasEnt vamos a guardar, para cada nodo, una lista de los nodos desde
        los que se puede llegar a este. No hace falta guardar la arista completa */
        List<List<Integer>> aristasEnt = new ArrayList<>();
        for(int i=0; i < cantVert; i++)
            aristasEnt.add(new ArrayList<>());
        
        for(int nodo=0; nodo < cantVert; nodo++)
            for(Arista arista: adj.get(nodo))
                aristasEnt.get(arista.getDestino()).add(nodo);
        
        cant[idOrigen] = 1;
        List<Integer> orden = ordenTopologico();
        
        for(Integer nodo: orden){
            for(Integer nodoAnterior: aristasEnt.get(nodo))
                cant[nodo] += cant[nodoAnterior];
        }
        
        return cant;
    }

    public String getNombre(int idNodo) {
        return nombresVert.get(idNodo);
    }

    public int getCantVert() {
        return cantVert;
    }

    public List<List<Arista>> getAdj() {
        return adj;
    }

    public List<String> getNombresVert() {
        return nombresVert;
    }

    @Override
    public float arbolExpansionMinimoPrim() {
        return 0;
    }

    @Override
    public float arbolExpansionMinimoKruskal() {
        List<AristaExt> listaAristas = new ArrayList<>();

        for (int nod=0; nod < cantVert; nod++){
            for(Arista arista: adj.get(nod)){
                listaAristas.add(new AristaExt(nod, arista.getDestino(), arista.getPeso()));
            }
        }

        Collections.sort(listaAristas);
        ConjuntoDisjunto S = new ConjuntoDisjunto(cantVert);

        float mst = 0.0f;
        for(AristaExt arista: listaAristas){
            int conjIni = S.buscarConjunto(arista.getInicio());
            int conjDest = S.buscarConjunto(arista.getDestino());

            if( conjIni != conjDest ){
                mst += arista.getPeso();

                S.unirConjuntos(conjIni, conjDest);
            }
        }
        return mst;
    }

    @Override
    public float [][] caminoMEntrePares() {
        float[][] distances = new float[cantVert][cantVert];

        for (int i = 0; i < cantVert; i++) {
            for(int j = 0 ; j <cantVert ;j++)
            {
                distances[i][j]=INF;
            }
            distances[i][i] = 0;
        }

        for (int i = 0; i < cantVert; i++) {
           
            distances[i][i] = 0;
            for (Arista arista : adj.get(i)) {
            
                if(distances[i][arista.getDestino()]>arista.getPeso())
                {
                    distances[i][arista.getDestino()]=distances[arista.getDestino()][i]=arista.getPeso();
                }

            }
             
        }

        for (int k = 0; k < cantVert; k++) 
        {
             for (int i = 0; i < cantVert; i++)
            {
                 for (int j = i + 1; j < cantVert; j++) {
                    if(distances[i][k]==INF||distances[k][j]==INF)
                        continue;
                    float a = distances[i][k] + distances[k][j];
                    if (distances[i][j] > a) 
                    {
                        distances[j][i] = distances[i][j] = a;
                    }
                }
            }
        }

        return distances;
    }

    /* Helper Method */
    public void imprimirListaAdyacencia(){
        for(int i=0; i < cantVert; i++){
            System.out.printf("%s (%d) => ", nombresVert.get(i), i);

            for(Arista arista: adj.get(i)){
                int destino = arista.getDestino();
                System.out.printf("%s (%d) | ",     nombresVert.get(destino), destino);
            }
            System.out.println();
        }
    }
}
