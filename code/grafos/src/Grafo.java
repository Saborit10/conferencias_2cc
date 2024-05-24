import excepciones.CicleDetectedException;
import excepciones.CicloNegativoException;
import excepciones.VerticeNoEncontradoException;

import java.util.List;

public interface Grafo {
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

    float[] caminoMConPesosNegativos(String verticeOrigen) throws VerticeNoEncontradoException, CicloNegativoException;

    float[] caminoMAciclico(String verticeOrigen) throws CicleDetectedException, VerticeNoEncontradoException;

    float arbolExpansionMinimoPrim();

    float arbolExpansionMinimoKruskal();

    float[][] caminoMEntrePares();
}