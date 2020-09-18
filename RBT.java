import java.util.*;
class Node {
	int data;
	Node parent,left,right; 
	int color; //1-red 0-black
}

class RBT {
	private Node root;
	private Node nod;

    public RBT() {
		nod = new Node();
		nod.color = 0;
		nod.left = null;
		nod.right = null;
		root = nod;
	}

	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != nod) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != nod) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	public void insert(int key) {
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = nod;
		node.right = nod;
		node.color = 1; 

		Node y = null;
		Node x = this.root;

		while (x != nod) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.data < y.data) {
			y.left = node;
		} else {
			y.right = node;
		}
		if (node.parent == null){
			node.color = 0;
			return;
		}		
		if (node.parent.parent == null) {
			return;
		}
		fixInsert(node);
	}
	
	void fixInsert(Node k){  //k-new node
		Node u;              //u-uncle node
		while (k.parent.color == 1) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; 
				if (u.color == 1) {
					System.out.println("Recoloring");
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				} else {
					if (k == k.parent.left) {
						k = k.parent;
						System.out.println("Right Rotate");
						rightRotate(k);
					}
					System.out.println("Recoloring");
					k.parent.color = 0;
					k.parent.parent.color = 1;
					System.out.println("Left Rotate");
					leftRotate(k.parent.parent);
				}
			} else {
				u = k.parent.parent.right; 

				if (u.color == 1) {
					System.out.println("Recoloring");
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						k = k.parent;
						System.out.println("Left Rotate");
						leftRotate(k);
					}
					System.out.println("Recoloring");
					k.parent.color = 0;
					k.parent.parent.color = 1;
					System.out.println("Right Rotate");
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = 0;
		
	}
public Node minimum(Node node) {
    while (node.left != nod) {
      node = node.left;
    }
    return node;
  }

  public Node maximum(Node node) {
    while (node.right != nod) {
      node = node.right;
    }
    return node;
    }
	
  private void printTree(Node root, String indent, boolean last) {
	   	if (root != nod) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }
            
           String nodeColor = root.color == 1?"RED":"BLACK";
		   System.out.println(root.data + "(" + nodeColor + ")");
		   printTree(root.left, indent, false);
		   printTree(root.right, indent, true);
		}
	}
 private void fixDelete(Node x) {
    Node s;
    while (x != root && x.color == 0) {
      if (x == x.parent.left) {
        s = x.parent.right;
        if (s.color == 1) {
          s.color = 0;
          x.parent.color = 1;
          leftRotate(x.parent);
          s = x.parent.right;
        }

        if (s.left.color == 0 && s.right.color == 0) {
          s.color = 1;
          x = x.parent;
        } else {
          if (s.right.color == 0) {
            s.left.color = 0;
            s.color = 1;
            rightRotate(s);
            s = x.parent.right;
          }

          s.color = x.parent.color;
          x.parent.color = 0;
          s.right.color = 0;
          leftRotate(x.parent);
          x = root;
        }
      } else {
        s = x.parent.left;
        if (s.color == 1) {
          s.color = 0;
          x.parent.color = 1;
          rightRotate(x.parent);
          s = x.parent.left;
        }

        if (s.right.color == 0 && s.right.color == 0) {
          s.color = 1;
          x = x.parent;
        } else {
          if (s.left.color == 0) {
            s.right.color = 0;
            s.color = 1;
            leftRotate(s);
            s = x.parent.left;
          }

          s.color = x.parent.color;
          x.parent.color = 0;
          s.left.color = 0;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
    x.color = 0;
  }

  private void rbTransplant(Node u, Node v) {
    if (u.parent == null) {
      root = v;
    } else if (u == u.parent.left) {
      u.parent.left = v;
    } else {
      u.parent.right = v;
    }
    v.parent = u.parent;
  }

  public void delete(Node node, int key) {
    Node z = nod;
    Node x, y;
    while (node != nod) {
      if (node.data == key) {
        z = node;
      }

      if (node.data <= key) {
        node = node.right;
      } else {
        node = node.left;
      }
    }

    if (z == nod) {
      System.out.println("Couldn't find key in the tree");
      return;
    }

    y = z;
    int yOriginalColor = y.color;
    if (z.left == nod) {
      x = z.right;
      rbTransplant(z, z.right);
    } else if (z.right == nod) {
      x = z.left;
      rbTransplant(z, z.left);
    } else {
      y = minimum(z.right);
      yOriginalColor = y.color;
      x = y.right;
      if (y.parent == z) {
        x.parent = y;
      } else {
        rbTransplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }

      rbTransplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }
    if (yOriginalColor == 0) {
      fixDelete(x);
    }
  }

 public static void main(String [] args){
    	RBT tree = new RBT();
        Scanner sc = new Scanner(System.in);
         
        while(true){
            System.out.println("\n1.insert\n2.Delete \n3.printtree\n4.Exit\nEnter the Choice:");
            int ch = sc.nextInt();
            if(ch==1){	
                System.out.print("Enter the value to insert: ");
                int inst=sc.nextInt();
                System.out.println("Before insert: ");
                tree.printTree(tree.root,"",true);
                tree.insert(inst);
                System.out.println("After insert: ");
                tree.printTree(tree.root,"",true);
            }else if(ch==2){	
                System.out.print("Enter the value to Delete: ");
                int delete=sc.nextInt();
                System.out.println("Before Delete: ");
                tree.printTree(tree.root,"",true);
                tree.delete(tree.root,delete);
                System.out.println("After Delete: ");
                tree.printTree(tree.root,"",true);
                }else if(ch == 3){
                System.out.println("RBT: ");
                tree.printTree(tree.root,"",true);
            }else if(ch == 4){
                System.out.print("Exit");
                break;
            }else{
                System.out.print("Enter your Choice:");
            }
        }
}
}
/*vasanth@vasanth-Lenovo-B490:~$ javac RBT.java
vasanth@vasanth-Lenovo-B490:~$ java RBT

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
1
Enter the value to insert: 3
Before insert: 
After insert: 
R----3(BLACK)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
1
Enter the value to insert: 4
Before insert: 
R----3(BLACK)
After insert: 
R----3(BLACK)
     R----4(RED)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
1
Enter the value to insert: 6
Before insert: 
R----3(BLACK)
     R----4(RED)
Recoloring
Left Rotate
After insert: 
R----4(BLACK)
     L----3(RED)
     R----6(RED)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
1
Enter the value to insert: 2
Before insert: 
R----4(BLACK)
     L----3(RED)
     R----6(RED)
Recoloring
After insert: 
R----4(BLACK)
     L----3(BLACK)
     |    L----2(RED)
     R----6(BLACK)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
1
Enter the value to insert: 1
Before insert: 
R----4(BLACK)
     L----3(BLACK)
     |    L----2(RED)
     R----6(BLACK)
Recoloring
Right Rotate
After insert: 
R----4(BLACK)
     L----2(BLACK)
     |    L----1(RED)
     |    R----3(RED)
     R----6(BLACK)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
2
Enter the value to Delete: 2
Before Delete: 
R----4(BLACK)
     L----2(BLACK)
     |    L----1(RED)
     |    R----3(RED)
     R----6(BLACK)
After Delete: 
R----4(BLACK)
     L----3(BLACK)
     |    L----1(RED)
     R----6(BLACK)

1.insert
2.Delete 
3.printtree
4.Exit
Enter the Choice:
*/


