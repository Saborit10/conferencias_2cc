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

        for(int i=0; i < G.getCantVert(); i++){
            System.out.print(G.getNombre(i) + ": ");

            Iterator it = G.getAdj().get(i).iterator();
            while( it.hasNext() ){
                int id = ((Arista)it.next()).getDestino();
//                System.out.printf("(%d) | ", id);
                System.out.printf("%s (%d) | ", G.getNombre(id), id);
            }
            System.out.println();
        }

        System.out.println();

        G.elimVertice("C");

        System.out.println(G.getNombresVert());

        for(int i=0; i < G.getCantVert(); i++){
            System.out.print(G.getNombre(i) + ": ");

            Iterator it = G.getAdj().get(i).iterator();
            while( it.hasNext() ){
                int id = ((Arista)it.next()).getDestino();
//                System.out.printf("(%d) | ", id);
                System.out.printf("%s (%d) | ", G.getNombre(id), id);
            }
            System.out.println();
        }

//        a = new ArrayList<>(G.recorridoAmplitud("A"));
//
//        for(int i=0; i < a.size(); i++){
//            System.out.print(G.getNombre(a.get(i)) + " ");
//        }
//        System.out.println();


    }
}