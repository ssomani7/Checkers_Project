package ooad.saurabh.matrixGeneration;

//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Matrix {

	public static void main(String[] args) {
		Map<String, Integer> hmap1 = new LinkedHashMap<String, Integer>();
		int count = 0;
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				String temp1 = row + "-" + col;
				if(row % 2 == 0) { //even number of rows
					if(col % 2 == 0) { //even number of columns
						count = count + 1;
						hmap1.put(temp1, count);
//						System.out.println("Count's value = "+ count);
					} else { //odd number of columns
						hmap1.put(temp1, 0);
					}
				} else { // odd number of rows
					if(col % 2 == 0) {
						hmap1.put(temp1, 0);
					} else {
						count = count + 1;
						hmap1.put(temp1, count);
//						System.out.println("Count's value = "+ count);
					}
				}
			}// inner for-loop for columns
		}// outer for-loop for rows
		
		for (Map.Entry<String, Integer> entry : hmap1.entrySet()) {
		    System.out.println(entry.getKey()+" : "+entry.getValue());
		}
		
//		try {
//			File file = new File("/Users/saurabhsomani/Desktop/hashMapOutput.txt");
//			FileWriter fileWriter = new FileWriter(file);
//			PrintWriter printWriter = new PrintWriter(fileWriter);
////			printWriter.print("This is ");
////			printWriter.print("a test");
//			for (Map.Entry<String, Integer> entry : hmap1.entrySet()) {
//			    printWriter.println(entry.getKey()+" : "+entry.getValue());
//			}
//			fileWriter.flush();
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}//end of main method
}//end of class Matrix
