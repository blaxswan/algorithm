public class TreeNode{
    private int val;
    public TreeNode left, right;

    public TreeNode(final int val){
        this.val = val;
        this.left = this.right = null;
    }

    public int value(){
        return this.val;
    }
}