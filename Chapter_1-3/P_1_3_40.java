// Read a sequence of cahracters from standard input into a 
// linked list with no duplicates.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class P_1_3_40 {

    private static class Node {
        char c;
        Node next;
        Node prev;
        
        public Node(char c) {
            this.c = c;
        }
    }

    public static void main(String[] args) {
        Node first = null;
        Node last = null;
        Map<Character, Node> map = new HashMap<>();

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader bufferReader = new BufferedReader(reader);

        try {
            int r;
            while ((r = bufferReader.read()) != -1) {
                char c = (char) r;
                if (c == '\n' || c == '\r') {
                    continue;
                }
                if (map.containsKey(c)) {
                    Node node = map.get(c);

                    // remove the existing node from the linked list
                    if (node.prev != null) {
                        node.prev.next = node.next;
                    } else {
                        first = node.next;
                    }
                    if (node.next != null) {
                        node.next.prev = node.prev;
                    } else {
                        last = node.prev;
                    }
                    //  move new node to the front
                    node.next = first;
                    node.prev = null;
                    if (first != null) {
                        first.prev = node;
                    }
                    first = node;

                } else {
                    // insert the new node at the front
                    Node newNode = new Node(c);

                    newNode.next = first;
                    if (first != null) {
                        first.prev = newNode;
                    }
                    first = newNode;

                    if (last == null) {
                        last = newNode;
                    }
                    // add to the map
                    map.put(c, newNode);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Node current = first; current != null; current = current.next) {
            System.out.print(current.c + " ");
        }
        System.out.println();
    }
}
