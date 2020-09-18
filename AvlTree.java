import java.util.Scanner;

class Node {
    int item, height;
    Node left, right;

    Node(int d) {
        item = d;
        height = 1;
    }
}

// Tree class
class AVLTree {
    Node root;

    int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // Get balance factor of a node
    int getBalanceFactor(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Insert a node
    Node insertNode(Node node, int item) {

        // Find the position and insert the node
        if (node == null)
            return (new Node(item));
        if (item < node.item)
            node.left = insertNode(node.left, item);
        else if (item > node.item)
            node.right = insertNode(node.right, item);
        else
            return node;


        node.height = 1 + max(height(node.left), height(node.right));
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (item < node.left.item) {
                System.out.println("\n Single Right Rotation and Balancing Factor is");
                return rightRotate(node);
            } else if (item > node.left.item) {
                System.out.println("\n Double Left-Right Rotation");
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (balanceFactor < -1) {
            if (item > node.right.item) {
                System.out.println("single Left Rotation");
                return leftRotate(node);
            } else if (item < node.right.item) {
                System.out.println("Double Right-Left Rotation ");
                node.left = rightRotate(node.left);
                return leftRotate(node);
            }
        }
        return node;
    }
    Node nodeWithMinimumValue(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }
    Node deleteNode(Node root, int item) {

        // Find the node to be deleted and remove it
        if (root == null)
            return root;
        if (item < root.item)
            root.left = deleteNode(root.left, item);
        else if (item > root.item)
            root.right = deleteNode(root.right, item);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;
                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                Node temp = nodeWithMinimumValue(root.right);
                root.item = temp.item;
                root.right = deleteNode(root.right, temp.item);
            }
        }
        if (root == null)
            return root;

        // Update the balance factor of each node and balance the tree
        root.height = max(height(root.left), height(root.right)) + 1;
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }
        return root;
    }


    void printTree(Node currPtr, String indent, boolean last) {
        if (currPtr != null) {
            System.out.print(indent);

            if (last) {

                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(currPtr.item);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        AVLTree tree = new AVLTree();
        System.out.println("AVL Tree Test\n");
        char ch;
        do {
            System.out.println("\nAvl Tree Operations\n");
            System.out.println("1. Insert ");
            System.out.println("2. Delete");
            System.out.println("3. Display");
            System.out.println("4. Height");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to insert:");
                    int item = scan.nextInt();
                    tree.printTree(tree.root, "", true);
                    System.out.println("After Inserted");
                    tree.root = tree.insertNode(tree.root, item);
                    tree.printTree(tree.root, "", true);


                    break;
                case 2:
                    System.out.println("Enter integer element to delete:");
                    int del = scan.nextInt();
                    tree.printTree(tree.root, "", true);
                    System.out.println("After Deletion:");
                    tree.root = tree.deleteNode(tree.root,del);
                    tree.printTree(tree.root, "", true);


                case 3:
                    System.out.println("Enter integer element to print");
                    tree.printTree(tree.root, "", true);

                case 4:

                    System.out.println("The Height of the tree is:"+ tree.height(tree.root));


            }
            System.out.print("Want to Continue(y/n):");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');
    }


}