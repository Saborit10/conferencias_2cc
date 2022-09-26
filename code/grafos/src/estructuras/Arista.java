package estructuras;

public class Arista {
    protected int destino;
    protected float peso;

    public Arista(int destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
