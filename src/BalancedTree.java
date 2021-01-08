public class BalancedTree {

    Node root;

    public BalancedTree() { // Constructor
        Node x = new Node();
        Node l = new Node();
        Node m = new Node();
        l.sentinel = "-inf";
        m.sentinel = "inf";
        x.sentinel = "inf";
        l.parent = x;
        m.parent = x;
        x.left = l;
        x.middle = m;
        this.root = x;
    }

    public void insert(Key newKey, Value newValue) {
        Key keyToInsert = newKey.createCopy();
        Value valueToInsert = newValue.createCopy();
        Node n = new Node(keyToInsert, valueToInsert);
        Insert23(n);

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
        if (x.left.sentinel != null) {
            x.sentinel = x.left.sentinel;
            x.key = null;
        } else {
            x.sentinel = null;
            x.key = x.left.key.createCopy();
        }
        if (x.middle != null) {
            x.size += x.middle.size;
            if (x.middle.sentinel != null) {
                x.sentinel = x.middle.sentinel;
                x.key = null;
            } else {
                x.sentinel = null;
                x.key = x.middle.key.createCopy();
            }
        }
        if (x.right != null) {
            x.size += x.right.size;
            if (x.right.sentinel != null) {
                x.sentinel = x.right.sentinel;
                x.key = null;
            } else {
                x.sentinel = null;
                x.key = x.right.key.createCopy();
            }
        }
        UpdateSum(x);
    }

    public void UpdateSum(Node x) {
        x.sum = null;
        if (x.left.sum != null) {
            x.sum = x.left.sum.createCopy();
            if (x.middle != null&&x.middle.sum!=null) x.sum.addValue(x.middle.sum.createCopy());
            if (x.right != null && x.right.sum != null) x.sum.addValue(x.right.sum.createCopy());
        } else {
            if (x.middle != null&&x.middle.sum!=null) x.sum = x.middle.sum.createCopy();
            if (x.right != null && x.right.sum != null) x.sum.addValue(x.right.sum.createCopy());
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
            } else if (chosen == y.right) {
                rank = rank + y.left.size + y.middle.size;
            }
            chosen = y;
            y = y.parent;
        }
        return rank;
    }

    public Key select(int index) {
        Node x = Select_Rec(this.root, index);
        if (x == null || x.sentinel == "-inf") return null;
        return x.key.createCopy();
    }

    public Node Select_Rec(Node x, int i) {
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

    public void Sum_Of_Current_parent_bigger(Node P, Node trvs, Value currentSum) { // fix
        if (P.left == trvs) {
            if (P.middle.sum != null) {
                currentSum.addValue(P.middle.sum.createCopy());
                if (P.right != null && P.right.sum != null)
                    currentSum.addValue(P.right.sum.createCopy());
            }
        } else if (P.middle == trvs) {
            if (P.right != null && P.right.sum != null)
                currentSum.addValue(P.right.sum.createCopy());
        }
    }

    public void Sum_Of_Current_parent_smaller(Node P, Node trvs, Value currentSum) {
        if (P.right != null && P.right == trvs) {
            currentSum.addValue(P.middle.sum.createCopy());
            currentSum.addValue(P.left.sum.createCopy());
        } else if (P.middle == trvs)
            currentSum.addValue(P.left.sum.createCopy());
    }


    public Value sumValuesInInterval(Key key1, Key key2) {
        if (this.root == null) {
            return null;
        }
        if (key2.compareTo(key1) < 0) {
            return null;
        }
        if (key2.compareTo(key1) == 0) {
            return search(key2);
        }
        Node src = Search23(this.root, key1.createCopy());
        if (src == null) src = Successor23(key1.createCopy());
        if (src.sentinel == "inf") return null;
        Node des = Search23(this.root, key2.createCopy());
        if (des == null) des = Predessor23(key2.createCopy());
        if (src == des) return src.value.createCopy();
        Node trvs = src;
        Node P = trvs.parent;
        Value sum = src.value.createCopy();
        while (P.compareTo(des) < 0) {//P<des
            Sum_Of_Current_parent_bigger(P, trvs, sum);
            trvs = P;
            P = P.parent;
        }
       Node trvs2 = des;
        sum.addValue(des.value.createCopy());
        Node P1 = trvs2.parent;

        while (P1 != P ) {
            Sum_Of_Current_parent_smaller(P1, trvs2, sum);
            trvs2 = P1;
            P1 = P1.parent;
        }
        if(P1.right==trvs2&&P1.left==trvs)
            sum.addValue(P1.middle.sum.createCopy());

        return sum;
    }


    public Node Successor23(Key k) {
        Node tmp = new Node();
        tmp.key = k.createCopy();
        Node x = root;
        if (x == null) return null;
        while (x != null) {
            if (x.left == null) return x; // a leaf
            if (tmp.compareTo(x.left) < 0) {
                x = x.left;
            } else if (x.middle != null && tmp.compareTo(x.middle) < 0) {
                x = x.middle;
            } else x = x.right;
        }
        return null;
    }

    public Node TreeMax(Node X) {
        while (X.left != null) {
            if (X.right != null) X = X.right;
            else X = X.middle;
        }
        Node P = X.parent;
        if (P.right == null) return P.left;
        else return P.middle;
    }

    public Node TreeMin(Node X) {
        while (X.left != null) {
            X = X.left;
        }
        Node P = X.parent;
        return P.middle;
    }

    public Node Predessor23(Key k) {
        Node tmp = Successor23(k);
        if (tmp.sentinel == "inf") return TreeMax(this.root);
        int num = rank(tmp.key);
        if (num == 1) return TreeMin(this.root);
        Key right = select(num - 1);
        return Search23(this.root, right);

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


    public void Insert23(Node z) {
        Node y = this.root;
        while (y.left != null) { // while y not a leaf
            if (z.compareTo(y.left) == -1) { //z < y.left

                y = y.left;

            } else if (z.compareTo(y.middle) == -1) {// z<y.mid
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
        }
        if (z != null) {
            Node w = new Node();
            set_Children(w, x, z, null);
            this.root = w;
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

}
