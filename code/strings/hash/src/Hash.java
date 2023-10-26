public class Hash implements Comparable<Hash> {
    public static final long MOD = 1000000007;

    private long val;

    public Hash(long val) {
        this.val = val;
    }

    public Hash(char c) {
        this.val = c - 'a' + 1;
    }

    public long getVal() {
        return val;
    }

    public Hash add(Hash other) {
        return new Hash((this.val + other.val) % MOD);
    }

    public Hash sub(Hash other) {
        return new Hash((this.val + MOD - other.val) % MOD);
    }

    public Hash prod(Hash other) {
        return new Hash((this.val * other.val) % MOD);
    }

    public boolean equals(Hash other) {
        return this.val == other.val;
    }

    public int compareTo(Hash other) {
        long cmp = this.val - other.val;
        return cmp == 0 ? 0 : (cmp < 0 ? -1 : 1);
    }

    public String toString() {
        return "[" + this.val + "]";
    }
}
