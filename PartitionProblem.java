/*-----------------------------------------
Partition problem lab
Take an input of ints and return and return an array of '+' or '-'
which represent the operation which should be done to each number in the input array.
 ------------------------------------------*/

import java.util.*;

public class PartitionProblem{

    public static void main (String[] args){
        Scanner myscanner = new Scanner(System.in);
        int items = myscanner.nextInt();        //Set number of items in input
        int[] contents = new int[items];        //Create array for input
        for(int i=0;i<items;i++){
             contents[i]=myscanner.nextInt();
         }

        char[] solution = solve(contents);  //Call method to create char array of +/-
        long subset1=0;                     //Holds value of subset 1
        long subset2=0;                     //Holds value of subset 2


        //Section below hard coded as part of lab assignment.

        for(int i=0;i<items;i++){           //Decipher +/- array and reform subsets
            if(solution[i]=='-'){
                subset1+=contents[i];
            }else{
                subset2+=contents[i];
            }
        }
        System.out.println(Math.abs(subset1-subset2)); //Print final difference.
    }

    public static char[] solve(int[] contents){

        char solution[] = new char[contents.length];
        int [] subset1 = new int [contents.length];
        int [] subset2 = new int [contents.length];

        //Sort contents so that highest values come first
        int n = contents.length;
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (contents[j] > contents[min_idx])
                    min_idx = j;
            int temp = contents[min_idx];
            contents[min_idx] = contents[i];
            contents[i] = temp;
        }//END OF SORTING

        long sub1total=0;   //record subtotal values
        long sub2total=0;

        for (int i=0;i<contents.length;i++) //Handle partitioning
        {
            if(sub1total==sub2total)        //If sum of subset 1 is equal to sum of subset 2
            {
                subset1[i]=contents[i];     //Add value to subset 1
                sub1total+=contents[i];     //Update total
            }
            else if(sub1total>sub2total)    //If sum of subset 1 is greater than  sum of subset 2
            {
                subset2[i]=contents[i];     //Add value to subset 2
                sub2total+=contents[i];     //Update total
            }
            else if (sub2total>sub1total)   //If sum of subset 1 is less than  sum of subset 2
            {
                subset1[i]=contents[i];     //Add value to subset 1
                sub1total+=contents[i];     //Update total
            }
        }

        //Write subsets to contents
        int index=0;
        for(int i=0;i<subset1.length;i++)//Write subset 1 contents from beginning of contents array
        {

            if(subset1[i]!=0)
            {
                contents[index]=subset1[i];
                solution[index]='+';
                index++;                //Only update index on finding '+'
            }
        }
        for(int i=0;i<subset2.length;i++)//Write subset 2 into remainder of contents array
        {
            if(subset2[i]!=0)
            {
                contents[index]=subset2[i];
                solution[index]='-';
                index++;                //Only update index on finding '-'
            }
        }
        return solution; //Return array of +/-
    }


}