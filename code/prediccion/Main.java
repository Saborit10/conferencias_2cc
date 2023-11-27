
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

interface Grafo {
    /**
     * Inserta un nodo en el grafo, si no existe
     * 
     * @param nombre Este es el nombre del nodo
     * @return Retorna el id del nodo
     */
    int insVertice(String nombre);

    /**
     * Agrega una arista CON peso en el grafo
     * 
     * @param origen  Este es el nodo origen de la arista
     * @param destino Este es el nodo final de la arista
     * @param peso    Este es el peso de la arista
     */
    void insArista(String origen, String destino, float peso);

    /**
     * Agrega una arista SIN peso en el grafo
     * 
     * @param origen  Este es el nodo origen de la arista
     * @param destino Este es el nodo final de la arista
     */
    void insArista(String origen, String destino);

    /**
     * Busca el id de un vertice
     * 
     * @param nombre Este es el nombre del vertice
     * @return Retorna el id del vertice buscado
     */
    int buscar(String nombre) throws VerticeNoEncontradoException;

    /**
     * Realiza un recorrido del primero en profundidad, en el grafo
     * 
     * @param verticeOrigen Vertice donde comineza el recorrido
     * @return Retorna una lista con los ids de los vertices, en el orden en que se
     *         recorrieron
     */
    List<Integer> recorridoProfundidad(String verticeOrigen) throws VerticeNoEncontradoException;

    public void eraseNode(String nod) throws VerticeNoEncontradoException;

    public void uneraseNode(String nod) throws VerticeNoEncontradoException;

    public void imprimirListaAdyacencia();
}

class GrafoListaAdy implements Grafo {
    /* Constante que representa el valor infinito */
    private final static float INF = Float.MAX_VALUE;

    /* Cantidad de Vertices */
    private int cantVert;

    /* Lista de Adyacencia */
    private List<List<Arista>> adj;

    /* Lista de Nombres de los Nodos */
    private List<String> nombresVert;

    private List<Boolean> erased;

    public GrafoListaAdy() {
        this.cantVert = 0;
        this.erased = new ArrayList<>();
        nombresVert = new ArrayList<>();
        adj = new ArrayList<>();
    }

    @Override
    public int buscar(String nombre) throws VerticeNoEncontradoException {
        int id = 0;
        for (String nodo : nombresVert) {
            if (nodo.equals(nombre))
                return id;
            id++;
        }
        throw new VerticeNoEncontradoException();
    }

    @Override
    public int insVertice(String nombre) {
        try {
            int id = buscar(nombre);
            return id;
        } catch (VerticeNoEncontradoException e) {
            nombresVert.add(nombre);
            adj.add(new LinkedList<>());
            erased.add(false);
            cantVert++;
            return cantVert - 1;
        }
    }

    public void eraseNode(String nod) throws VerticeNoEncontradoException {
        int id = buscar(nod);

        this.erased.set(id, true);
    }

    public void uneraseNode(String nod) throws VerticeNoEncontradoException {
        int id = buscar(nod);

        this.erased.set(id, false);
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
    public List<Integer> recorridoProfundidad(String verticeOrigen) throws VerticeNoEncontradoException {
        boolean[] marca = new boolean[cantVert];
        List<Integer> orden = new LinkedList<>();

        recorridoProfundidadRecursivo(buscar(verticeOrigen), orden, marca);
        return orden;
    }

    private void recorridoProfundidadRecursivo(int idNodo, List<Integer> orden, boolean[] marca) {
        marca[idNodo] = true;
        orden.add(idNodo);

        for (Arista arista : adj.get(idNodo)) {
            int idNuevoNodo = arista.getDestino();

            if (!marca[idNuevoNodo] && !erased.get(idNuevoNodo)) {
                recorridoProfundidadRecursivo(idNuevoNodo, orden, marca);
            }
        }
    }

    public String getNombre(int idNodo) {
        return nombresVert.get(idNodo);
    }

    /* Helper Method */
    public void imprimirListaAdyacencia() {
        for (int i = 0; i < cantVert; i++) {
            System.out.printf("%s (%d) => ", nombresVert.get(i), i);

            for (Arista arista : adj.get(i)) {
                int destino = arista.getDestino();
                System.out.printf("%s (%d) | ", nombresVert.get(destino), destino);
            }
            System.out.println();
        }
    }
}

public class Main {
    private static final int dx[] = { 0, 0, -1, 1 };
    private static final int dy[] = { -1, 1, 0, 0 };

    private static boolean isValidCell(int n, int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    public static Grafo buildMatrixGraph(int n, int[][] matrix) {
        Grafo G = new GrafoListaAdy();

        for (int i = 1; i <= n * n; i++)
            G.insVertice("" + i);

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                for (int i = 0; i < 4; i++) {
                    int X = x + dx[i];
                    int Y = y + dy[i];

                    if (isValidCell(n, X, Y)) {
                        G.insArista("" + matrix[x][y], "" + matrix[X][Y]);
                    }
                }
            }
        }
        return G;
    }

    private static Grafo G;

    private static int cantCells = 9;

    private static int[][] matrix = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 }
    };

    private static boolean[][] erased = new boolean[3][3];

    private static boolean canEraseCell(int x, int y) throws VerticeNoEncontradoException {
        erased[x][y] = true;
        G.eraseNode("" + matrix[x][y]);

        int orderX[] = { 0, 1, 2 };
        int orderY[] = { 0, 1, 2 };

        Collections.shuffle(Arrays.asList(orderX));
        Collections.shuffle(Arrays.asList(orderY));

        for (int i : orderX) {
            for (int j : orderY) {
                if (!erased[i][j] && (i != x || j != y)) {
                    int cant = 0;

                    try {
                        cant = G.recorridoProfundidad("" + matrix[i][j]).size();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    if (cant == cantCells - 1) {
                        erased[x][y] = false;
                        G.uneraseNode("" + matrix[x][y]);
                        return true;
                    }
                }
            }
        }
        erased[x][y] = false;
        G.uneraseNode("" + matrix[x][y]);
        return false;
    }

    public static void main(String[] args) throws VerticeNoEncontradoException {
        Random random = new Random();

        G = buildMatrixGraph(3, matrix);

        G.imprimirListaAdyacencia();

        int[][] pares = {
                { 0, 0 },
                { 0, 2 },
                { 1, 1 },
                { 2, 0 },
                { 2, 2 },
        };

        int[][] impares = {
                { 0, 1 },
                { 1, 0 },
                { 1, 2 },
                { 2, 1 },
        };

        Collections.shuffle(Arrays.asList(pares));
        Collections.shuffle(Arrays.asList(impares));

        int[] choices = { 1, 3, 5 };

        boolean par = true;

        while (cantCells > 1) {
            int number = choices[random.nextInt(choices.length)];
            par ^= true;

            // System.out.println("Move " + number + " cells.");

            if (!par) {
                for (int i = 0; i < pares.length; i++) {
                    int x = pares[i][0];
                    int y = pares[i][1];

                    if (!erased[x][y] && canEraseCell(x, y)) {
                        System.out.printf("Erase the cell (%d, %d)\n", x + 1, y + 1);

                        erased[x][y] = true;
                        G.eraseNode("" + matrix[x][y]);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < impares.length; i++) {
                    int x = impares[i][0];
                    int y = impares[i][1];

                    if (!erased[x][y] && canEraseCell(x, y)) {
                        System.out.printf("Erase the cell (%d, %d)\n", x + 1, y + 1);

                        erased[x][y] = true;
                        G.eraseNode("" + matrix[x][y]);
                        break;
                    }
                }
            }
            cantCells--;
        }
    }
}
