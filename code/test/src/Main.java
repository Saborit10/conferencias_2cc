import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.*;

import java.util.List;

class VerticeNoEncontradoException extends Exception {
}


class Arista {
    protected int destino;
    protected float peso;

    public Arista(int destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}

class Camino extends Arista implements Comparable<Camino>{
    private final static float EPS = 1e-9f;

    public Camino(int destino, float peso) {
        super(destino, peso);
    }

    @Override
    public int compareTo(Camino camino) {
        if( Math.abs(this.peso - camino.peso) < EPS )
            return 0;
        else if( this.peso < camino.peso )
            return -1;
        else
            return 1;
    }
}



interface Grafo {
    /**
     * Inserta un nodo en el grafo, si no existe
     * @param nombre Este es el nombre del nodo
     * @return Retorna el id del nodo
     */
    int insVertice(String nombre);

    /**
     * Agrega una arista CON peso en el grafo
     * @param origen Este es el nodo origen de la arista
     * @param destino Este es el nodo final de la arista
     * @param peso Este es el peso de la arista
     */
    void insArista(String origen, String destino, float peso);

    /**
     * Agrega una arista SIN peso en el grafo
     * @param origen Este es el nodo origen de la arista
     * @param destino Este es el nodo final de la arista
     */
    void insArista(String origen, String destino);

    /**
     * Elimina un nodo del grafo
     * @param nombre Este es el nombre del grafo
     */
    void elimVertice(String nombre) throws VerticeNoEncontradoException;

    /**
     * Elimina una arista del grafo
     * @param origen Este es el nodo origen de la arista
     * @param destino Este es el nodo final de la arista
     */
    void elimArista(String origen, String destino) throws VerticeNoEncontradoException;

    /**
     * Busca el id de un vertice
     * @param nombre Este es el nombre del vertice
     * @return Retorna el id del vertice buscado
     */
    int buscar(String nombre) throws VerticeNoEncontradoException;

    /**
     * Retorna el nombre de un nodo, dado el id
     * @param id Identificador del nodo
     * @return Retorna una cadena con el nombre del nodo
     */
    String getNombre(int idNodo);

    /**
     * Comprueba que dos nodos estan unidos por una arista
     * @param origen Nodo origen
     * @param destino Nodo final
     * @return Retorna true si los nodos estan unidos por una arista. En caso contrario retorna falso.
     */
    boolean esAdyacente(String origen, String destino) throws VerticeNoEncontradoException;

    /**
     * Realiza un recorrido del primero a lo ancho, en el grafo
     * @param verticeOrigen Vertice donde comineza el recorrido
     * @return Retorna una lista con los ids de los vertices, en el orden en que se recorrieron
     */
    List recorridoAmplitud(String verticeOrigen) throws VerticeNoEncontradoException;

    /**
     * Realiza un recorrido del primero en profundidad, en el grafo
     * @param verticeOrigen Vertice donde comineza el recorrido
     * @return Retorna una lista con los ids de los vertices, en el orden en que se recorrieron
     */
    List recorridoProfundidad(String verticeOrigen) throws VerticeNoEncontradoException;

    float[] caminoMSinPesos(String verticeOrigen);

    float[] caminoMConPesosPositivosGrafoDenso(String verticeOrigen) throws VerticeNoEncontradoException;

    float[] caminoMConPesosPositivos(String verticeOrigen) throws VerticeNoEncontradoException;

    float[] caminoMConPesosNegativos(String verticeOrigen) throws VerticeNoEncontradoException;

    float[] caminoMAciclico(String verticeOrigen);
}

class GrafoListaAdy implements Grafo {
    /* Constante que representa el vaor infinito */
    private final float INF = Float.MAX_VALUE;

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

    @Override
    public float[] caminoMSinPesos(String verticeOrigen) {
        return null;
    }

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

    public float[] caminoMConPesosPositivos(String verticeOrigen) throws VerticeNoEncontradoException {
        int idOrigen = buscar(verticeOrigen);
        float[] dist = new float[cantVert];
        PriorityQueue<Camino> Q = new PriorityQueue<>();

        for(int i=0; i < cantVert; i++)
            dist[i] = INF;
        dist[idOrigen] = 0;

        Q.add(new Camino(idOrigen, 0f));
        while( !Q.isEmpty() ){
            int nod = Q.poll().getDestino();

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
    
    @Override
    public float[] caminoMConPesosNegativos(String verticeOrigen) {
        return null;
    }

    @Override
    public float[] caminoMAciclico(String verticeOrigen) {
        return null;
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


public class Main {
    public static void main(String[] args) throws Exception{
        Scanner f = new Scanner(System.in);

        GrafoListaAdy G = new GrafoListaAdy();

        int cn = f.nextInt();
        int ca = f.nextInt();
        int ni = f.nextInt();

        for(int i=0; i < ca; i++){
            int a = f.nextInt();
            int b = f.nextInt();
            int c = f.nextInt();

            G.insArista("" + a, "" + b, c);
            G.insArista("" + b, "" + a, c);

        }

        float[] dist = G.caminoMConPesosPositivos("" + ni);

        float sol = 0;
        for(int i=0; i < cn; i++)
            if( sol < dist[i] )
                sol = dist[i];

        System.out.println(2 * (int)sol);
    }
}
