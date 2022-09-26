package estructuras;

public class Camino implements Comparable<Camino>{
    private final static float EPS = 1e-9f;

    private int destino;
    private float peso;

    public Camino(int destino, float peso) {
        this.destino = destino;
        this.peso = peso;
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

    public int getDestino() {
        return destino;
    }

    public float getPeso() {
        return peso;
    }
}
