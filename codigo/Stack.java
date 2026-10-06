package codigo;

public class Stack {
    private Node topo;

    public Stack() {
        this.topo = null;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public void Push(Node novo) {
        novo.setProximo(topo);
        topo = novo;
    }

    public Node pop() {
        if (isEmpty()) {
            return null;
        }
        Node removido = topo;
        topo = topo.getProximo();
        removido.setProximo(null);
        return removido;

    }
}
