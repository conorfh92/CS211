package lab4;



import java.util.Scanner;

public class ScrabbleAssistant {

    public static void main(String args[]) {

        FileIO reader = new FileIO();
        Scanner sc = new Scanner(System.in);


        String[] dict = reader.load("/Users/conorhanlon/Documents/dictionary.txt");    //Reading the File as a String array


        System.out.println("Enter your letters");       //Take in letters
        String letters = sc.nextLine();
        sc.close();

        String[] validWords = new String[10000];         //Holds valid words.
        int index = 0;                                    //Increment on valid words added to array

        for (int i = 0; i < dict.length; i++) {         //Check for valid words. Add to array
            if (isValidWord(dict[i], letters)) {
                validWords[index] = dict[i];
                index++;
            }
        }

        sortValidWords(validWords);

        for (int i = 0; i < validWords.length; i++) {
            if (validWords[i] != null)
                System.out.println(validWords[i] +" " + getValue(validWords[i]));
        }


        //Save results
         try {
             reader.save("/Users/conorhanlon/Documents/output.txt", validWords);
         } catch (Exception e) {
             System.out.println(e.getClass());
         }
    }

    public static boolean isValidWord(String test, String letters) {

        if (test.length() > letters.length()) {     //Return false for words longer than letters
            return false;
        }

        boolean[] foundLetters = new boolean[letters.length()];//Index true for letters already used.


        for (int i = 0; i < test.length(); i++) {                     //Cycle through test word

            boolean match = false;        //true if single letter matches, then reset.

            for (int j = 0; j < letters.length(); j++) {

                if ((test.charAt(i) == letters.charAt(j)) && (!foundLetters[j])) { //If match found and letter not used
                    foundLetters[j] = true;    //Set letter to used         df
                    //System.out.println("found letter");
                    match = true;
                    break;
                }

            }
            if (!match) {             //Return false if no match;
                // System.out.println("no match found for: " +test);
                return false;
            }
        }

        //System.out.println("Returning true");
        return true;
    }

    public static void sortValidWords(String[] wordArray) {

        int n = wordArray.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            if (wordArray[i] == null) {
                break;
            }
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (wordArray[j] == null) {
                    break;
                }
                if (getValue(wordArray[j]) > getValue(wordArray[minIndex])) {
                    minIndex = j;
                }
                if(getValue(wordArray[j])==getValue(wordArray[minIndex])){
                    if(wordArray[j].length()>wordArray[minIndex].length()){
                        minIndex=j;
                    }
                }
                // Swap the found minimum element with the first element
                String temp = wordArray[minIndex];
                wordArray[minIndex] = wordArray[i];
                wordArray[i] = temp;
            }
        }
    }

    public static int getValue(String word) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] alphabetValues =
                {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        int value = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);        //get character
            int j = alphabet.indexOf(c);    //get index of character
            //System.out.println("index of " + c + " is " + j);
            value += (alphabetValues[j]);     //Add corresponding value of character from array
        }
        //System.out.println("returning" + value + " for " + word);
        return value;
    }
}









