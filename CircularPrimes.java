//Find all circular primes in range

import java.util.*;

public class CircularPrimes {
    public static void main(String args[] ) throws Exception {
    

        Scanner myscanner = new Scanner(System.in);
        System.out.println("Enter lower bounds");
        int lower= myscanner.nextInt();				//Take input for lower bounds
        System.out.println("Enter upper bounds");
        int upper = myscanner.nextInt();			//Take input for upper bounds
        myscanner.close();

        int counter=0;								//Stores count of circular primes

        for(int i=Math.max(lower,2);i<=upper;i++){	//Iterates through numbers to check.

            boolean isprime=true;
            
            for(int j=2;j<=Math.sqrt(i);j++){		//Checks if numbers are prime.
                if(i%j==0){

                    isprime=false;
                    break;
                }
            }

            if(isprime){
              if(rotate(i))		//Call rotate method to check if a prime is circular
                counter++;		//If rotate() returns true, update counter
            }
        }
        
        //Print results
        if(counter=1) {
        	System.out.println("There is "+ counter + " circular prime in the range " + lower +"-" + upper);
        }
        else {
        System.out.println("There are "+ counter + " circular primes in the range " + lower +"-" + upper);
        }
    }

        public static boolean rotate(int num)
        {
            String numl=Integer.toString(num);
            int numLength=numl.length();					//get length of number in digits
            boolean isCircle=true;
            int multiplier = (int)Math.pow(10,numLength-1); //Get a power of ten multiplier to keep length consistent
            int numTest=(num%10*multiplier)+(num/10);		//rotate: take last number, multiply to return length
            												//add remaining digits
            
            
            //Test loop while the rotated number has not rotated back to its original
            while(numTest!=num)								
            {												
                 
                for (int i = 2; i < Math.sqrt(numTest); i++) //Check test number is prime
                  if (numTest % i == 0){
                    isCircle= false; 
                    break; 														
                  }
                
            numTest=(numTest%10*multiplier)+(numTest/10);	//Perform another rotation
            }
            return isCircle;
        }
}