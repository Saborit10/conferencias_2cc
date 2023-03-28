public interface MyList<T> {
    public boolean isEmpty();

    public void clear();

    public int size();

    public T add(T elem);

    public T add(int pos, T elem) throws InvalidIndexException;

    public T remove(int pos) throws InvalidIndexException;

    public T removeElem(T elem) throws ElemNotFoundException;

    public T get(int pos) throws InvalidIndexException;

    public T set(int pos, T elem) throws InvalidIndexException;
}
