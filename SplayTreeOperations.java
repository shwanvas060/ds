package codeprac;
import java.util.Scanner;
class SplayNode{
    SplayNode left, right, parent;
    int element;
    public SplayNode(){
        this(0,null,null,null);
    }




    public SplayNode(int ele, SplayNode left, SplayNode  right, SplayNode parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = ele;
    }
}
class SplayTree {
    SplayNode root;
    int count = 0;

    public SplayTree() {
        root = null;
    }



    public void insert(int ele) {
        SplayNode z = root;
        SplayNode p = null;
        while (z != null) {
            p = z;
            if (ele > p.element)
                z = z.right;
            else
                z = z.left;

        }
        z = new SplayNode();
        z.element = ele;
        z.parent = p;
        if (p == null)
            root = z;
        else if (ele > p.element)
            p.right = z;
        else
            p.left = z;
        Splay(z);
        count++;
    }

    public void LeftChildParent(SplayNode c, SplayNode p) {


        if (p.parent != null) {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /**
     * rotate
     **/
    public void RightChildParent(SplayNode c, SplayNode p) {

        if (p.parent != null) {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    private void Splay(SplayNode x) {
        root = x;
        while (x.parent != null) {
            SplayNode Parent = x.parent;
            SplayNode GrandParent = Parent.parent;
            if (GrandParent == null) {
                if (x == Parent.left){
                    System.out.println("zig rotations");

                    LeftChildParent(x, Parent);
            }else {
                    System.out.println("zag rotations");

                    RightChildParent(x, Parent);
                }

            } else {
                if (x == Parent.left) {
                    if (Parent == GrandParent.left) {
                        System.out.println("zig-zig rotations");
                        LeftChildParent(Parent, GrandParent);
                        LeftChildParent(x, Parent);
                    } else {
                        System.out.println("zig-zag rotations");
                        LeftChildParent(x, x.parent);
                        RightChildParent(x, x.parent);

                    }
                } else {
                    if (Parent == GrandParent.left) {
                        System.out.println("zag-zig rotations");
                        RightChildParent(x, x.parent);
                        LeftChildParent(x, x.parent);
                    } else {
                        System.out.println("zag-zag rotations");
                        RightChildParent(Parent, GrandParent);
                        RightChildParent(x, Parent);

                    }
                }
            }
        }
    }

    private SplayNode findNode(int ele) {
        SplayNode PrevNode = null;
        SplayNode z = root;
        while (z != null) {
            PrevNode = z;
            if (ele > z.element)
                z = z.right;
            else if (ele < z.element)
                z = z.left;
            else {
                Splay(z);
                return z;
            }

        }
        if (PrevNode != null) {
            Splay(PrevNode);
            return null;
        }
        return null;
    }
    public void delete(int ele)
    {
        SplayNode node = findNode(ele);
        delete(node);
    }
    private void delete(SplayNode node)
    {
        if (node == null)
            return;
        Splay(node);
        if((node.left != null) && (node.right !=null))
        {
            SplayNode min = node.left;
            while(min.right!=null)
                min = min.right;
            min.right =node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if(node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        }
        else if(node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else{
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        count--;
    }



    public void display(SplayNode root, String indent, boolean last) {
        if(root != null){
            System.out.print(indent);
            if(last){
                System.out.print("R----");
                indent += "  ";

            }else{
                System.out.print("L----");
                indent += "|    ";
            }
            System.out.println(root.element);
            display(root.left, indent,true);
            display(root.right, indent, false);
        }

    }
}
public class SplayTreeOperations {
    public static void main(String[] acts){
        Scanner scan = new Scanner(System.in);
        SplayTree st = new SplayTree();
        System.out.println("Splay Tree Test");
        char ch;
        String op = "\nSplay Tree Operations\n1.Insert\n2.Display\n3.Delete";
        do {

            System.out.println(op);
            System.out.print("Enter the choice:");
            int choice = scan.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter integer Element to insert");
                    int insert = scan.nextInt();
                    System.out.println("before insert");
                    st.display(st.root, "", true);
                    st.insert(insert);
                    System.out.println("After Insert");
                    st.display(st.root, "", true);
                }
                case 2 -> {
                    System.out.println("Enter integer Element to display");
                    st.display(st.root, "", true);
                }
                case 3 -> {
                    System.out.println("Enter integer Element to delete:");
                    int delete= scan.nextInt();
                    System.out.println("before insert");
                    st.display(st.root, "", true);

                    st.delete(delete);
                    System.out.println("After insert");
                    st.display(st.root, "", true);
                }
                default -> System.out.println("Wrong Entry \n");
            }
            System.out.println("\nTo continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');
    }
}

