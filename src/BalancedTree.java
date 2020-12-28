

public class BalancedTree {

    Node root;


    public BalancedTree() { // Constructor
            Node x =new Node();
            Node l = new Leaf();
            Node m = new Leaf();
            l.k= new NodeKey(Integer.MIN_VALUE);
            m.k = new NodeKey(Integer.MAX_VALUE);
            l.parent= x;
            m.parent= x;
            x.k = new NodeKey(Integer.MAX_VALUE);
            x.left= l;
            x.middle = m;
            this.root= x;
    }
//
//    public void delete(Key key) {
//
//    }
//
//    public Value search(Key key){
//
//    }
//
//    public int rank(Key key){
//
//    }
//
//    public Key select(int index){
//
//    }
//
//    public Value sumValuesInInterval(Key key1, Key key2){
//
//    }
}
