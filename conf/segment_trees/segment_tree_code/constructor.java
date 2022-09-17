class SegmentTree {
    private int n;
    private int A[];
    private long T[];

    public SegmentTree(int n, int[] A){
        this.n = n;
        this.A = A;
        T = new long[4 * n + 5];
        
        build(1, n, 1);
    }

    // Metodos de la clase. 
}
