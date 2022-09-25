public class AristaExt extends Arista implements Comparable<AristaExt>{
  private final static float EPS = 1e-9f;

  private int inicio;

  public AristaExt(int inicio, int destino, float peso) {
    super(destino, peso);
    this.inicio = inicio;
  }

  public int getInicio() {
    return inicio;
  }

  @Override
  public int compareTo(AristaExt otro) {
    if( Math.abs(this.peso - otro.peso) < EPS )
      return 0;
    else if( this.peso < otro.peso )
      return -1;
    else
      return 1;
  }
}
