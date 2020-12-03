/**
 * 
 */
package main.com.zc;

import java.util.StringJoiner;

/**
 * @author omnya
 *
 */
public class Test1 {
public static void main(String[]args)
{
	String stringArray[] = {"Hello", " how", " are", " you", " welcome", " to", " Tutorialspoint"};
    StringJoiner joiner = new StringJoiner("");
    for(int i = 0; i < stringArray.length; i++) {
       joiner.add(stringArray[i]);
    }
    String str = joiner.toString();
    System.out.println(str);
    String[] ary = str.split(" ");
    for(int i=0;i<ary.length;i++) {
    	System.out.println(ary[i]);
    }
    
	//Test.getInstance();
}
}
