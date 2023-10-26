import java.util.List;
import java.util.ArrayList;

public class Hasher {
    public static final int MAX_LENGTH = 100005;
    public static final Hash BASE = new Hash(29);

    private static Hash[] P;
    private Hash[] H;

    public Hasher(String S) {
        if (P == null) {
            P = new Hash[MAX_LENGTH];

            P[0] = new Hash(1);
            for (int i = 1; i < MAX_LENGTH; i++)
                P[i] = P[i - 1].prod(BASE);

        }

        H = new Hash[S.length()];

        // calcular el hashing de cada prefijo
        for (int i = 0; i < S.length(); i++) {
            H[i] = P[i].prod(new Hash(S.charAt(i)));
            H[i] = H[i].add(i > 0 ? H[i - 1] : new Hash(0));
        }
    }

    public Hash query(int a, int b) {
        if (a > b)
            return new Hash(0);

        Hash beg = a > 0 ? H[a - 1] : new Hash(0);
        Hash end = H[b];

        return end.sub(beg).prod(P[MAX_LENGTH - a - 1]);
    }
}
