

public class BalancedTree {

    Node root;

    public BalancedTree() { // Constructor
        Node x = new Node();
        this.root = x;
    }

    public void insert(Key newKey, Value newValue) {

        Key keyToInsert = newKey.createCopy();
        Value valueToInsert = newValue.createCopy();
        Node n = new Node(keyToInsert,valueToInsert);
        Insert23(n);


    }

    public void delete(Key key) {

    }

    public void Update_Key(Node x) {//@@@@
        x.key = x.left.key.createCopy();
        if (x.middle != null) {
            x.key = x.middle.key.createCopy();
        }
        if (x.right != null) {
            x.key = x.right.key.createCopy();
        }
    }

    public Node Search23(Node x, Key k) {
        if (x.left == null) {
            if (x.key.compareTo(k)==0) {
                return x;
            } else return null;
        }
        if (k.compareTo(x.left.key)==-1 ||k.compareTo(x.left.key)==0 ) {
            return Search23(x.left, k);
        } else if (k.compareTo(x.middle.key)==-1||k.compareTo(x.middle.key)==0) {
            return Search23(x.middle, k);
        } else {
            return Search23(x.right, k);
        }
    }

    public Value search(Key key) {
        Node n = Search23(this.root, key);
        return  n.value.createCopy();
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


    public void Insert23(Node z) {
        Node y = this.root;
        while (y.left!=null) { // while y not a leaf
            if (z.key.compareTo(y.key)==-1) { //z > y
                y = y.left;
            } else if (z.key.compareTo(y.middle.key)==-1) {// z<y
                y = y.middle;
            } else {
                y = y.right;
            }
        }
        Node x = y.parent;
        z = Insert_And_Split(x, z);
        while (x != this.root) {
            x = x.parent;
            if (z != null) {
                z = Insert_And_Split(x, z);
            } else {
                Update_Key(x);
            }
            if (z != null) {
                Node w = new Node();
                set_Children(w, x, z, null);
                this.root = w;
            }
        }
    }


    public Node Insert_And_Split(Node x, Node z) {
        Node l = x.left;
        Node m = x.middle;
        Node r = x.right;
        if (r == null) {
            if (z.key.compareTo(l.key)==-1) {
                set_Children(x, z, l, m);
            } else if (z.key.compareTo(m.key)==-1) {
                set_Children(x, l, z, m);
            } else {
                set_Children(x, l, m, z);
            }
            return null;
        }
        Node y = new Node();
        if (z.key.compareTo(l.key)==-1) {
            set_Children(x, z, l, null);
            set_Children(y, m, r, null);
        } else if (z.key.compareTo(m.key)==-1) {
            set_Children(x, l, z, null);
            set_Children(y, m, r, null);
        } else if (z.key.compareTo(r.key)==-1) {
            set_Children(x, l, m, null);
            set_Children(y, z, r, null);
        } else {
            set_Children(x, l, m, null);
            set_Children(y, r, z, null);
        }
        return y;
    }
}
