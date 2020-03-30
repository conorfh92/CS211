package lab3_quicksort;
import java.util.*;

/*  Take a list of words as input
    Sort based on highest value ascii value
    Sort alphabetically if two words have the same highest value letter
 */

public class QuicksortLab {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        String [] wordList= new String[n];
    }


    public void recQuickSort(int left, int right) {

        if(right-left <= 0) // if size <= 1,
            return; // already sorted
        else{ // size is 2 or larger

            String pivot = array[right]; // rightmost item
            // partition range
            int partition = partitionIt(left, right, pivot);
            recQuickSort(left, partition-1); // sort left side
            recQuickSort(partition+1, right); // sort right side
        }
    }

    public int partitionIt(int left, int right, String pivot){

        int leftPtr = left-1; // left (after ++)
        int rightPtr = right; // right-1 (after --)
        while(true)
        {
            while( array[++leftPtr] < pivot ){} // scan to the right
            while(rightPtr > 0 && array[--rightPtr] > pivot){}// scan to the left

            if(leftPtr >= rightPtr) // if pointers cross, break
                break;
            else
                swap(leftPtr, rightPtr); //otherwise, swap elements
        }
        swap(leftPtr, right); // swap pivot into correct place
        return leftPtr; // return pivot location
    }

    public String medianOfThree(int left, int right,String [] array) {
        int center = (left+right)/2;
        // order left & center
        if( array[left] > array[center] )
            swap(left, center);
        // order left & right
        if( array[left] > array[right] )
            swap(left, right);
        // order center & right
        if( array[center] > array[right] )
            swap(center, right);
        swap(center, right-1); // put pivot on right
        return array[right-1]; // return median value
    }


}
