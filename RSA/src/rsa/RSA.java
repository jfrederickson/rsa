/**
 * RSA class - low-level RSA operations
 * @author Jonathan Frederickson
 * @author Justin Lok
 */

package rsa;
import java.security.*;

import javax.crypto.*;

import java.util.Arrays;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.lang.Math.*;
import java.lang.IndexOutOfBoundsException;

public class RSA {
	
	
	/**
	 * Main method - testing project
	 * TODO: Clean up all the commented stuff
	 */
	public static void main(String[] args) {
//		System.out.println(toLong("123456789", 0));
//		System.out.println(isPrime(4));
		
		Random rand = new Random();
		System.out.println(randPrime(60, 500, rand));
		System.out.println(relPrime(50, rand));
		int testCharNum = (0x00410042);
		//System.out.println("With shifting: " + Long.toBinaryString(testLong >> Long.size(testLong)-8));
//		System.out.println("As byte: " + (byte) (testLong >> 58));
		//System.out.println(Long.toBinaryString());
//		System.out.println(Long.toBinaryString(testLong));
//		System.out.println(testLong);
		System.out.println(to2Chars(testCharNum));
		System.out.println(Integer.toBinaryString(toInt("ABC", 0)));
//		toLong("AB", 0);
		
	}
	
	/**
	 * Generates a random prime number between m and n
	 * FIXME: Currently loops forever if none exist
	 * @param m lower bound
	 * @param n upper bound
	 * @param rand a random number generator (java.util.Random)
	 * @return a random prime number between m and n
	 */
	public static long randPrime(int m, int n, Random rand) {
		boolean found = false;
		int result = 0;
		while(!found) {
			int tmp;
			tmp = rand.nextInt(n-m);
			if(isPrime(tmp+m)) {
				found = true;
				result = tmp+m;
			}
		}
		return result;
	}
	
	/**
	 * Finds a random number relatively prime to a given long int
	 * FIXME: Currently loops forever if none exist
	 * @param n
	 * @param rand a random number generator (java.util.Random)
	 * @return a random number relatively prime to n
	 */
	public static long relPrime(long n, Random rand) {
		boolean found = false;
		long result = 0;
		while(!found) {
			long tmp;
			tmp = rand.nextLong() % n; // Modulo n to make sure it's below our number
			if(gcd(n, tmp) == 1) {
				result = tmp;
				found = true;
			}
		}
		return Math.abs(result);
	}
	
	/**
	 * Prints the given array of long ints on standard out
	 * @param cipher
	 */
	public static void show(long[] cipher) {
		System.out.println(Arrays.toString(cipher));
	}
	
	/**
	 * Convert two chars to int in UTF-16 format
	 * @param msg
	 * @param p
	 * @return the two digit number beginning at position p of msg as an int
	 */
	public static int toInt(java.lang.String msg, int p) {
		char[] msgArray = msg.toCharArray();
		int result;
		
		if(p >= 0 && p < msg.length()-1) {
			result = (msgArray[p] << 16) | (msgArray[p+1]);
		}
		else {
			// Pad with zeroes if there is no second character
			result = (msgArray[p] << 16);
		}
		
		return result;
	}
	
	/**
	 * Converts an int (two characters in UTF-16 format) into a string
	 * @param x
	 * @return the string represented by x - two characters in UTF-16 format
	 */
	public static String to2Chars(int x) {
		byte[] chars = new byte[2];
		int mask1 = 0xffff0000;
		int mask2 = 0x0000ffff;
		
		char char1 = (char) ((x & mask1) >> 16);
		char char2 = (char) (x & mask2);
		return "" + char1 + char2;
	}
	
	/*
	 * Check if parameter is prime
	 * Uses a brute force method - iterates through 2..sqrt(l)
	 */
	private static boolean isPrime(long l) {
		if(l == 2) return true;
		for(int i=2; i<Math.sqrt(l)+1; i++) {
			if(l % i == 0) return false;
		}
		return true;
	}
	
	/*
	 * Euclidean algorithm - greatest common divisor
	 */
	private static long gcd(long a, long b) {
		if(b == 0) return a;
		else return gcd(b, a%b);
	}
	
	/**
	 * Find the multiplicative inverse of a long int.
	 * 
	 * @param e - base number
	 * @param m - modulo
	 * @return inverse - the multiplicative inverse of e
	 */
	public static long inverse(long e, long m) {
		if(findGCD(e,m) != 1) {
			// No inverse exists
			return -1;
		}
		
		long base = m, temp, quotient;
		long remainder = m;
		long u = 0;
		long inverse = 1;
		
		while (e > 1) {
			quotient = e / remainder;
			temp = remainder;
			remainder = e % remainder;
			e = temp;
			
			temp = u; 
			u = inverse - quotient * u;
			inverse = temp;
		}
		if (inverse < 0) {
			inverse += base;
		}
		return inverse;
	}
	
	/**
	 * Used for the inverse method.
	 * Returns the greatest common denominator to test if an inverse exists
	 * 
	 * @param a and b are positive
	 * @return the gcd of a and b
	 */
	private static long findGCD (long a, long b) {

		long gcd = 0;
		long remainder = 0;

		a = Math.abs (a);
		b = Math.abs (b);

		while (true) {
			if (b == 0) {
				gcd = a;
				break;
			}
			else {
				remainder = a % b;
				a = b;
				b = remainder;
			}
		}
		return gcd;
	}
	
	/**
	 * Raise a number, b, to a power, p, modulo m.
	 * 
	 * @param b - base number
	 * @param p - power
	 * @param m - modulo
	 * @return result - the result of ((b^p)(mod m))
	 */
	public static long modPower(long b, long p, long m) {
		long result = 1;
		
		// Simplify each modulus
		for(int i = 1; i <= p; i++) {
			result = (result * b) % m;
		}
		
		return result;
	}



	/**
	 * Driver to test RSA-Part 2 Methods
	 */
//	public static void main(java.lang.String[] args) {
//		//RSA a = new RSA();
//		
//		long num = 5;
//		long mod = 26;
//		long exp = 1243535325; // modPower can be improved
//		long inverse = RSA.inverse(num,mod);
//		
//		if(inverse != -1) {
//			System.out.println("The inverse of " + num + " (mod " + mod + ") is: " + inverse);		
//		}
//		else
//			System.out.println("No inverse exists.");
//		
//		long power = RSA.modPower(num,exp,mod);
//		System.out.println(num + " to the power of " + exp + " is: " + power + " (mod " + mod + ")");
//	}
}
