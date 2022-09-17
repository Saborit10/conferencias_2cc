private long build(int l, int r, int nod){
    if( l == r )
        return T[nod] = A[l];
    
    int m = (l + r) / 2;

    return T[nod] = build(l, m, 2*nod)
                  + build(m+1, r, 2*nod+1);
}
