public long query(int a, int b){
    return query(a, b, 1, n, 1);
}

private long query(int a, int b, int l, int r,
    int nod){
    
    if( a<=l && r<=b )
        return T[nod];
    if( b < l || r < a )
        return 0;
    
    int m = (l + r) / 2;
    
    return query(a, b, l, m, 2*nod)
         + query(a, b, m+1, r, 2*nod+1);
}
