import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		
		Map m = new Map();
		Scanner scanner;
		File f = new File("oliviaCoordinateMap3.txt");
		File g = new File("oliviaMap3.txt");
		//
		//oliviaMap3
		String helpInfo = "This program is supposed to process maps and return the map with the path marked" 
				+"\n"+ "SWITCHES:" +"\n"+ "Stack: uses the stack based approach to solve for the path"+"\n"+
				"Queue: uses the queue based approach to solve for the path"+"\n"+"Opt: finds the shortest path"+
				"\n"+"Time: prints the runtime of the program"+"\n"+
				"Incoordinate: if set, the input file is in the coordinate-based system, else it is in the"
				+ " text-map format"+"\n"+"Outcoordinate: if set, the output file is in the coordinate based system"
				+", else it is in the text-map format"+"\n"+"Help: prints out what the program is meant to do"+
				" and what the switches are and what they do";
		//System.out.println(helpInfo);
		
		try {
			scanner = new Scanner(f);
			Solver p = new Solver(scanner, g);
			//p.getMap().getCharMap();
			//p.checkMapOp();
			//p.checkMapQueue();
			//p.checkMapStack();
			//p.roomPath();
			//p.getPathCoordsMap();
			
			
			/*
			 * stack
			 * queue
			 * opt
			 * time
			 * incoordinate
			 * outcoordinate
			 * help
			 */
		} catch (FileNotFoundException e) {
			
		}
		
		long total = System.currentTimeMillis() - startTime;
		//System.out.print(total);
	}

}
