import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
	private static int rows, cols, rooms;
	private static char[][] map;
	private static String[][] coords;
	private Tile[][] tileMap;
	private static boolean coordsMapSet;
	private static boolean charMapSet;

	public Map() {
		rows = 0;
		cols = 0;
		rooms = 0;
		coordsMapSet = false;
		charMapSet = false;
	}
	
	public void maps(Scanner scanner, File f) {
		charMapSet = true;
		try {
			scanner = new Scanner(f);
			scanner.nextLine();
			String check = scanner.nextLine();
			boolean charMap = true;
			for(int i = 0; i < check.length()-1; i++) {
				//may need to change if coord maps aren't spaced or maps are spaced
				if(check.substring(i, i+1).equals(" ")) {
					charMap = false;
				}
			}
			
			if(charMap) {
				charMapChar(scanner, f);
			} else {
				charMapCoords(scanner, f);
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void charMapChar(Scanner scanner, File f) { /**works**/ //input is a char map
		
		try {
			//code that might throw a special error
			scanner = new Scanner(f);
			
			//use next methods to grab the first 3 numbers
			//from the file for your map
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			
			if(rows < 0 || cols < 0 || rooms < 0) {
				System.out.println("either the row number, column number, or number of rooms"+
						" is a negative numeber");
				System.exit(-1);
			}
			
			//take in the cols and # of rooms
			map = new char[rows*rooms][cols];
			int i = 0;
			scanner.nextLine();
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				//use charAt to grab the elements of the map for a given row
				if(i < map.length) { //ignore extra chars on a line or extra rows of chars
					for(int j = 0; j < cols; j ++) {
						map[i][j] = line.charAt(j);
						//map[i][j] = scanner.next().charAt(0);
					}
				}
				
				
				i++;
				
			}
			
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void charMapCoords(Scanner scanner, File f) { /**works**/ //input is a coord map
		
		try {
			//code that might throw a special error
			scanner = new Scanner(f);
			
			//use next methods to grab the first 3 numbers
			//from the file for your map
			rows = scanner.nextInt();
			cols = scanner.nextInt();
			rooms = scanner.nextInt();
			
			if(rows < 0 || cols < 0 || rooms < 0) {
				System.out.println("either the row number, column number, or number of rooms"+
						" is a negative numeber");
				System.exit(-1);
			}
			
			//take in the cols and # of rooms
			map = new char[rows*rooms][cols];
			for(int i = 0; i < map.length; i++) { //initialize map with "."
				for(int j = 0; j < map[0].length; j++) {
					map[i][j] = '.';
				}
			}
			
			scanner.nextLine();
			while(scanner.hasNext()) {
				char ch = scanner.next().charAt(0);
				int r = Integer.valueOf(scanner.next());
				int col = Integer.valueOf(scanner.next());
				
				// if ints or chars do not match proper format an error is given
				if(r >= rows*rooms || r < 0 || col >= cols || col < 0) { 
					System.out.println("error: coordinates are out of bounds");
					System.exit(-1);
				} else {
					if(ch != '|' && ch != '@' && ch != 'K' &&
							ch != 'C' && ch != '.') {
						System.out.println("illegal characters");
						System.exit(-1);
					} else {
						map[r][col] = ch;
					}
				}
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void coordsMap() { /**works**/
		coordsMapSet = true;
		int r = map.length * map[0].length;
		//int r = map.length;
		coords = new String[r][3];
		int k = 0;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < cols; j ++) {
				coords[k][0] = map[i][j] + "";
				coords[k][1] = i + "";
				coords[k][2] = j + "";
				k++;
			}
						
		}
	}
	
	public void makeTileMap() {
		int tRows = map.length;
		int tCols = map[0].length;
		tileMap = new Tile[tRows][tCols];
		for(int i = 0; i < tRows; i ++) {
			for(int j = 0; j < tCols; j++) {
				tileMap[i][j] = new Tile(map[i][j], i, j);
			}
		}
	}
	
	public char[][] getCharMap(){
		if(!charMapSet) {
			System.out.println("character map not created");
			return null;
		} else {/*
			for(int i = 0; i < map.length; i ++) {
				for(int j = 0; j < map[0].length; j++) {
					System.out.print(map[i][j] + "");
				}
				System.out.println();
			}*/
			return map;
		}
		
	}
	
	public String[][] getCoordMap(){
		if(!coordsMapSet) {
			System.out.println("coordinate map not created");
			return null;
		} else {/*
			for(int i = 0; i < coords.length; i ++) {
				for(int j = 0; j < coords[0].length; j++) {
					System.out.print(coords[i][j] + " ");
				}
				System.out.println();
			}*/
			return coords;
		}
	}
	
	public Tile[][] getTileMap(){
		return tileMap;
	}
	
	public int getRooms() {
		return rooms;
	}
	
	public int getCol() {
		return cols;
	}
	
	public int getRow() {
		return rows;
	}
	
	public Tile[][] room(int num) {
		int start = 0;
		int end = tileMap.length/rooms;
		Tile[][] room = new Tile[end][tileMap[0].length];
		for(int i = 1; i < num; i++) {
			start += end;
			end += end;
		}
		
		//System.out.println("Room: " + num);
		int k = 0;
		for(int i = start; i < end; i++) {
			for(int j = 0; j < tileMap[0].length; j++) {
				room[k][j] = tileMap[i][j];
				//System.out.print(room[k][j].getC());
			}
			//System.out.println();
			k++;
		}
		
		return room;
		
	}
	
}
