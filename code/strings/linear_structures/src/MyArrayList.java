public class MyArrayList<T> implements MyList<T> {
    private int last;
    private T[] items;

    public MyArrayList() {
        items = (T[]) (new Object[1]);
        last = -1;
    }

    public MyArrayList(int initSize) {
        items = (T[]) (new Object[initSize]);
        last = -1;
    }

    void extendArray() {
        T[] newArray = (T[]) (new Object[items.length * 2]);

        for (int i = 0; i < items.length; i++)
            newArray[i] = items[i];

        items = newArray;
    }

    @Override
    public boolean isEmpty() {
        return last == -1;
    }

    @Override
    public void clear() {
        items = (T[]) (new Object[1]);
        last = -1;
    }

    @Override
    public int size() {
        return last + 1;
    }

    @Override
    public T add(T elem) {
        if (last + 1 == items.length)
            extendArray();

        items[++last] = elem;

        return elem;
    }

    @Override
    public T add(int pos, T elem) throws InvalidIndexException {
        if (pos < 0 || pos > last)
            throw new InvalidIndexException();

        if (last + 1 == items.length) {
            extendArray();
        }

        for (int i = last; i >= pos; i--)
            items[i + 1] = items[i];

        items[pos] = elem;

        return elem;
    }

    @Override
    public T remove(int pos) throws InvalidIndexException {
        if (pos < 0 || pos > last)
            throw new InvalidIndexException();

        T elem = items[pos];
        for (int i = pos + 1; i <= last; i++)
            items[i - 1] = items[i];

        last--;
        return elem;
    }

    @Override
    public T removeElem(T elem) throws ElemNotFoundException {
        for (int i = 0; i <= last; i++) {
            if (items[i].equals(elem)) {
                try {
                    return remove(i);
                } catch (InvalidIndexException e) {
                }
            }
        }
        throw new ElemNotFoundException();
    }

    @Override
    public T get(int pos) throws InvalidIndexException {
        if (pos < 0 || pos > last)
            throw new InvalidIndexException();

        return items[pos];
    }

    @Override
    public T set(int pos, T elem) throws InvalidIndexException {
        if (pos < 0 || pos > last)
            throw new InvalidIndexException();

        return items[pos] = elem;
    }

    @Override
    public String toString() {
        String ans = "[";

        for (int i = 0; i < last; i++)
            ans += items[i] + ", ";
        if (last >= 0)
            ans += items[last];

        return ans + "]";
    }
}
