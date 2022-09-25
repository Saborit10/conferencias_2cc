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

  public static void testArbolExpansionMinimoKruskal(){
    GrafoListaAdy G = new GrafoListaAdy();

    G.insVertice("A");
    G.insVertice("B");
    G.insVertice("C");
    G.insVertice("D");
    G.insVertice("E");
    G.insVertice("F");

    G.insArista("A", "B", 3);
    G.insArista("A", "C", 4);
    G.insArista("A", "D", 2);
    G.insArista("C", "D", 1);
    G.insArista("D", "B", 2);
    G.insArista("D", "E", 5);
    G.insArista("E", "C", 2);

    System.out.println(G.arbolExpansionMinimoKruskal());
  }
}
