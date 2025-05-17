import java.util.Iterator;

public class IntSeq {
   public static void main(String[] args) {
        ListInts ls = new ListInts();

        ls.agregarAtras(3);
        ls.agregarAtras(-7);
        ls.agregarAtras(13);
        ls.agregarAdelante(44);
        ls.agregarAtras(2);
        // Deberia ser el -7.
        System.out.printf("indice: %d, valor %d\n", 0, ls.obtener(2));
        
        int longitud = ls.longitud();
        for (int i = 0; i < longitud; i++) {
            System.out.printf(" %d", ls.obtener(i));
            if (i != longitud - 1) { 
                System.out.print(",");
            } else {
                System.out.print("\n");
            }
        }

        // con iterators 
        for (Integer v : ls) {
            System.out.printf("%d, ", v);
        }
        System.out.print("\n");
   } 
}

interface SecuenciaDeInts {
    public int longitud();
    public void agregarAdelante(int elem);
    public void agregarAtras(int elem);
    // T es por si es null
    public int obtener(int indice);
    public void eliminar(int indice);
}


class ListInts implements SecuenciaDeInts, Iterable<Integer> {
    private Nodo primero;
    private int longitud;

    private class Nodo {
        int valor;
        Nodo sig;
    }


    class ListIntsIterador implements Iterator<Integer> {
        Nodo actual;

        ListIntsIterador() {
            this.actual = ListInts.this.primero;
        }

        public boolean hasNext() {
            return actual != null;
        }
        
        public Integer next() {
            Integer valor = this.actual.valor;
            this.actual = this.actual.sig;
            return valor;
        }
    }

    ListInts() {
        this.primero = null;
    }

    public Iterator<Integer> iterator() {
        return new ListIntsIterador();
    }

    public void agregarAdelante(int elem) {
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.valor = elem;
        nuevoNodo.sig = this.primero;
        this.primero = nuevoNodo;
    }

    public void agregarAtras(int elem) {
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.valor = elem;

        if (this.primero == null) {
            this.primero = nuevoNodo;
        } else {
            Nodo nodoActual = this.primero;
            
            while (nodoActual.sig != null) {
                nodoActual = nodoActual.sig;
            }

            nodoActual.sig = nuevoNodo;
        }
    }

    // Precondicion es que 0 <= i < |s|
    public void eliminar(int i) {
        Nodo actual = this.primero;
        Nodo prev = this.primero;

        for (int j = 0; j < i; j++) {
            prev = actual;
            actual = actual.sig;
        }

        if (i == 0) {
            this.primero = actual.sig;
        } else {
            prev.sig = actual.sig;
        }
    }

    // 0 <= i < |s|
    public int obtener(int i) {
        Nodo actual = this.primero;
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
        }

        return actual.valor;
    }

    public int longitud() {
        int contador = 0;
        Nodo actual = this.primero;

        while(actual != null) {
            actual = actual.sig;
            contador++;
        }

        return contador;
    }
}
