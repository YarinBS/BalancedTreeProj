public class NodeKey implements Key {

    int value;
    public NodeKey(int n){
        this.value=n;
    }


    @Override
    public Key createCopy() {
        return null;
    }

    @Override
    public int compareTo(Key o) {
        return 0;
    }
}
