public class NodeKey implements Key, Value {

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

    @Override
    public Value createValueCopy() {
        return null;
    }

    @Override
    public void addValue(Value valueToAdd) {

    }
}
