public long update(int p, int x){
    return update(p, x, 1, n, 1);
}

private long update(int p, int x, int l, int r,
    int nod){
    
    if( p<=l && r<=p )
        return T[nod] = x;
    if( p < l || r < p )
        return T[nod];
    
    int m = (l + r) / 2;
    
    return T[nod] = update(p, x, l, m, 2*nod)
                  + update(p, x, m+1, r, 2*nod+1);
}