package courses.basics_strong.funcprogramming.section15.datastructures;

@SuppressWarnings("rawtypes")
public class TreeFun<E extends Comparable<E>> {

    // The root node
    private final E value;
    private final TreeFun<E> left;
    private final TreeFun<E> right;
    public int size;
    public int height;

    private static final TreeFun NIL = new TreeFun();



    @SuppressWarnings("unchecked")
    private TreeFun() {
        value = null;
        left = NIL;
        right = NIL;
        size = 0;
        height = -1;
    }

    private TreeFun(TreeFun<E> left, E value, TreeFun<E> right) {
        this.left = left;
        this.value = value;
        this.right = right;
        // size is left size + right size +1 for the root node
        this.size = 1 + this.left.size + this.right.size;
        // height = the max height value from left and right height + 1 for the root node
        this.height = 1 + Math.max(this.left.height, this.right.height);
    }

    @SafeVarargs
    public static <E extends Comparable<E>> TreeFun<E> tree(E...es) {
        TreeFun<E> tree = NIL;
        for (int i = 0; i < es.length; i++) {
            tree = tree.insert(es[i]);
        }

        return tree;
    }

    @SuppressWarnings("unchecked")
    private TreeFun<E> insert(E newValue) {
        // if the tree is empty
        if (isEmpty()) {
            // create a root with left and right NIL
            return new TreeFun<>(NIL, newValue, NIL);
        }
        // else if the tree is not empty
        else {
            // if the new value is lower than current value
            if (newValue.compareTo(this.value) < 0) {
                // we create a new node and put the new value to the left
                return new TreeFun<>(left.insert(newValue), this.value, this.right);
            }
            // else if the new value is greater
            else if ( newValue.compareTo(this.value) > 0) {
                // we create a new node and put the new value to the right
                return new TreeFun<>(this.left, this.value, right.insert(newValue));
            } else {
                // else means are similar and update this value as node
                return new TreeFun<>(this.left, newValue, this.right);
            }
        }
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        return this.value != null ? String.format(" %s %s %s ", left, value, right) : "";
    }

    public TreeFun<E> remove(E element){
        // if element want to remove is lower than the current value
        if(element.compareTo(this.value) < 0) {
            // we remove it from the left
            return new TreeFun<>(this.left.remove(element), this.value, this.right);
        }
        // else if element want to remove is greater than the current value
        else if (element.compareTo(this.value) > 0) {
            // we remove it from the right
            return new TreeFun<>(this.left, this.value, this.right.remove(element));
        }
        // else means they are similar
        else {
            //when node is having zero or one child
            if(this.left.isEmpty())
                return this.right;
            else if(this.right.isEmpty())
                return this.left;
                //have two child nodes-need to find the node which can replace root
                //either replace it by max node value from left subtree or smallest node from right subtree
            else {
                E max = min(this.right);
                return new TreeFun<>(this.left,max,this.right);
            }
        }
    }

    //function to find minimum node value
    private E min(TreeFun<E> tr) {
        E e = null;
        while(tr.left!=null) {
            e = tr.value;
            tr = tr.right;
        }
        return e;
    }

    public boolean isMember(E value) {
        // if value is NOT null
        if(this.value != null) {
            // if the value received is lower than current tree value
            if(value.compareTo(this.value) < 0) {
                // recurse on left to check if the value is member of
                return left.isMember(value);
            }
            // else if the value received is greater than current tree value
            else if(value.compareTo(this.value) > 0) {
                // recurse on right to check if the value is member of
                return right.isMember(value);
            }
            // else return false
            else {
                return true;
            }
        }
        // else since the value is null, return false
        else {
            return false;
        }
    }

    public E max() {
        // if right is NULL
        // return value
        // else recurs on right max
        return this.right.equals(NIL) ?  this.value : this.right.max();
    }

    public E min() {
        // if left is NULL
        // return value
        // else recurs on left min
        return this.left.equals(NIL) ?  this.value : this.left.min();
    }
}