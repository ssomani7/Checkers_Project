package ooad.saurabh.trialVersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class ReadFromFile {
	
// Able to get String[] object withoutConstructor	
//	ReadFromFile(){
//		readInput(); /* Generates an ArrayList of String ('fromRow-fromCol:toRow-toCol')
//		 			  * and passes it as an argument to method boardCoordinates. This method
//		 			  * returns a String[] object. 
//		 			  */		
//	}//end of constructor
	
	public Map<String, Integer> matrix(){
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
	
	
	public String[] readInput() {
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
			String tempStr    = sc.next();			
			int leftValue     = getCoordinates(tempStr, 0);
			int rightValue    = getCoordinates(tempStr, 1);			
			String leftKey    = getKeyByValue(hmap2, leftValue);						
			String rightKey   = getKeyByValue(hmap2, rightValue);			
			String tempFromTo = leftKey + ':' + rightKey;
			movesToBeSimulated.add(tempFromTo);
		}		
		sc.close();
		String[] boardFromToValues = boardCoordinates(movesToBeSimulated);
		return boardFromToValues;
	}//end of method readInput
	
	
	public String[] boardCoordinates(List<String> movesToBeSimulated) {
		String[] boardFromToValues = new String[movesToBeSimulated.size()];
		boardFromToValues          = movesToBeSimulated.toArray(boardFromToValues);
		return boardFromToValues;
	}//end of method boardCoordinates
	
	
	public void rowColSplitter(String[] boardFromToValues) {
		int row, col;
		String[] temp = boardFromToValues[0].split(":");
		
		for(String ptr:temp) {
			System.out.println("Splitter --> " + ptr);
			row = getCoordinates(ptr, 0);
			System.out.println("row = " + row);
			col = getCoordinates(ptr, 1);
			System.out.println("col = " + col);
		}		
	}//end of method rowColSplitter
	
	
	public String getKeyByValue(Map<String, Integer> hmap2, Integer value) {
        for (Entry<String, Integer> entry : hmap2.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
	}//end of method getKeyByValue
	
	
	public int getCoordinates(String key, int position) {
		String[] arrOfStr = key.split("-");	
		return Integer.parseInt(arrOfStr[position]);
	}//end of method getCoordinates
	
	
}// end of class ReadFromFile
