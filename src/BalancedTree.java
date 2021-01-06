

public class BalancedTree {

    Node root;

    public BalancedTree() { // Constructor
        Node x = new Node();
        Node l = new Node();
        Node m = new Node();
        l.sentinel="-inf";
        m.sentinel="inf";
        l.parent = x;
        m.parent = x;
//        l.key = this.root.key.createCopy();
//        m.key = this.root.key.createCopy();
        x.left = l;
        x.middle = m;
        this.root = x;
    }

    public void insert(Key newKey, Value newValue) {

        Key keyToInsert = newKey.createCopy();
        Value valueToInsert = newValue.createCopy();
        Node n = new Node(keyToInsert, valueToInsert);
        if (this.root.key == null) {
            this.root.key=newKey.createCopy();
            this.root.value=newValue.createCopy();
            this.root.left.key = newKey.createCopy();
            this.root.middle.key = newKey.createCopy();

        } else Insert23(n);


    }


    public void delete(Key key) {
        Node x = Search23(this.root, key);
        if (x == null) {
            return;
        }
        if (x == this.root) {
            this.root = null;
            return;
        }
        Node y = x.parent;
        if (x == y.left) {
            set_Children(y, y.middle, y.right, null);
        } else if (x == y.middle) {
            set_Children(y, y.left, y.right, null);
        } else if (x == y.right) {
            set_Children(y, y.left, y.middle, null);
        }
        x = null;
        while (y != null) {
            if (y.middle == null) {
                if (y != this.root) {
                    y = Borrow_Or_Merge(y);
                } else {
                    this.root = y.left;
                    y.left.parent = null;
                    y = null;
                    return;
                }
            } else {
                Update_Key(y);
                y = y.parent;
            }
        }
    }

    public void Update_Key(Node x) {//@@@@
        x.size = x.left.size;
        x.key = x.left.key.createCopy();
        if (x.middle != null) {
            x.size += x.middle.size;
            if(x.middle.sentinel!=null) x.sentinel =x.middle.sentinel;
            else x.sentinel=null;
            x.key = x.middle.key.createCopy();
        }
        if (x.right != null) {
            x.size += x.right.size;
            if(x.right.sentinel!=null) x.sentinel =x.right.sentinel;
            else x.sentinel=null;
            x.key = x.right.key.createCopy();
        }
    }

    public Node Search23(Node x, Key k) {
        Node temp = new Node();
        temp.key = k;
        if (x.left == null) {
            if (x.compareTo(temp) == 0) {
                return x;
            } else return null;
        }
        if (temp.compareTo(x.left) == -1 || temp.compareTo(x.left) == 0) {
            return Search23(x.left, k.createCopy());
        } else if (temp.compareTo(x.middle) == -1 || temp.compareTo(x.middle) == 0) {
            return Search23(x.middle, k.createCopy());
        } else {
            return Search23(x.right, k.createCopy());
        }
    }

    public Value search(Key key) {
        Node n = Search23(this.root, key);
        if (n == null) {
            return null;
        }
        return n.value.createCopy();
    }

    public Node searchNode(Key key) {
        Node n = Search23(this.root, key);
        return n;
    }

    public int rank(Key key) {
        Node chosen = Search23(this.root, key);
        if (chosen == null) {
            return 0;
        }
        Node y = chosen.parent;
        int rank = 1;
        while (y != null) {
            if (chosen == y.middle) {
                rank = rank + y.left.size;
            }
            else if( chosen == y.right){
                rank = rank + y.left.size + y.middle.size;
            }
            chosen = y;
            y = y.parent;
        }
        return rank;
    }

    public Node select(int index) { // FIX THIS - returns a pointer to the object with the i'th smallest key, if exists.
        return Select_Rec(this.root, index);
    }

    public Node Select_Rec(Node x, int i){
        if (x.size < i) {
            return null;
        }
        if (x.left == null) {
            return x;
        }
        int s_left = x.left.size;
        int s_left_middle = x.middle.size + x.left.size;
        if (i <= s_left) {
            return Select_Rec(x.left, i);
        } else if (i <= s_left_middle) {
            return Select_Rec(x.middle, i - s_left);
        } else {
            return Select_Rec(x.right, i - s_left_middle);
        }
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

    public Value sumValuesInInterval(Key key1, Key key2) { // FIX THIS - returns the sum of keys that are smaller or equal to a given value

        int temp= Sum_Of_Smaller_Rec(this.root , key1) - Sum_Of_Smaller_Rec(this.root ,key2);
        return temp''
    }

    public int Sum_Of_Smaller_Rec(Node x , Key key){
        int k=0;
        if (x.left == null) {
            if (x.sentinel != "-inf") {
                if (x.size <= k) {
                    return x.size;
                } else {
                    return 0;
                }
            } else {
                return 36606;
            }
        }
        if (k <= x.left.size) {
            return Sum_Of_Smaller_Rec(x.left, k);
        } else if (k <- x.middle.size) {
            return x.left.sum + Sum_Of_Smaller_Rec(x.middle, k); // Add 'sum' to Node
        } else {
            return x.left.sum + x.middle.sum + Sum_Of_Smaller_Rec(x.right, k);
        }
    }


public void Insert23(Node z) {
    Node y = this.root;
    while (y.left != null) { // while y not a leaf
        if (z.compareTo(y.left)==-1) { //z < y.left

            y = y.left;

        } else if (z.compareTo(y.middle)==-1) {// z<y.mid
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
            if (z.compareTo(l) == -1) {
                set_Children(x, z, l, m);
            } else if (z.compareTo(m) == -1) {
                set_Children(x, l, z, m);
            } else {
                set_Children(x, l, m, z);
            }
            return null;
        }
        Node y = new Node();
        if (z.compareTo(l) == -1) {
            set_Children(x, z, l, null);
            set_Children(y, m, r, null);
        } else if (z.compareTo(m) == -1) {
            set_Children(x, l, z, null);
            set_Children(y, m, r, null);
        } else if (z.compareTo(r) == -1) {
            set_Children(x, l, m, null);
            set_Children(y, z, r, null);
        } else {
            set_Children(x, l, m, null);
            set_Children(y, r, z, null);
        }
        return y;
    }

    public Node Borrow_Or_Merge(Node y) {
        Node z = y.parent;
        if (y == z.left) {
            Node x = z.middle;
            if (x.right != null) {
                set_Children(y, y.left, x.left, null);
                set_Children(x, x.middle, x.right, null);
            } else {
                set_Children(x, y.left, x.left, x.middle);
                y = null;
                set_Children(z, x, z.right, null);
            }
            return z;
        }
        if (y == z.middle) {
            Node x = z.left;
            if (x.right != null) {
                set_Children(y, x.right, y.left, null);
                set_Children(x, x.left, x.middle, null);
            } else {
                set_Children(x, x.left, x.middle, y.left);
                y = null;
                set_Children(z, x, z.right, null);
            }
            return z;
        }
        Node x = z.middle;
        if (x.right != null) {
            set_Children(y, x.right, y.left, null);
            set_Children(x, x.left, x.middle, null);
        } else {
            set_Children(x, x.left, x.middle, y.left);
            y = null;
            set_Children(z, z.left, x, null);
        }
        return z;
    }


    public void delete23(Node x) {
        Node y = x.parent;
        if (x == y.left) {
            set_Children(y, y.middle, y.right, null);
        } else if (x == y.middle) {
            set_Children(y, y.left, y.right, null);
        } else set_Children(y, y.left, y.middle, null);
        x = null;
        while (y != null) {
            if (y.middle == null) {
                if (y != this.root) {
                    y = Borrow_Or_Merge(y);
                } else {
                    this.root = y.left;
                }
                y.left.parent = null;
                y = null;
                return;
            } else Update_Key(y);
            y = y.parent;
        }

    }

}
