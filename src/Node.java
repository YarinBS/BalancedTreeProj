class Node<T extends Comparable<?>> implements Value {
    Node left, middle, right;
    T data;
    Node parent;
    NodeKey k;
    Boolean isLeaf = false;


    public Node(){
    }

    public Node(Node l, Node m, Node r, Node p, int value) { // 3 sons
        this.left = l;
        this.middle = m;
        this.right = r;
        this.parent = p;
        this.k = new NodeKey(value);
    }

    public Node(Node l, Node m, Node p, int value) { // 2 sons
        this.left = l;
        this.middle = m;
        this.parent = p;
        this.k = new NodeKey(value);
    }

    public Node(T data) {
        this.data = data;
    }


    @Override
    public Value createValueCopy() {
        return null;
    }

    @Override
    public void addValue(Value valueToAdd) {

    }
}


