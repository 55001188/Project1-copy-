import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class p1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		boolean q, s, o, t, in, out, help;
		q = false; s = false; o = false; t = false; in = false; out = false; help = false;
		int c = 0;
		//arguments should be boolean?
		/*
		if(args.length > 2) {
		for(int i = 0; i < args.length -1; i++) {
			for(int j = i+1; j < args.length -1; i++) {
				if(args[i].equals("--Queue")){
					if(args[j].equals("--Stack") || args[j].equals("--Opt")){
						System.out.println("Multiple of the following switches is set: "+
							"Queue, Stack, Opt");
						System.exit(-1);
					}
					
				}
				if(args[i].equals("--Stack") && (args[j].equals("--Queue") || args[j].equals("--Opt"))){
					System.out.println("Multiple of the following switches is set: "+
							"Queue, Stack, Opt");
					System.exit(-1);
				}
				if(args[i].equals("--Opt") && (args[j].equals("--Stack") || args[j].equals("--Queue"))){
					System.out.println("Multiple of the following switches is set: "+
							"Queue, Stack, Opt");
					System.exit(-1);
				}
			}
			
		}
		}*/
		
		for(int i = 0; i < args.length -1; i++) {
			if(args[i].equals("--Queue")) {
				q = true;
			}
			if(args[i].equals("--Stack")) {
				s = true;
			}
			if(args[i].equals("--Opt")) {
				o = true;
			}
			if(args[i].equals("--Time")) {
				t = true;
			}
			if(args[i].equals("--Incoordinate")) {
				in = true;
			}
			if(args[i].equals("--Outcoordinate")) {
				out = true;
			}
			if(args[i].equals("--Help")) {
				help = true;
			}
		}
		
		Map m = new Map();
		Scanner scanner;
		//how is file chosen??
		File f = new File(args[args.length-1]);
		
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
			System.exit(-1);
		}
		
		try {
			scanner = new Scanner(f);
			Solver p = new Solver(scanner, f);
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
