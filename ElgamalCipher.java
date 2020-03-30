package lab_6
import java.util.*;

public class ElGamalCipher {
    public static void main (String args[]){
        //Take input
        Scanner sc=new Scanner(System.in);
        //Split input into string arrays
        String [] first= sc.nextLine().split(" ");
        String [] second= sc.nextLine().split(" ");


        //Set up
        long p = Long.parseLong(first[0]);      //Prime from public key
        long g = Long.parseLong(first[1]);      //Generator from public key
        long gxmp = Long.parseLong(first[2]);   //generator by x mod p result
        long c1 = Long.parseLong(second[0]);    //C1 from cipher
        long c2 = Long.parseLong(second[1]);    //C2 from cipher
        long key=0;                             //Declare key

        //Iterate through values of x until a value equal to gxmp found
        for(int x=0;x<p;x++){
            if(modPow(g, x, p)==gxmp){
                //System.out.println("Key is " + x);
                key=x;  //Set key to value of x
                break;
            }
        }
        //Decrypt using the key
        long c1Result = modPow(c1, p - 1 - key, p); //Get 1/c1^x
        long c2Result = modMult(c1Result, c2, p);   //c1Result * c2 mod p
        System.out.println(c2Result);               //Print the decrypted message

    }

    public static long modPow(long number, long power, long modulus){
//raises a number to a power with the given modulus
//when raising a number to a power, the number quickly becomes too large to handle
//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
//however you want the algorithm to work quickly - having a multiplication loop would result in an O(n) algorithm!
//the trick is to use recursion - keep breaking the problem down into smaller pieces and use the modMult method to join them back together
        if(power==0)
            return 1;
        else if (power%2==0) {
            long halfpower=modPow(number, power/2, modulus);
            return modMult(halfpower,halfpower,modulus);
        }else{
            long halfpower=modPow(number, power/2, modulus);
            long firstbit = modMult(halfpower,halfpower,modulus);
            return modMult(firstbit,number,modulus);
        }
    }

    public static long modMult(long first, long second, long modulus){
//multiplies the first number by the second number with the given modulus
//a long can have a maximum of 19 digits. Therefore, if you're multiplying two ten digits numbers the usual way, things will go wrong
//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
//however you want the algorithm to work quickly - having an addition loop would result in an O(n) algorithm!
//the trick is to use recursion - keep breaking down the multiplication into smaller pieces and mod each of the pieces individually
        if(second==0)
            return 0;
        else if (second%2==0) {
            long half=modMult(first, second/2, modulus);
            return (half+half)%modulus;
        }else{
            long half=modMult(first, second/2, modulus);
            return (half+half+first)%modulus;
        }
    }
}