/**
 * RSA class - low-level RSA operations
 * @author Jonathan Frederickson
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
	 */
	public static void main(String[] args) {
//		System.out.println(toLong("123456789", 0));
//		System.out.println(isPrime(4));
		
		Random rand = new Random();
		System.out.println(randPrime(60, 500, rand));
		System.out.println(relPrime(50, rand));
		long testLong = (0xFF);
		//System.out.println("With shifting: " + Long.toBinaryString(testLong >> Long.size(testLong)-8));
//		System.out.println("As byte: " + (byte) (testLong >> 58));
		//System.out.println(Long.toBinaryString());
//		System.out.println(Long.toBinaryString(testLong));
//		System.out.println(testLong);
		try {
//			System.out.println(longTo2Chars(testLong));
		}
		catch(Exception e) {
			
		}
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
		return result;
	}
	
	/**
	 * Prints the given array of long ints on standard out
	 * @param cipher
	 */
	public static void show(long[] cipher) {
		System.out.println(Arrays.toString(cipher));
	}
	
	/**
	 * 
	 * @param msg
	 * @param p
	 * @return
	 */
	public static long toLong(java.lang.String msg, int p) {
		String resultString;
		char[] msgArray = msg.toCharArray();
		long result;
		
		result = msgArray[0] & (msgArray[1] << 16);
		System.out.println(Long.toBinaryString((long) msgArray[0]));
		System.out.println(msgArray[1]);
		System.out.println(Long.toBinaryString(result));
		
		return result;
	}
	
	/**
	 * Converts a long int to two characters
	 * @param x
	 * @return
	 */
	public static String longTo2Chars(long x) throws UnsupportedEncodingException {
		byte[] chars = new byte[2];
		
		System.out.println(Long.toBinaryString(x << 56));
		
		long mask1 = 0x00000000000000ffL;
		long mask2 = 0x000000000000ff00L;
//		
//		System.out.println("Input: " + Long.toBinaryString(x));
		chars[0] = (byte) (x & mask2);
		System.out.println("Mask 1: " + (x & mask1));
//		chars[1] = (byte) (x >> 8);
//		
//		System.out.println("Array 1: " + chars[0]);
//		System.out.println("Array 2: " + chars[1]);
//		return new String(chars, "UTF-8");
		return null;
	}
	
	/*
	 * Check if parameter is prime
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
}
