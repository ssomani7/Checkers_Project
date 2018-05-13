package ooad.saurabh.fileReading;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		return hmap1;		
	}//end of method matrix
	
	
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
	}//end of method getCoordinates
	
	
	public static void displayFromTo(List<String> movesToBeSimulated) {
		for(String ptr : movesToBeSimulated) {
			System.out.println("Moves to be Simulated --> " + ptr);
		}		
	}//end of method displayFromTo
	
	
	public static void boardCoordinates(List<String> movesToBeSimulated) {
		String[] boardFromToValues = new String[movesToBeSimulated.size()];
		boardFromToValues          = movesToBeSimulated.toArray(boardFromToValues);
		
		for(String ptr:boardFromToValues) {
			System.out.println("boardFromToValues --> " + ptr);
		}
		System.out.println("----------------------------------");
		splitter(boardFromToValues);
	}//end of method boardCoordinates
	
	
	public static void splitter(String[] boardFromToValues) {
		int fromRow, fromCol;
		String[] temp = boardFromToValues[0].split(":");
		
		for(String ptr:temp) {
			System.out.println("Splitter --> " + ptr);
			fromRow = getCoordinates(ptr, 0);
			System.out.println("row = " + fromRow);
			fromCol = getCoordinates(ptr, 1);
			System.out.println("col = " + fromCol);
		}		
	}//end of method splitter
	
	
	public static void main(String[] args) {
		Map<String, Integer> hmap2 = matrix();
		List<String> movesToBeSimulated = new ArrayList<String>();
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
			
			String tempFromTo = leftKey + ':' + rightKey;
			movesToBeSimulated.add(tempFromTo);
		}		
		sc.close();
		displayFromTo(movesToBeSimulated);
		System.out.println("----------------------------------");
		boardCoordinates(movesToBeSimulated);
	}//end of main method

}//end of class Test
