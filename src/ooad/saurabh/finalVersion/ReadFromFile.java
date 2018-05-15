package ooad.saurabh.finalVersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class ReadFromFile {
	
	private List<String> movesFromFile = new ArrayList<String>(); //Holds orginal input from File
	
	protected List<String> getMovesFromFile() {
		return movesFromFile;
	}

	//This Method generates a row-column pairs for all 64 boxes of Checkers board
	private Map<String, Integer> matrix(){
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
	}//end of method matrix()
	
	//This Method returns a String[] of row & column values with respect to given input moves in the file
	protected String[] readInput() {
		Map<String, Integer> hmap2 = matrix();
		List<String> movesToBeSimulated = new ArrayList<String>();	
	    File file = new File("/Users/saurabhsomani/Desktop/checkersInput.txt");
	    Scanner sc = null;
	    
		try {
		   sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(sc.hasNext()) {
			String tempStr    = sc.next();
			movesFromFile.add(tempStr);
			int leftValue     = getCoordinates(tempStr, 0);
			int rightValue    = getCoordinates(tempStr, 1);			
			String leftKey    = getKeyByValue(hmap2, leftValue);						
			String rightKey   = getKeyByValue(hmap2, rightValue);			
			String tempFromTo = leftKey + ':' + rightKey;
			movesToBeSimulated.add(tempFromTo);
		}		
		sc.close();
		String[] boardFromToValues  = boardCoordinates(movesToBeSimulated);
		return boardFromToValues;
	}//end of method readInput()
	
	
	//This method converts a List<String> into String[]
	protected String[] boardCoordinates(List<String> movesToBeSimulated) {
		String[] boardFromToValues = new String[movesToBeSimulated.size()];
		boardFromToValues          = movesToBeSimulated.toArray(boardFromToValues);
		return boardFromToValues;
	}//end of method boardCoordinates()
	
	//This method separates out Row & Column Values from the given String
	protected void rowColSplitter(String[] boardFromToValues) {
		int row, col;
		String[] temp = boardFromToValues[0].split(":");
		
		for(String ptr:temp) {
			System.out.println("Splitter --> " + ptr);
			row = getCoordinates(ptr, 0);
			System.out.println("row = " + row);
			col = getCoordinates(ptr, 1);
			System.out.println("col = " + col);
		}		
	}//end of method rowColSplitter()
	
	//This Method returns row-column coordinates of original board with respect to input move from file. 
	private String getKeyByValue(Map<String, Integer> hmap2, Integer value) {
        for (Entry<String, Integer> entry : hmap2.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
	}//end of method getKeyByValue()
	
	
	//This Method returns the Integer value inside the given String
	protected int getCoordinates(String key, int position) {
		String[] arrOfStr = key.split("-");	
		return Integer.parseInt(arrOfStr[position]);
	}//end of method getCoordinates()
	
	
}// end of class ReadFromFile