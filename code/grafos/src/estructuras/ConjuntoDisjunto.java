package estructuras;

public class ConjuntoDisjunto {
  private int[] padre, rango;

  public ConjuntoDisjunto(int cantConjuntos) {
    padre = new int[cantConjuntos];
    rango = new int[cantConjuntos];

    for(int i=0; i < cantConjuntos; i++){
      padre[i] = i;
      rango[i] = 1;
    }
  }

  public int buscarConjunto(int nod) {
    if( padre[nod] != nod )
      return padre[nod] = buscarConjunto(padre[nod]);
    return nod;
  }

  public void unirConjuntos(int a, int b) {
    if( rango[a] > rango[b] ){
      padre[b] = a;
      rango[a]++;
    }
    else{
      padre[a] = b;
      rango[b]++;
    }
  }
}
