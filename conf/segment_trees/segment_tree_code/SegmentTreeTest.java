
import java.io.*;

/**
 * SegmentTree
 */
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

    private long build(int l, int r, int nod){
        if( l == r )
            return T[nod] = A[l];
        
        int m = (l + r) / 2;

        return T[nod] = build(l, m, 2 * nod) + build(m+1, r, 2*nod+1);
    }


    public long query(int a, int b){
        return query(a, b, 1, n, 1);
    }

    private long query(int a, int b, int l, int r, int nod){
        if( a<=l && r<=b )
            return T[nod];
        if( b < l || r < a )
            return 0;
        
        int m = (l + r) / 2;
        
        return query(a, b, l, m, 2*nod) + query(a, b, m+1, r, 2*nod+1);
    }


    public long update(int p, int x){
        return update(p, x, 1, n, 1);
    }

    private long update(int p, int x, int l, int r, int nod){
        if( p<=l && r<=p )
            return T[nod] = x;
        if( p < l || r < p )
            return T[nod];
        
        int m = (l + r) / 2;
        
        return T[nod] = update(p, x, l, m, 2*nod) + update(p, x, m+1, r, 2*nod+1);
    }
    
}

public class SegmentTreeTest {

    public static void main(String args[]) throws IOException{
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));

        String[] tmp;

        tmp = f.readLine().split(" ");

        int n = Integer.parseInt(tmp[0]);
        int q = Integer.parseInt(tmp[1]);
        int[] A = new int[n+1];

        tmp = f.readLine().split(" ");

        for(int i=1; i<=n; i++){
            A[i] = Integer.parseInt(tmp[i-1]);
        }

        SegmentTree segmentTree = new SegmentTree(n, A);

        while( q-- > 0 ){
            tmp = f.readLine().split(" ");

            int ty = Integer.parseInt(tmp[0]);
            int a = Integer.parseInt(tmp[1]);
            int b = Integer.parseInt(tmp[2]);

            if( ty == 1 ){
                segmentTree.update(a, b);
            }
            else{
                System.out.println(segmentTree.query(a, b));
            }
        }


        
    }
}