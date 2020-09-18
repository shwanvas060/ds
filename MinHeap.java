package heap;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Minheap {
    private static final int d = 2;
    private int heapSize;
    private int[] heap;

    public Minheap(int capacity) {
        heapSize = 0;
        heap = new int[capacity];
        Arrays.fill(heap, 0);

    }

    public boolean isEmpty() {
        return heapSize == 0;

    }

    public boolean isFull() {
        return heapSize == heap.length;
    }

    public void makeEmpty() {
        heapSize = 0;
    }

    private int parent(int i) {
        return (i - 1) / d;
    }

    private int kthChild(int i, int k) {
        return d * i + k;
    }


    public void insert(int x) {
        if (isFull( ) )

            throw new NoSuchElementException("Heap is filled \n Overflow Exception");

        heap[heapSize++] = x;
        heapifyup(heapSize - 1);
    }

    private void heapifyup(int i) {
        int tmp = heap[i];
        while (i > 0 && tmp < heap[parent(i)]) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = tmp;
    }

    public int findmin() {
        if(isEmpty())
            System.out.println("heap is empty");
        return  heap[0];
    }
    public int deleteMin()
    {
        int keyItem = heap[0];
        delete(0);
        return keyItem;
    }
    public int delete(int ind)
    {
        if (isEmpty() )
            System.out.println("heap is empty");

        int keyItem = heap[ind];
        heap[ind] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(ind);
        return keyItem;
    }

    private void heapifyDown(int ind) {
        int child;
        int tmp = heap[ ind ];
        while (kthChild(ind, 1) < heapSize)
        {
            child = minChild(ind);
            if (heap[child] < tmp)
                heap[ind] = heap[child];
            else
                break;
            ind = child;
        }
        heap[ind] = tmp;
    }

    private int minChild(int ind) {
        int bestChild  = kthChild(ind,1);
        int k = 2;
        int pos = kthChild(ind,k);
        while((k <= d) && (pos < heapSize))
        {
            if(heap[pos] < heap[bestChild])
                bestChild = pos;
            pos = kthChild(ind, k++);
        }
        return bestChild;
    }


    public void printHeap()
    {
        System.out.print("\nHeap = ");
        for (int i = 0; i < heapSize; i++)
            System.out.print(heap[i] +" ");
        System.out.println();
    }
}


public class MinHeap{

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Min Heap Test\n\n");
        System.out.println("Enter size of Min heap");

        Minheap mh = new Minheap(scan.nextInt() );

        char ch;

        do
        {
            System.out.println("\nBinary Heap Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. delete min");
            System.out.println("3. check full");
            System.out.println("4. check empty");
            System.out.println("5. clear");

            int choice = scan.nextInt();
            switch (choice)
            {
                case 1 :
                    try
                    {
                        System.out.println("Enter integer element to insert");
                        mh.insert( scan.nextInt() );
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage() );
                    }
                    break;
                case 2 :
                    try
                    {
                        System.out.println("Min Element : "+ mh.deleteMin());
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage() );
                    }
                    break;
                case 3 :
                    System.out.println("Full status = "+ mh.isFull());
                    break;
                case 4 :
                    System.out.println("Empty status = "+ mh.isEmpty());
                    break;
                case 5 :
                    mh.makeEmpty();
                    System.out.println("Heap Cleared\n");
                    break;
                default :
                    System.out.println("Wrong Entry \n ");
                    break;
            }

            mh.printHeap();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');
    }
}