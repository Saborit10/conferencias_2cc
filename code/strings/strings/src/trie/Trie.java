package trie;

import java.util.ArrayList;
import java.util.List;

class Node<T>{
    private boolean terminal;
    private int[] next;
    private T value;

    public Node(int alphabetSize, boolean terminal){
        this.next = new int[alphabetSize];
        this.terminal = terminal;

        for(int i=0; i < alphabetSize; i++)
            this.next[i] = -1;
    }

    public Node(int alphabetSize, boolean esTerminal, T value){
        this(alphabetSize, esTerminal);

        this.value = value;
    }

    public int[] getNext() {
        return next;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

public abstract class Trie<T> {
    private int alphabetSize;
    private List<Node<T>> nodes;

    public Trie(int alphabetSize){
        this.alphabetSize = alphabetSize;
        this.nodes = new ArrayList<>();

        crearNodo();
    }

    private int crearNodo(){
        nodes.add(new Node<>(this.alphabetSize, false));
        return nodes.size() - 1;
    }

    protected abstract int getCharCode(char c);

    public void agregar(String S, T value){
        int nod = 0;

        for(int i=0; i < S.length(); i++){
            int c = getCharCode(S.charAt(i));
            int[] next = nodes.get(nod).getNext();

            if( next[c] == -1 )
                next[c] = crearNodo();
            nod = next[c];
        }
        nodes.get(nod).setTerminal(true);
        nodes.get(nod).setValue(value);
    }

    public T buscar(String S){
        int nod = 0;

        for(int i=0; i < S.length(); i++){
            int c = getCharCode(S.charAt(i));
            int[] next = nodes.get(nod).getNext();

            if( next[c] == -1 )
                return null;
            nod = next[c];
        }
        if( nodes.get(nod).isTerminal() )
            return nodes.get(nod).getValue();
        else
            return null;
    }
}
