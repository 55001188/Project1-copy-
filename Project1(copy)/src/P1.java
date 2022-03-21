import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		boolean q, s, o, t, in, out, help;
		q = false; s = false; o = false; t = false; in = false; out = false; help = false;
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("Queue")) {
				q = true;
			}
			if(args[i].equals("Stack")) {
				s = true;
			}
			if(args[i].equals("Opt")) {
				o = true;
			}
			if(args[i].equals("Time")) {
				t = true;
			}
			if(args[i].equals("Incoordinate")) {
				in = true;
			}
			if(args[i].equals("Outcoordinate")) {
				out = true;
			}
			if(args[i].equals("Help")) {
				help = true;
			}
		}
		
		Map m = new Map();
		Scanner scanner;
		//how is file chosen??
		File f = new File("oliviaCoordinateMap3.txt");
		File g = new File("oliviaMap3.txt");
		
		String helpInfo = "This program is supposed to process maps and return the map with the path marked" 
				+"\n"+ "SWITCHES:" +"\n"+ "Stack: uses the stack based approach to solve for the path"+"\n"+
				"Queue: uses the queue based approach to solve for the path"+"\n"+"Opt: finds the shortest path"+
				"\n"+"Time: prints the runtime of the program"+"\n"+
				"Incoordinate: if set, the input file is in the coordinate-based system, else it is in the"
				+ " text-map format"+"\n"+"Outcoordinate: if set, the output file is in the coordinate based system"
				+", else it is in the text-map format"+"\n"+"Help: prints out what the program is meant to do"+
				" and what the switches are and what they do";
		
		if(help) {
			System.out.println(helpInfo);
		}
		
		try {
			scanner = new Scanner(f);
			Solver p = new Solver(scanner, g);
			if(in) {
				p.getMap().getCharMap();
			} else {
				p.getMap().getCharMap();
			}
			
			if(q) {
				p.checkMapQueue();
			}
			if(s) {
				p.checkMapStack();
			}
			if(o) {
				p.checkMapOp();
			}
			if(out) {
				p.roomPath();
				p.getPathCoordsMap();
			} else {
				p.roomPath();
				p.getTextMap();
			}
			
			
		} catch (FileNotFoundException e) {
			
		}
		
		long total = System.currentTimeMillis() - startTime;
		if(t) {
			System.out.print(total);
		}
	}

}
