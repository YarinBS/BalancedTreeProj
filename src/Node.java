class Node<T extends Comparable<?>>  {
    Node left, middle, right;
    T data;
    Node parent;
    Key key;
    Value value;


    public Node(){
    }

    public Node(Key key, Value value){
        this.key=key;
        this.value=value;
    }

    public Node(T data) {
        this.data = data;
    }



}


