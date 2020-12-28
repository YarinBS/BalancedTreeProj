

public class BalancedTree {

    Node root;

    public BalancedTree() { // Constructor
        Node x = new Node();
        Node l = new Leaf();
        Node m = new Leaf();
        l.k = new NodeKey(Integer.MIN_VALUE);
        m.k = new NodeKey(Integer.MAX_VALUE);
        l.parent = x;
        m.parent = x;
        x.k = new NodeKey(Integer.MAX_VALUE);
        x.left = l;
        x.middle = m;
        this.root = x;
    }

    public void insert(Key newKey, Value newValue) {

    }

    public void delete(Key key) {

    }

    public void Update_Key(Node x) {
        x.k = x.left.k;
        if (x.middle != null) {
            x.k = x.middle.k;
        }
        if (x.right != null) {
            x.k = x.right.k;
        }
    }

    public Node Search23(Node x, NodeKey k) {
        if (x.isLeaf) {
            if (x.k.value == k.value) {
                return x;
            } else return null;
        }
        if (k.value <= x.left.k.value) {
            return Search23(x.left, k);
        } else if (k.value <= x.middle.k.value) {
            return Search23(x.middle, k);
        } else {
            return Search23(x.right, k);
        }
    }

    public Value search(NodeKey key) {
        return Search23(this.root, key);
    }

    public int rank(Key key) {

    }

    public Key select(int index) {

    }

    public void set_Children(Node x, Node l, Node m, Node r) {
        x.left = l;
        x.middle = m;
        x.right = r;
        l.parent = x;
        if (m != null) {
            m.parent = x;
        }
        if (r != null) {
            r.parent = x;
        }
        Update_Key(x);
    }

    public Value sumValuesInInterval(Key key1, Key key2) {

    }

    public Node Insert_And_Split(Node x, Node z) {
        Node l = x.left;
        Node m = x.middle;
        Node r = x.right;
        if (r == null) {
            if (z.k.value < l.k.value) {
                set_Children(x, z, l, m);
            } else if (z.k.value < m.k.value) {
                set_Children(x, l, z, m);
            } else {
                set_Children(x, l, m, z);
            }
            return null;
        }
        Node y = new Node();
        if (z.k.value < l.k.value) {
            set_Children(x, z, l, null);
            set_Children(y, m, r, null);
        } else if (z.k.value < m.k.value) {
            set_Children(x, l, z, null);
            set_Children(y, m, r, null);
        } else if (z.k.value < r.k.value) {
            set_Children(x, l, m, null);
            set_Children(y, z, r, null);
        } else {
            set_Children(y, r, z, null);
        }
        return y;
    }
}
