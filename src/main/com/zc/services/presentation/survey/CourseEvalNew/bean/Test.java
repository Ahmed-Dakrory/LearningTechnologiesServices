package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

public class Test {
	

		   public static void main(String args[]) {
		      int [] numbers = {10, 20, 30, 40, 50};
		      int [] numbersy = {60,70};
		      for(int y:numbersy ){
		      for(int x : numbers ) {
		         if( x == 30 ) {
		            continue;
		         }
		         System.out.println( x );
		         System.out.println( y );
		         System.out.print("\n");
		      }}
		   }
		}

