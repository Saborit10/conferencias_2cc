import trie.Trie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner f = new Scanner(System.in);

        int n = f.nextInt();

        Trie<Integer> T = new Trie<>(26) {
            @Override
            protected int getCharCode(char c) {
                return c - 'A';
            }
        };

        for(int i=0; i < n; i++){
            String S = f.next().trim();

            T.agregar(S, i+1);
        }

        int q = f.nextInt();

        for(int i=0; i < q; i++){
            String S = f.next().trim();

            Integer resultado = T.buscar(S);

            System.out.println(resultado == null ? -1 : resultado);
        }
    }
}