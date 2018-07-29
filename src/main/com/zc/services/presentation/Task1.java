package main.com.zc.services.presentation;

import java.util.ArrayList;
import java.util.Scanner;

import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;

public class Task1 {
	  
	    public static void main(String[] args) {
	        // TODO code application logic here
	        
	        Scanner input = new Scanner(System.in);
	        
	        ArrayList<String> city = new ArrayList<String>();
	               
	        city.add("MEDIUM_DESC");
	        city.add("Alexandria");
	        city.add("Assiut");
	        city.add("Aswan");
	        city.add("Al Beheira");
	        city.add("Beni Suef");
	        city.add("Cairo");
	        city.add("Al Daqahliyya");
	        city.add("Damietta");
	        city.add("Fayoum");
	        city.add("Al Gharbiyah");
	        city.add("Al Giza");
	        city.add("Ismailiya");
	        city.add("Kafr El Sheikh");
	        city.add("Luxor");
	        city.add("Al Minya");
	        city.add("Marsa Matrouh");
	        city.add("Al Monufiyya");
	        city.add("North Sinai");
	        city.add("New Valley");
	        city.add("Other Country");
	        city.add("Port Said");
	        city.add("Qena");
	        city.add("Qalubiya");
	        city.add("Red Sea");
	        city.add("Sohag");
	        city.add("Al Sharqiyah");
	        city.add("South Sinai");
	        city.add("Suez");
	        
	        System.out.println("Egypt's governerrates:");
	        System.out.println(city.size());
	        System.out.println("");
	        
	        java.util.Iterator<String> cityIterator = city.iterator();
	        while (cityIterator.hasNext()) {
	            System.out.println(cityIterator.next());
	        }
	        
	        System.out.println("");
	        System.out.println("Enter your city name:");

	        String cityName = input.next();
	        
	        if (city.contains(cityName)) {
	            System.out.println("City found");
	        } else {
	            System.out.println("City not found");
	        }
	       
	    }
}
