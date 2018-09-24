import java.io.PrintWriter;
import java.util.Scanner;

public class Component {
	int numRows, numCols, minVal,maxVal, newMin, newMax, newLabel, EQSize;
	int minRow = 0, minCol = 0, maxRow = 0, maxCol = 0;
	int[][] zeroFramedAry;
	int[] EQAry, neighborAry;
	Scanner inFile;
	PrintWriter outFile1, outFile2, outFile3;
	
	//Constructor
	Component(int numRows, int numCols, int minVal, int maxVal, Scanner inFile, PrintWriter outFile1, PrintWriter outFile2, PrintWriter outFile3){
		this.numRows = numRows;
		this.numCols = numCols;
		this.minVal = minVal;
		this.maxVal = maxVal;
		newMin = maxVal;
		newMax = minVal;
		zeroFramedAry = new int [numRows+2][numCols+2];
		EQSize = (numRows*numCols)/2;
		EQAry = new int[EQSize];
		for(int i=0;i<EQSize;i++){
			EQAry[i] = i;
		}
		neighborAry = new int[4];
		this.inFile = inFile;
		this.outFile1 = outFile1;
		this.outFile2 = outFile2;
		this.outFile3 = outFile3;
	}
	
	//methods
	void loadImage(){
		for(int i=1;i<numRows+1;i++){
			for (int j=1;j<numCols+1;j++){
				zeroFramedAry[i][j] = Integer.parseInt(inFile.next());
			}
		}
	}
	
	void loadNeighbors(int row, int col){
		neighborAry[0] = zeroFramedAry[row-1][col];
		neighborAry[1] = zeroFramedAry[row][col-1];
		neighborAry[2] = zeroFramedAry[row][col+1];
		neighborAry[3] = zeroFramedAry[row+1][col];
	}
	
	void updateEQAry(int minLabel, int a, int b){
		if(a>minLabel)EQAry[a] = minLabel;
		if(b>minLabel)EQAry[b] = minLabel;
	}
	
	void manageEQAry(){
		int trueLabel = 0, index = 1;
		while(!(index>newLabel)){
			if(EQAry[index] == index){
				trueLabel++;
				EQAry[index] = trueLabel;
			}		
			else EQAry[index] = EQAry[EQAry[index]];
			index++;
		}
	}
	
	void printEQAry(int pass){
		outFile1.println("This is the EQArray for pass #"+pass+":");
		
		int counter=10;
		int tempEQSize=EQSize;
		while(tempEQSize/10!=0){
			counter=counter*10;
			tempEQSize=tempEQSize/10;
		}
		
		for(int i=0;i<EQSize;i++){
			int temp=EQAry[i];

			if(i%25==0){
				outFile1.print(EQAry[i]);
				outFile1.println();
			}
			else{
				outFile1.print(EQAry[i]);
				while(temp<counter){
					outFile1.print(" ");
					temp=temp*10;	
				}
				outFile1.print(" ");
			}
		}
	}
	
	//overloaded print EQAry method specifically for after manageEQAry method
	void printEQAry(String pass){
		outFile1.println("This is the EQArray after manageEQAry method: ");
		
		int counter=10;
		int tempEQSize=EQSize;
		while(tempEQSize/10!=0){
			counter=counter*10;
			tempEQSize=tempEQSize/10;
		}
		
		for(int i=0;i<EQSize;i++){
			int temp=EQAry[i];

			if(i%25==0){
				outFile1.print(EQAry[i]);
				outFile1.println();
			}
			else{
				outFile1.print(EQAry[i]);
				while(temp<counter){
					outFile1.print(" ");
					temp=temp*10;	
				}
				outFile1.print(" ");
			}
		}
		outFile1.println();
	}
	
	void prettyPrint(int pass){
		outFile1.print("This is the result of pass #" + pass + ":");
		for(int i=0;i<numRows+2;i++){
			for(int j=0;j<numCols+2;j++){
				if(zeroFramedAry[i][j]<10 && zeroFramedAry[i][j]!=0)
					outFile1.print(zeroFramedAry[i][j]+"  ");
				else if(zeroFramedAry[i][j]<10 && zeroFramedAry[i][j]==0)
					outFile1.print("   ");
				else outFile1.print(zeroFramedAry[i][j]+" ");
			}
			outFile1.println();
		}
		printEQAry(pass);
		outFile1.println();
	}
	
	void printImage(){
		outFile2.println(numRows+" "+numCols+" "+minVal+" "+maxVal);
		for(int i=0;i<numRows+2;i++){
			for(int j=0;j<numCols+2;j++){
				if(zeroFramedAry[i][j]<10)
					outFile2.print(zeroFramedAry[i][j]+"  ");
				else outFile2.print(zeroFramedAry[i][j]+" ");
			}
			outFile2.println();
		}
	}
	
	void pass1(){
		newLabel = 0;
		for(int i=1;i<numRows+1;i++){
			for(int j=1;j<numCols+1;j++){
				if(zeroFramedAry[i][j]>0){

					loadNeighbors(i,j);
					
					//Case 1
					if(neighborAry[0]==0 && neighborAry[1]==0){
						newLabel++;
						zeroFramedAry[i][j] = newLabel;
					}
					
					//Case 3
					else if(neighborAry[0]!=0 && neighborAry[1]!=0 && neighborAry[0]!=neighborAry[1]){
						int minLabel = 0;
						minLabel = neighborAry[0];
						if(neighborAry[1]<neighborAry[0]) 
							minLabel = neighborAry[1];
						zeroFramedAry[i][j] = minLabel;
						updateEQAry(minLabel, neighborAry[0], neighborAry[1]);
					}
					
					//Case 2
					else{
						if(neighborAry[0]!=0)
							zeroFramedAry[i][j] = neighborAry[0];
						else zeroFramedAry[i][j] = neighborAry[1];
					}
				}
			}
		}
	}
	
	void pass2(){
		for(int i=numRows+1;i>0;i--){
			for(int j=numCols+1;j>0;j--){
				if(zeroFramedAry[i][j]>0){
					
					loadNeighbors(i,j);
					
					//Case 1
					if(neighborAry[2]==0 && neighborAry[3]==0);
					
					//Case 2
					else{
						int minLabel = 0;
						minLabel = zeroFramedAry[i][j];
						if(neighborAry[2]<minLabel && neighborAry[2]!=0)
							minLabel = neighborAry[2];
						if(neighborAry[3]<minLabel && neighborAry[3]!=0)
							minLabel = neighborAry[3];
						zeroFramedAry[i][j] = minLabel;
						if(neighborAry[2]!=0 && neighborAry[3]!=0 && neighborAry[2]!=neighborAry[3]);
						updateEQAry(minLabel,neighborAry[2],neighborAry[3]);
					}
				}
			}
		}
	}
	
	int pass3(){
		int numObjects = 0, newMin = 0, newMax = 0;
		for(int i=1;i<numRows+1;i++){
			for(int j=1;j<numCols+1;j++){
				zeroFramedAry[i][j] = EQAry[zeroFramedAry[i][j]];
				if(zeroFramedAry[i][j]>numObjects)
					numObjects = zeroFramedAry[i][j];
				if(zeroFramedAry[i][j]>newMax)
					newMax = zeroFramedAry[i][j];
				if(zeroFramedAry[i][j]<newMin)
					newMin = zeroFramedAry[i][j];
			}
		}
		return numObjects;
	}
	
	void findBoundingBox(Property[] CC, int minRow, int minCol, int maxRow, int maxCol, int label, int index){
		int count = 0;
		//finds first instance of label to initialize
		for(int row=1;row<numRows+1;row++){
			for(int col=1;col<numCols+1;col++){
				if(zeroFramedAry[row][col] == label){
					minCol = col;
					maxCol = col;
				}
			}	
		}
		//using first instance to update bounds
		for(int row=1;row<numRows+1;row++){
			for(int col=1;col<numCols+1;col++){
				if(count == 0 && zeroFramedAry[row][col] == label){
					minRow = row;
					maxRow = row;	
					count++;
				}
				if(zeroFramedAry[row][col] == label)
					maxRow = row;
				
				if(zeroFramedAry[row][col] == label && col<minCol)
					minCol = col;
				if(zeroFramedAry[row][col] == label && col>maxCol)
					maxCol = col;
			}
		}
		CC[index].minRow = minRow;
		CC[index].minCol = minCol;
		CC[index].maxRow = maxRow;
		CC[index].maxCol = maxCol;
	}
	
	void loadProperty(Property[] CC, int size){
		int numPixels = 0;
		for(int i=0;i<size;i++){
			CC[i].label = i+1;
			numPixels = countLabel(i+1);
			CC[i].numPixels = numPixels;
			findBoundingBox(CC,minRow, minCol, maxRow, maxCol, i+1, i);
		}
	}
	
	void printProperty(Property CC[], int size){
		outFile3.println(numRows+" "+numCols+" "+minVal+" "+maxVal);
		outFile3.println(size);
		for(int i=0;i<size;i++){
			outFile3.println(CC[i].label);
			outFile3.println(CC[i].numPixels);
			outFile3.println(CC[i].minRow+" "+CC[i].minCol);
			outFile3.println(CC[i].maxRow+" "+CC[i].maxCol);
			outFile3.println();
		}
	}
	
	int countLabel(int label){
		int count = 0;
		for(int i=1;i<numRows+1;i++){
			for(int j=1;j<numCols+1;j++){
				if(zeroFramedAry[i][j]==label)
					count++;
			}
		}
		return count;
	}
}