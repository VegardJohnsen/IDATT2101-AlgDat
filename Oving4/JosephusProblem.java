/**
 * Josephus problem is a math puzzle with a grim description: n prisoners are standing on a circle, sequentially numbered from 0 to n−1.
 * An executioner walks along the circle, starting from prisoner 0, removing every k-th prisoner and killing him.
 * As the process goes on, the circle becomes smaller and smaller, until only one prisoner remains, who is then freed.
 * Given any n and k, find out which prisoner will be the last one alive in the end.
 */
public class JosephusProblem {

    static class Node {
        public int data ;
        public Node next;
        public Node( int data )
        {
            this.data = data;
        }
    }

    /**
     * getJosephusPosition finds the position of the last survivor in the circle.
     *
     * @param m int - the number of people to skip before removing the next person
     * @param n int - the number of people in the circle
     */
    static void getJosephusPosition(int m, int n) {
        Node head = new Node(0);
        Node prev = head;
        for(int i = 1; i < n; i++) {
            prev.next = new Node(i);
            prev = prev.next;
        }

        // Connect last node to first
        prev.next = head;


        Node ptr1 = head;
        Node ptr2 = head;

        while(ptr1.next != ptr1) {

            // Find m-th node
            int count = 1;
            while(count != m) {
                ptr2 = ptr1;
                ptr1 = ptr1.next;
                count++;
            }

            /* Remove the m-th node */
            ptr2.next = ptr1.next;
            ptr1 = ptr2.next;
        }
        System.out.println("Last person left standing is " + ptr1.data);
    }


    public static void main(String[] args) {
        // Change the values to test the program
        // n is the number of people in the circle
        int n = 6;
        // k is the number of people to skip before removing the next person
        int k = 4;
        getJosephusPosition(k, n);
    }
}
