public class Main {
    public static void main(String[] args) throws Exception {

        MyList<Integer> a = new MyArrayList<>(5);

        a.add(5);
        a.add(6);
        a.add(7);
        a.add(8);
        a.add(9);
        a.add(10);
        a.removeElem(10);
        a.removeElem(6);
        a.removeElem(5);
        a.removeElem(7);
        a.remove(0);
        a.remove(0);
//        a.remove(2);


        System.out.println(a);
        System.out.println("Size: " + a.size());
        System.out.println("Is Empty: " + (a.isEmpty() ? "Yes" : "No"));


    }
}