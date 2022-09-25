import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Test {

  public static void testOrdenTopologico() throws CicleDetectedException {
    GrafoListaAdy G = new GrafoListaAdy();

    G.insVertice("A");
    G.insVertice("B");
    G.insVertice("C");
    G.insVertice("D");
    G.insVertice("E");
    G.insVertice("F");

    G.insArista("A", "B");
    G.insArista("A", "C");
    G.insArista("A", "D");
    G.insArista("C", "D");
    G.insArista("D", "B");
    G.insArista("D", "E");

    // --- Comenta la siguiente linea para que no hayan ciclos en el grafo
//    G.insArista("E", "C");

    System.out.println(
        G.ordenTopologico()
            .stream()
            .map(
                G::getNombre
            )
            .collect(Collectors.toList())
    );
  }

  public static void testCaminoMAciclico() throws CicleDetectedException, VerticeNoEncontradoException {
    GrafoListaAdy G = new GrafoListaAdy();

    G.insVertice("A");
    G.insVertice("B");
    G.insVertice("C");
    G.insVertice("D");
    G.insVertice("E");
    G.insVertice("F");

    G.insArista("A", "B");
    G.insArista("A", "C");
    G.insArista("A", "D");
    G.insArista("C", "D");
    G.insArista("D", "B");
    G.insArista("D", "E");

    // --- Comenta la siguiente linea para que no hayan ciclos en el grafo
//    G.insArista("E", "C");

    System.out.println(
        Arrays.toString(G.caminoMAciclico("A"))
    );
  }
}
