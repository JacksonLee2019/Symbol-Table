//Jackson Lee
import java.util.*;

public class SymbolTable {
    
    private class Node {
        private String key;
        private LinkedList<Integer> lines;//used for a list of line numbers on which the key was found
        private Node next;
        
        private Node(String k, int lineNum, Node x) {
            key = k;
            lines = new LinkedList<>();
            lines.addLast(lineNum);
            next = x;
        }
        
        private void addLineNum(int lineNum) {
            //if lineNum is not in the list of line numbers
            //add it to the end of the list of lineNums
            
            for(int i = 0; i < lines.size(); i++) {
                if(lineNum == lines.get(i)) {
                    return;
                }
            }
            
            lines.addLast(lineNum);
        }
    }
    
    private Node[] table;
    private int tableSize;
    
    public SymbolTable(int s) {
        //creates a symbol table with size s
        
        tableSize = s;
        table = new Node[tableSize];
        
        for(int i = 0; i < tableSize; i++) {
            table[i] = new Node(null, i, null);
        }
    }
    
    private int hash(String k) {
        return k.hashCode()%tableSize;
    }
    
    public boolean insert(String k, int lineNum) {
        //if k is not in the table add it to the table with lineNum as the 
        //first line number on which k is found and return true
        //if k is in the table, add lineNum to the list of line numbers 
        //and return false
        
        int code = Math.abs(hash(k));
        Node temp = table[code].next;
        
        if(temp == null) {
            table[code].next = new Node(k, lineNum, null);
            return true;
        }
        
        temp = table[code].next;
        
        while(temp != null) {
            if(k.compareTo(temp.key) == 0) {
                temp.addLineNum(lineNum);
                return false;
            }
            
            temp = temp.next;
        }
        
        temp = table[code].next;
        
        while(temp.next != null) {
            temp = temp.next;
        }
        
        temp.next = new Node(k, lineNum, null);
        
        return true;
    }
    
    public boolean find(String k) {
        //return true if k is in the table otherwise return false
        
        int code = Math.abs(hash(k));
        Node temp = table[code].next;
        
        while(temp != null) {
            if(temp != null && k.compareTo(temp.key) == 0) {
                return true;
            }
            
            temp = temp.next;
        }
        return false;
    }
    
    public LinkedList<Integer> getLines(String k) {
        //if k is in the table return the line numbers on which k is found
        //if k is not in the table return null
        
        int code = Math.abs(hash(k));
        Node temp = table[code].next;
        
        if(temp == null) {
            return null;
        }
        
        while(temp != null) {
            if(k.compareTo(temp.key) == 0) {
                return temp.lines;
            }
            temp = temp.next;
        }
        
        return null;
    }
    
    public class STIterator implements Iterator<String> {
        //An iterator that iterates through the keys in the table
        
        private ArrayList<Node> list = new ArrayList<>();
        
        public STIterator() {            
            for(int i = 0; i < table.length; i++) {
                Node temp = table[i].next;
                if(temp != null) {
                    while(temp != null) {
                        list.add(temp);
                        temp = temp.next;
                    }
                }
            }
        }
        
        public boolean hasNext() {
            return list.size() > 0;
        }
        
        public String next() {
            //PRE: hasNext()
            //returns a string containing the next key and the line numbers
            //associated with the key. The string includes a space between the
            //key and the fierst line number and between each pair of line numbers
            
            Node n = list.remove(0);
            
            return "Key:"+n.key+" Lines:"+n.lines;
        }
        
        public void remove() {
            //option method not implemented
        }
    }
    
    public boolean delete(String k) {
        //if k is in the table, delete the entry for k and return true
        //if k is not in the table, return false
        
        int code = Math.abs(hash(k));
        Node temp = table[code].next;
        
        if(temp == null) {
            return false;
        }
        
        Node temp1 = table[code];
        Node temp2 = table[code].next;
        
        while(temp1.next != null) {
            if(k.compareTo(temp2.key) == 0) {
                temp1.next = temp2.next;
                return true;
            }
            
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
           
        return false;
    }
    
    public Iterator<String> iterator() {
        //return a new iterator object
        STIterator i = new STIterator();
        return i;
    }
    
    public static void main(String[] args) {
        SymbolTable t = new SymbolTable(10);
        
        t.insert("Hi", 1);
        t.insert("Hi", 2);
        t.insert("Jackson", 3);
        t.insert("Is", 4);
        t.insert("Cool", 5);
        t.insert("Cool", 6);
        t.insert("Is", 7);
        t.insert("Jackson", 8);
        t.insert("Jake", 10);
        t.insert("Jc", 11);
        t.insert("Kb", 12);
        t.insert("La", 13);
        t.insert("La", 14);

        for(int i = 0; i < t.table.length; i++) {
            if(t.table[i].next != null) {
                Node temp = t.table[i].next;
                while(temp != null) {               
                    System.out.print("Key:"+temp.key+" Lines:"+temp.lines+" ");
                    temp = temp.next;
                }
                System.out.println();
            }
        }
        
        Iterator iter = t.iterator();
        
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }
        
        System.out.println(t.find("Hi"));
        System.out.println(t.find("Cool"));
        System.out.println(t.find("Not"));
        System.out.println(t.find("Jackson"));
        
        System.out.println(t.getLines("Jackson"));
        System.out.println(t.getLines("La"));
        System.out.println(t.getLines("Cool"));
        System.out.println(t.getLines("Not"));
    
        System.out.println(t.delete("La"));
        System.out.println(t.delete("Jc"));
        System.out.println(t.delete("Jackson"));
        System.out.println(t.delete("Jake"));
        
        for(int i = 0; i < t.table.length; i++) {
            if(t.table[i].next != null) {
                Node temp = t.table[i].next;
                while(temp != null) {               
                    System.out.print("Key:"+temp.key+" Lines:"+temp.lines+" ");
                    temp = temp.next;
                }
                System.out.println();
            }
        }
        
        iter = t.iterator();
        
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}