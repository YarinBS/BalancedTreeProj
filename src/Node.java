class Node<T extends Comparable<?>> {
    Node left, middle, right;
    T data;
    Node parent;
    Key key;
    Value value;
    String sentinel;
    int size;

    public Node() {
        this.size=0;
    }

    public Node(Key key, Value value) {
        this.key = key;
        this.value = value;
        this.size=1;
    }

    public Node(T data) {
        this.data = data;
        this.size=1;
    }


    public int compareTo(Node rsNode) {//  A>B first bigger returns 1, A<B first smaller returns -1
        int ans = 0;
        if (rsNode.sentinel == null && this.sentinel == null) {
            ans = this.key.compareTo(rsNode.key);
        } else if (rsNode.sentinel == "-inf") { // B= -inf
            ans = 1;
        } else if (rsNode.sentinel == "inf") {
            ans = -1;
        } else if (this.sentinel == "-inf") {
            ans = -1;
        } else if (this.sentinel == "inf") {
            ans = 1;
        }
        return ans;
    }

}


