import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{

        GrafoListaAdy G = new GrafoListaAdy();

        G.insArista("A", "B");
        G.insArista("B", "A");

        G.insArista("A", "D");
        G.insArista("D", "A");

        G.insArista("A", "C");
        G.insArista("C", "A");

        G.insArista("B", "C");
        G.insArista("C", "B");

        G.insArista("B", "E");
        G.insArista("E", "B");

        G.insArista("C", "E");
        G.insArista("E", "C");

        System.out.println(G.getCantVert());

        System.out.println(G.esAdyacente("C", "E"));

        ArrayList<Integer> a = new ArrayList<>(G.recorridoAmplitud("A"));

        for(int i=0; i < a.size(); i++){
            System.out.println(G.getNombre(a.get(i)));
        }

    }
}