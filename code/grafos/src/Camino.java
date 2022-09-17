public class Camino extends Arista implements Comparable<Camino>{
    private final static float EPS = 1e-9f;

    public Camino(int destino, float peso) {
        super(destino, peso);
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
}
