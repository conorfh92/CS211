import java.util.*;

public class HuffmanEncoding {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in)
        String input = sc.nextLine();               //Take String to be encoded
        sc.close();

        int[] asciiFrequency = new int[256];       //stores number of times each character occurs;
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            int asciiDecimal = (int) current;      //Cast character to int to get decimal ascii value
            asciiFrequency[asciiDecimal]++;
        }


        printFrequencies(asciiFrequency);//Print statement
        PriorityQueue<Tree> PQ = new PriorityQueue<>(); //make a priority queue to hold the forest of trees

        for (int i = 0; i < asciiFrequency.length; i++) {
            if (asciiFrequency[i] > 0) {

                Tree huffTree = new Tree();             //Create a Tree for each character
                huffTree.frequency = asciiFrequency[i]; //set the character frequency of that Tree
                Node huffNode = new Node();             //Create node for letter
                huffNode.letter = (char) i;
                huffNode.smallestLetter = (char) i;
                huffTree.root = huffNode;               //insert the huffNode as the root node
                PQ.add(huffTree);                       //add the Tree into the PQ
            }
        }


        while (PQ.size() > 1) { //while there are two or more Trees left in the forest


            //HUFFMAN ALGORITHM
            Tree mergeTree = new Tree();
            Node mergeNode = new Node();                               //Create default root node
            mergeTree.root = mergeNode;                                //Set default node
            mergeNode.smallestLetter = PQ.peek().root.smallestLetter;  //Update new root with smallest letter in tree
            mergeTree.frequency = PQ.peek().frequency;                 //Add frequency of left child
            mergeTree.root.leftChild = PQ.poll().root;                 //Take from PQ and set left child
            mergeTree.frequency += PQ.peek().frequency;                //Add frequency of right child
            mergeTree.root.rightChild = PQ.poll().root;                //Take from PQ and set right child

            //check smallestLetter value of rootNode is accurate
            if (mergeTree.root.leftChild.smallestLetter > mergeTree.root.rightChild.smallestLetter) {
                mergeTree.root.smallestLetter = mergeTree.root.rightChild.smallestLetter;
            }
            PQ.add(mergeTree);                            //Add mergeTree back to priority queue

        }
        Tree huffmanTree = PQ.poll();                     //Remove final and fully merged tree

        for (int i = 0; i < asciiFrequency.length; i++) { //Print encoded string
            if (asciiFrequency[i] > 0) {
                char c = (char) i;
                String huffCode = huffmanTree.getCode(c);
                System.out.println(c + " = " + huffCode);
            }
        }

        findCompressionRatio(huffmanTree, asciiFrequency);

    }

    public static void printFrequencies(int array[]){ //Print frequencies of characters
        for(int i=0;i<array.length;i++)
        if(array[i]!=0){
            char c = (char)i;
            System.out.println("'" + c + "'" + " occurs " + array[i] + " times");
        }
    }

    public static void findCompressionRatio(Tree huffmanTree, int[] frequency){ //Print compression ratio
        double asciiBits = 0;
        double huffmanBits=0;
        int huffCodeLength=0;

        for(int i=0; i<frequency.length; i++){
            if(frequency[i]>0){
                asciiBits += (frequency[i]*8);
                huffCodeLength=(huffmanTree.getCode((char)i)).length();
                huffmanBits += (huffCodeLength*frequency[i]);
            }
        }
        int compression=(int)Math.round((huffmanBits/asciiBits)*100);
        System.out.println("Before compression " + asciiBits + " bits");
        System.out.println("After compression " + huffmanBits + " bits");
        System.out.println("Achieved compression of " + compression + "%");
    }
}//End Main class

//--------------------------- Node and Tree classes below provided by lecturer ---------------------------------------
class Node {
    public char letter = '@'; //stores letter
    public char smallestLetter = '@'; //track smallest letter in tree
    public Node leftChild;
    public Node rightChild;
}

class Tree implements Comparable < Tree > {
    public Node root;
    public int frequency = 0; //holds overall frequency of characters

    public Tree()
    {
        root = null;
        frequency=0;
    }
    //CompareTo places tree with lowest ascii value character first in PQ if frequency values are equal.
    public int compareTo(Tree object) {
        if (frequency - object.frequency > 0) { //compare the cumulative frequencies of the tree
            return 1;
        } else if (frequency - object.frequency < 0) {
            return -1; //return 1 or -1 depending on whether these frequencies are bigger or smaller
        } else {
            // Sort based on letters
            char a = this.root.smallestLetter;
            char b = object.root.smallestLetter;

            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
            return 0;
        }
    }
    String path = "error"; //this variable will track the path to the letter we're looking for

    public String getCode(char letter) { //we want the code for this letter

        return this._getCode(letter, this.root, ""); //return the path that results
    }

    private String _getCode(char letter, Node current, String path) {
        if (current == null) {
            return null;
        }
        if (current.letter == letter) {
            return path;
        }

        String leftPath = this._getCode(letter, current.leftChild, path + "0");
        if (leftPath != null) {
            return leftPath;
        }

        String rightPath = this._getCode(letter, current.rightChild, path + "1");
        return rightPath;
    }

} // end Tree class