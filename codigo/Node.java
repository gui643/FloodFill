package codigo;

public class Node {
    private Position position;
    private Node proximo;

    public Node(Position position) {
        this.position = position;
        this.proximo = null;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }
}
