class Node<T extends Comparable<?>> {
    Node left, middle, right;
    T data;
    Node parent;
    Key key;
    Value value;
    String sentinel;

    public Node() {
    }

    public Node(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Node(T data) {
        this.data = data;
    }


    public int compareTo(Node rsNode) {//  A>B first bigger returns 1, A<B first smaller returns -1
        int ans = 0;
        if (rsNode.sentinel == null) {
            ans = this.key.compareTo(rsNode.key);
        }
        if (rsNode.sentinel == "-inf") {
            ans =1;
        }
        if (rsNode.sentinel == "inf") {
            ans =-1;
        }
        if (this.sentinel == "-inf") {
            ans =-1;
        }
        if (this.sentinel == "inf") {
            ans =1;
        }
        return ans;
    }

}


