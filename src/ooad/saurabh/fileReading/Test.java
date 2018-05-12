package ooad.saurabh.fileReading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Test {

	public static Map<String, Integer> matrix(){
		Map<String, Integer> hmap1 = new LinkedHashMap<String, Integer>();
		int count = 0;
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				String temp1 = row + "-" + col;
				if(row % 2 == 0) { //even number of rows
					if(col % 2 == 0) { //even number of columns
						count = count + 1;
						hmap1.put(temp1, count);
					} else { //odd number of columns
						hmap1.put(temp1, 0);
					}
				} else { // odd number of rows
					if(col % 2 == 0) {
						hmap1.put(temp1, 0);
					} else {
						count = count + 1;
						hmap1.put(temp1, count);
					}
				}
			}// inner for-loop for columns
		}// outer for-loop for rows
		
//		for (HashMap.Entry<String, Integer> entry : hmap1.entrySet()) {
//		    System.out.println(entry.getKey()+" : "+entry.getValue());
//		}
		return hmap1;		
	}
	
	public static String getKeyByValue(Map<String, Integer> hmap2, Integer value) {
        for (Entry<String, Integer> entry : hmap2.entrySet()) {

            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
	}
	
	public static int getCoordinates(String key, int position) {
		String[] arrOfStr = key.split("-");	
		return Integer.parseInt(arrOfStr[position]);
	}
	
	public static void main(String[] args) {
		Map<String, Integer> hmap2 = matrix();
	    File file = new File("/Users/saurabhsomani/Desktop/checkersInput.txt");
	    Scanner sc = null;
		try {
		   sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(sc.hasNext()) { //prints line of inputFile.
			String tempStr   = sc.next();
			System.out.println("----------------------------------");
			System.out.println(tempStr);
			
			int x_coordinate = getCoordinates(tempStr, 0);
			int y_coordinate = getCoordinates(tempStr, 1);
			
			String leftKey   = getKeyByValue(hmap2, x_coordinate);			
//			System.out.println("From = " + leftKey);
			
			int fromRow = getCoordinates(leftKey, 0);
			System.out.println("From Row    = " + fromRow);
			
			int fromCol = getCoordinates(leftKey, 1);		
			System.out.println("From Column = " + fromCol);
			
			System.out.println("----------------------------------");
			
			String rightKey   = getKeyByValue(hmap2, y_coordinate);
//			System.out.println("To = " + rightKey);
			
			int toRow = getCoordinates(rightKey, 0);
			System.out.println("To Row    = " + toRow);
			
			int toCol = getCoordinates(rightKey, 1);		
			System.out.println("To Column = " + toCol);
			System.out.println("----------------------------------");
		}
		sc.close();		
	}//end of main method

}//end of class Test
