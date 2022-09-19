import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        GrafoListaAdy G = new GrafoListaAdy();
        G.insVertice("A");
        G.insVertice("B");
        G.insVertice("C");
        G.insVertice("D");
        G.insVertice("E");
        G.insVertice("F");

        G.insArista("A", "B");
        G.insArista("A", "C");
        G.insArista("A", "E");
        G.insArista("B", "D");
        G.insArista("C", "E");
        G.insArista("D", "C");
        G.insArista("E", "F");
        G.insArista("F", "C");

        System.out.println(G.getCantVert());

        System.out.println(G.esAdyacente("C", "E"));

        ArrayList<Integer> a = new ArrayList<>(G.recorridoProfundidad("A"));

//        for(int i=0; i < a.size(); i++){
//            System.out.print(G.getNombre(a.get(i)) + " ");
//        }

        System.out.println(G.getNombresVert());

        G.imprimirListaAdyacencia();

        System.out.println();

        G.elimVertice("C");

        G.imprimirListaAdyacencia();

//        a = new ArrayList<>(G.recorridoAmplitud("A"));
//
//        for(int i=0; i < a.size(); i++){
//            System.out.print(G.getNombre(a.get(i)) + " ");
//        }
//        System.out.println();


    }
}