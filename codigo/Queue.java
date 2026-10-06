package codigo;

public class Queue {
    private Node inicio;
    private Node fim;

    public Queue() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void enqueue(Node novo) {
        if (isEmpty()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
    }

    public Node dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node removido = inicio;
        inicio = inicio.getProximo();

        if (inicio == null) {
            fim = null;
        }
        removido.setProximo(null);
        return removido;
    }
}
