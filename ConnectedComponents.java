import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConnectedComponents {

	public static void main(String[] argv) throws IOException{
		
		int numRows = 0, numCols = 0, minVal = 0, maxVal = 0, propertySize = 0;
		
		try {
			Scanner inFile = new Scanner(new File(argv[0]));	//read in data
			PrintWriter outFile1 = new PrintWriter(new FileWriter(argv[1]));
			PrintWriter outFile2 = new PrintWriter(new FileWriter(argv[2]));
			PrintWriter outFile3 = new PrintWriter(new FileWriter(argv[3]));
			
			//retrieve header information
			numRows = Integer.parseInt(inFile.next());
			numCols = Integer.parseInt(inFile.next());
			minVal = Integer.parseInt(inFile.next());
			maxVal = Integer.parseInt(inFile.next());
			
			Component component = new Component(numRows, numCols, minVal, maxVal, inFile, outFile1, outFile2, outFile3);
			component.loadImage();
			component.pass1();
			component.prettyPrint(1);
			component.pass2();
			component.prettyPrint(2);
			component.manageEQAry();
			propertySize = component.pass3();
			Property[] CC = new Property[propertySize];
			for(int a=0;a<propertySize;a++){
				CC[a]=new Property();
			}
			component.loadProperty(CC, propertySize);
			component.printProperty(CC, propertySize);
			component.printEQAry("manageEQAry");
			component.prettyPrint(3);
			component.printImage();
			
			outFile1.close();
			outFile2.close();
			outFile3.close();
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}