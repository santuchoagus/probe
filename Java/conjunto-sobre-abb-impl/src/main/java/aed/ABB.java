package aed;
import java.util.LinkedList;
import java.util.PriorityQueue;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;

    private class Nodo {
        T obj;
        Nodo nodoIzq;
        Nodo nodoDer;

        public Nodo(T obj) {
            this.obj = obj;
            nodoIzq = null;
            nodoDer = null;
        }

        private void agregarseInorderQueue(LinkedList<T> queue) {
            if (this.nodoIzq != null)
                nodoIzq.agregarseInorderQueue(queue);

            queue.add(this.obj);

            if (this.nodoDer != null)
                nodoDer.agregarseInorderQueue(queue);
        }

        private boolean pertenece(T obj) {
            boolean result = false;
            if (this.obj.equals(obj)) {
                result = true;
            } else if (obj.compareTo(this.obj) < 0) {
                result = nodoIzq != null && nodoIzq.pertenece(obj);
            } else {
                result = nodoDer != null && nodoDer.pertenece(obj);
            }
            return result;
        }

        private void eliminarConCeroHijos(Nodo padre) {
            if (padre == null)
                raiz = null;
            else {
                if (this.obj.compareTo(padre.obj) < 0)
                    padre.nodoIzq = null;
                else
                    padre.nodoDer = null;
            }
        }

        private void eliminarConUnHijo(Nodo padre) {
            Nodo nuevoNodo = null;

            if (this.nodoIzq != null)
                nuevoNodo = nodoIzq;
            else
                nuevoNodo = nodoDer;

            if (padre == null)
                raiz = nuevoNodo;
            else {
                if (this.obj.compareTo(padre.obj) < 0)
                    padre.nodoIzq = nuevoNodo;
                else
                    padre.nodoDer = nuevoNodo;
            }
        }

        private void eliminarConDosHijos(Nodo padre) {
            // min{T2} (derecha) = nuevoNodo
            Nodo nuevoNodo = this.nodoDer._minimoRef();

            this.nodoDer.eliminar(nuevoNodo.obj, this);

            this.obj = nuevoNodo.obj;
        }

        private Nodo _minimoRef() {
            Nodo min = this;

            if (nodoIzq != null)
                min = nodoIzq._minimoRef();

            return min;
        }


        private void eliminar(T elem, Nodo padre) {
            int direccionElemento = elem.compareTo(obj);
            if (direccionElemento == 0) {
                // casos de 0, 1 y 2 hijos.
                if (this.nodoIzq == null && this.nodoDer == null)
                    this.eliminarConCeroHijos(padre);
                else if (nodoIzq == null)
                    this.eliminarConUnHijo(padre);
                else if (nodoDer == null)
                    this.eliminarConUnHijo(padre);
                else
                    eliminarConDosHijos(padre);

            } else if (direccionElemento < 0) {
                if (nodoIzq != null)
                    this.nodoIzq.eliminar(elem, this);
            } else {
                if (nodoDer != null)
                    this.nodoDer.eliminar(elem, this);
            }
        }



        private void insertar(T obj) {
            if (this.obj.equals(obj))
                return;

            if (obj.compareTo(this.obj) < 0) {
                if (nodoIzq != null)
                    nodoIzq.insertar(obj);
                else
                    nodoIzq = new Nodo(obj);
            } else {
                if (nodoDer != null)
                    nodoDer.insertar(obj);
                else
                    nodoDer = new Nodo(obj);
            }
        }

        private int cantidadDeDescendientes() {
            int numeroIzq = nodoIzq == null ? 0 : 1 + nodoIzq.cantidadDeDescendientes();
            int numeroDer = nodoDer == null ? 0 : 1 + nodoDer.cantidadDeDescendientes();
            return numeroIzq + numeroDer;
        }

        private T minimo() {
            T min = obj;

            if (nodoIzq != null)
                min = nodoIzq.minimo();

            return min;
        }

        private T maximo() {
            T max = obj;

            if (nodoDer != null)
                max = nodoDer.maximo();

            return max;
        }
    }

    public ABB() {
        this.raiz = null;
    }

    public int cardinal() {
        return raiz == null ? 0: 1 + raiz.cantidadDeDescendientes();
    }

    // El algoritmo asume que cardinal > 0 como precondicion.
    public T minimo(){
        return raiz.minimo();
    }

    public T maximo(){
        return raiz.maximo();
    }

    public void insertar(T elem){
        if (raiz == null) {
            raiz = new Nodo(elem);
        } else {
            raiz.insertar(elem);
        }
    }

    public boolean pertenece(T elem){
        if (raiz == null)
            return false;
        return raiz.pertenece(elem);
    }

    public void eliminar(T elem){
        if (raiz != null)
            raiz.eliminar(elem, null);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Iterador<T> iterator = new ABB_Iterador();

        while (iterator.haySiguiente()) {
            T valor = iterator.siguiente();
            sb.append(valor);

            if (iterator.haySiguiente())
                sb.append(",");
        }

        sb.append("}");

        return sb.toString();
    }

    private class ABB_Iterador implements Iterador<T> {
        private LinkedList<T> inOrderQueue;

        public ABB_Iterador() {
            inOrderQueue = new LinkedList<>();

            if (raiz != null) {
                raiz.agregarseInorderQueue(inOrderQueue);
            }
        }

        public boolean haySiguiente() {
            return inOrderQueue.peek() != null;
        }
    
        public T siguiente() {
            return inOrderQueue.removeFirst();
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
