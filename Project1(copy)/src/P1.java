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
		
		try {
			scanner = new Scanner(f);
			Solver p = new Solver(scanner, g);
			p.getMap().getCharMap();
			p.checkMapOp();
			//p.checkMapQueue();
			//p.checkMapStack();
			p.roomPath();
			//p.getPathCoordsMap();
			
			
			/*
			 * stack and path and direct path work
			 */
		} catch (FileNotFoundException e) {
			
		}
		
		long total = System.currentTimeMillis() - startTime;
		System.out.print(total);
	}

}
