public class MyLinkedList<T> implements MyList<T> {
    private final Node<T> head;
    private Node<T> end;
    private int cant;


    public MyLinkedList() {
        this.cant = 0;
        head = new Node<>(null);
        end = head;
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    @Override
    public void clear() {
        head.setNext(null);
    }

    @Override
    public int size() {
        return cant;
    }

    @Override
    public T add(T elem) {
        end.setNext(new Node<>(elem));
        end = end.getNext();
        cant++;
        return elem;
    }

    @Override
    public T add(int pos, T elem) throws InvalidIndexException {
        return null;
    }

    @Override
    public T remove(int pos) throws InvalidIndexException {
        if (pos < 0 || pos >= cant)
            throw new InvalidIndexException();

        Node<T> cur = head.getNext();
        Node<T> ant = head;

        for (int i = 0; i < cant; i++) {
            if (pos == i) {
                T elem = cur.getValue();
                ant.setNext(cur.getNext());
                return elem;
            }

            cur = cur.getNext();
            ant = ant.getNext();
        }
        throw new InvalidIndexException();
    }

    @Override
    public T removeElem(T elem) throws ElemNotFoundException {
        return null;
    }

    @Override
    public T get(int pos) throws InvalidIndexException {
        return null;
    }

    @Override
    public T set(int pos, T elem) throws InvalidIndexException {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("[");

        Node<T> cur = head.getNext();
        for (int i = 0; i < cant; i++) {
            if (i < cant - 1)
                ans.append(cur.getValue()).append(",");
            else
                ans.append(cur.getValue());
            cur = cur.getNext();
        }
        ans.append("]");
        return ans.toString();
    }
}
