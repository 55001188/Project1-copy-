import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Solver {
	private Queue<Tile> path;
	private ArrayList<Tile> pathArr;
	private ArrayList<Tile> finalPath;
	private Map newMap;
	private boolean[][] checked;
	private Queue<Tile> tilesQ;
	private Stack<Tile> tilesO;
	private Stack<Tile> tilesS;
	private Tile[][] copy;
	private char[][] pathmap;
	private String[][] pathcoords;
	private int start, end, roomNum;
	private boolean cakeFound,cakeInRoom, doorFound, usedQ, usedS, opt;
	//scanner and file vars needed??
	
	public Solver(){ //default constructor
		
	}
	
	public Solver(Scanner scan, File file){
		path = new LinkedList<Tile>();
		pathArr = new ArrayList<Tile>();
		finalPath = new ArrayList<Tile>();
		newMap = new Map();
		newMap.maps(scan, file);
		newMap.makeTileMap();
		//copy = newMap.getTileMap();
		//copy = newMap.room(1);
		tilesQ = new LinkedList(); 
		tilesO = new Stack(); 
		tilesS = new Stack();
		cakeFound = false;
		roomNum = newMap.getRooms();
		usedQ = false;
		opt = false;
		usedS = false;
		pathmap = newMap.getCharMap();
	}
	
	private void checkRoomQ() { //using queues
		cakeInRoom = false;
		doorFound = false;
		
		//unchecked tiles
		int col = newMap.room(1)[0].length;
		int row = newMap.room(1).length;
		checked = new boolean[row][col];
		for(int i = 0; i < checked.length; i++) {
			for(int j = 0; j < checked[0].length; j++) {
				checked[i][j] = false;
			}
		}
		
		//find and enqueue 'K'
		for(int i = 0; i < copy.length; i++) {
			for(int j = 0; j < copy[0].length; j++) {
				if(copy[i][j].getC() == 'K') {
					tilesQ.add(copy[i][j]);
					checked[i][j] = true;
				}
				
				if(copy[i][j].getC() == 'C') {
					cakeInRoom = true;
				}
			}
		}
		
		
		if(cakeInRoom) {
			while(!cakeFound) {
				Tile temp = tilesQ.peek();
				path.add(tilesQ.remove());
				QcheckNSEW(temp);
		
			}
			while(tilesQ.size() > 0) {
				path.add(tilesQ.remove());
			}
			
		} else {
			while(!doorFound) {
				Tile temp = tilesQ.peek();
				path.add(tilesQ.remove());
				QcheckNSEW(temp);
		
			}
		}
		
		
		
		
	}
	
	private void checkRoomS() { //using stacks
		cakeInRoom = false;
		doorFound = false;
		
		//unchecked tiles
		int col = newMap.room(1)[0].length;
		int row = newMap.room(1).length;
		checked = new boolean[row][col];
		for(int i = 0; i < checked.length; i++) {
			for(int j = 0; j < checked[0].length; j++) {
				checked[i][j] = false;
			}
		}
		//find and push K
		for(int i = 0; i < copy.length; i++) {
			for(int j = 0; j < copy[0].length; j++) {
				if(copy[i][j].getC() == 'K') {
					tilesS.add(copy[i][j]);
					checked[i][j] = true;
				}
				
				if(copy[i][j].getC() == 'C') {
					cakeInRoom = true;
				}
			}
		}
		
		Tile start = tilesS.peek();
		//figure out stack stuff
		/*
		 * NSEW
		 * move north until you can't anymore while pushing each Tile you visit to stack
		 * check if can move S E or W
		 * keep going in chosen direction until you can't or until one of the previous direction is an option
		 * while pushing each Tile to stack
		 * if you are completely stuck, pop the Tiles from the stack until you can move again
		 * you cannot re push and Tiles that have already been checked 
		 */
		
		
		ScheckNSEW(start);
		
		for(int i = tilesS.size()-1; i >= 0; i--) {
			path.add(tilesS.pop());
		}
		
	}
	
	private void ScheckNSEW(Tile tile) {
		int r = tile.getRow();
		int c = tile.getCol();
		//have to use that while statement for r for multiple rooms
		while(r > checked.length) {
			r-=checked.length;
		}
		
		if(cakeInRoom) {
			if(cakeFound) {
			} else {
				if(r-1 >= 0 && !checked[r-1][c] && copy[r-1][c].getC() != '@') {
					if(copy[r-1][c].getC() == 'C') {
						tilesS.push(copy[r-1][c]);
						cakeFound = true;
					}else if(copy[r-1][c].getC() == '.') {
						tilesS.push(copy[r-1][c]);
						checked[r-1][c] = true;
						ScheckNSEW(copy[r-1][c]);
					}
				} else if(r+1 < copy.length && !checked[r+1][c] && copy[r+1][c].getC() != '@') {
					if(copy[r+1][c].getC() == 'C') {
						tilesS.push(copy[r+1][c]);
						cakeFound = true;
					}
					else if(copy[r+1][c].getC() == '.') {
						tilesS.push(copy[r+1][c]);
						checked[r+1][c] = true;
						ScheckNSEW(copy[r+1][c]);
					}
				} else if(c+1 < copy[0].length && !checked[r][c+1] && copy[r][c+1].getC() != '@') {
					if(copy[r][c+1].getC() == 'C') {
						tilesS.push(copy[r][c+1]);
						cakeFound = true;
					}
					else if(copy[r][c+1].getC() == '.') {
						tilesS.push(copy[r][c+1]);
						checked[r][c+1] = true;
						ScheckNSEW(copy[r][c+1]);
					}
				} else if(c-1 >= 0 && !checked[r][c-1] && copy[r][c-1].getC() != '@') {
					if(copy[r][c-1].getC() == 'C') {
						tilesS.push(copy[r][c-1]);
						cakeFound = true;
					}
					else if(copy[r][c-1].getC() == '.') {
						tilesS.push(copy[r][c-1]);
						checked[r][c-1] = true;
						ScheckNSEW(copy[r][c-1]);
					}
				} else {
					if(tilesS.size() > 0) {
						tilesS.pop();
						if(tilesS.size() > 0) {
							ScheckNSEW(tilesS.peek());
						}
					}
					
				}
			}
		} else {
			if(doorFound) {
			} else {
				if(r-1 >= 0 && !checked[r-1][c] && copy[r-1][c].getC() != '@') {
					if(copy[r-1][c].getC() == '|') {
						//take note of door
						doorFound = true;
						tilesS.push(copy[r-1][c]);
						
					}
					else if(copy[r-1][c].getC() == '.') {
						tilesS.push(copy[r-1][c]);
						checked[r-1][c] = true;
						ScheckNSEW(copy[r-1][c]);
					}
				} else if(r+1 < copy.length && !checked[r+1][c] && copy[r+1][c].getC() != '@') {
					if(copy[r+1][c].getC() == '|') {
						//take note of door
						doorFound = true;
						tilesS.push(copy[r+1][c]);
						
					}
					else if(copy[r+1][c].getC() == '.') {
						tilesS.push(copy[r+1][c]);
						checked[r+1][c] = true;
						ScheckNSEW(copy[r+1][c]);
					}
				} else if(c+1 < copy[0].length && !checked[r][c+1] && copy[r][c+1].getC() != '@') {
					if(copy[r][c+1].getC() == '|') {
						//take note of door
						doorFound = true;
						tilesS.push(copy[r][c+1]);
						
					}
					else if(copy[r][c+1].getC() == '.') {
						tilesS.push(copy[r][c+1]);
						checked[r][c+1] = true;
						ScheckNSEW(copy[r][c+1]);
					}
				} else if(c-1 >= 0 && !checked[r][c-1] && copy[r][c-1].getC() != '@') {
					if(copy[r][c-1].getC() == '|') {
						//take note of door
						doorFound = true;
						tilesS.push(copy[r][c-1]);
						
					}
					else if(copy[r][c-1].getC() == '.') {
						tilesS.push(copy[r][c-1]);
						checked[r][c-1] = true;
						ScheckNSEW(copy[r][c-1]);
					}
				} else {
					if(tilesS.size() > 0) {
						tilesS.pop();
						if(tilesS.size() > 0) {
							ScheckNSEW(tilesS.peek());
						}
					}
					
				}
			}
		}
	}
	
	
	private void QcheckNSEW(Tile tile) { 
		for(int i = 0; i < copy.length; i++) {
			for(int j = 0; j < copy[0].length; j++) {
				if(copy[i][j] == tile) {
					if(i - 1 >= 0 && checked[i-1][j] == false) { //check NORTH
						//check for cake, door, then walkable path
						//System.out.println("n");
						if(copy[i-1][j].getC() == 'C') {
							tilesQ.add(copy[i-1][j]);
							cakeFound = true;
						}
						else if(copy[i-1][j].getC() == '|') {
							//take note of door
							doorFound = true;
							tilesQ.add(copy[i-1][j]);
							
						}
						else if(copy[i-1][j].getC() == '.') {
							tilesQ.add(copy[i-1][j]);
							checked[i-1][j] = true;
						}
					}
					
					if(i+1 < copy.length && checked[i+1][j] == false) { //check SOUTH
						//check for cake, door, then walkable path
						//System.out.println("s");
						if(copy[i+1][j].getC() == 'C') {
							tilesQ.add(copy[i+1][j]);
							cakeFound = true;
						}
						else if(copy[i+1][j].getC() == '|') {
							//take note of door
							doorFound = true;
							tilesQ.add(copy[i+1][j]);
						}
						else if(copy[i+1][j].getC() == '.') {
							tilesQ.add(copy[i+1][j]);
							checked[i+1][j] = true;
						}
					}
					
					if(j+1 < copy[0].length && checked[i][j+1] == false) { //check EAST
						//check for cake, door, then walkable path
						//System.out.println("e");
						if(copy[i][j+1].getC() == 'C') {
							tilesQ.add(copy[i][j+1]);
							cakeFound = true;
						}
						else if(copy[i][j+1].getC() == '|') {
							//take note of door
							doorFound = true;
							tilesQ.add(copy[i][j+1]);
						}
						else if(copy[i][j+1].getC() == '.') {
							tilesQ.add(copy[i][j+1]);
							checked[i][j+1] = true;
						}
					}
					
					if(j-1 >= 0 && checked[i][j-1] == false) { //check WEST
						//System.out.println("w");
						//check for cake, door, then walkable path
						if(copy[i][j-1].getC() == 'C') {
							tilesQ.add(copy[i][j-1]);
							cakeFound = true;
						}
						else if(copy[i][j-1].getC() == '|') {
							//take note of door
							doorFound = true;
							tilesQ.add(copy[i][j-1]);
						}
						else if(copy[i][j-1].getC() == '.') {
							tilesQ.add(copy[i][j-1]);
							checked[i][j-1] = true;
						}
					}
				}
			}
		}
	}
		
	
	public void checkMapQueue() { //queue
		/**code**/
		//start = 0;
		usedQ = true;
		usedS = false;
		opt = false;
		roomNum = newMap.getRooms();
		//end = copy.length/roomNum;
		path = new LinkedList<Tile>();
		
		for(int i = 1; i <= roomNum; i++) {
			copy = newMap.room(i);
			for(int j = 0; j < copy.length; j ++) {
				for(int l = 0; l < copy[0].length; l++) {
					if(copy[j][l].getC() == 'C') {
						cakeInRoom = true;
					}
				}
			}
			if(!cakeInRoom && i == roomNum) {
				System.out.println("The cake is a lie!");
			}else {
				checkRoomQ();
			}
			
		}
	}

	public void checkMapStack() { //stack
		/**code**/
		//start = 0;
		usedS = true;
		usedQ = false;
		opt = false;
		roomNum = newMap.getRooms();
		//end = copy.length/roomNum;
		path = new LinkedList<Tile>();
		
		
		for(int i = 1; i <= roomNum; i++) {
			copy = newMap.room(i);
			for(int j = 0; j < copy.length; j ++) {
				for(int l = 0; l < copy[0].length; l++) {
					if(copy[j][l].getC() == 'C') {
						cakeInRoom = true;
					}
				}
			}
			if(!cakeInRoom && i == roomNum) {
				System.out.println("The cake is a lie!");
			}else {
				checkRoomS();
			}
			
		}
	}	
	
	public void checkMapOp() { //optimal path
		//start = 0;
		opt = true;
		usedS = false;
		usedQ = false;
		roomNum = newMap.getRooms();
		//end = copy.length/roomNum;
		path = new LinkedList<Tile>();
		
		
		for(int i = 1; i <= roomNum; i++) {
			copy = newMap.room(i);
			for(int j = 0; j < copy.length; j ++) {
				for(int l = 0; l < copy[0].length; l++) {
					if(copy[j][l].getC() == 'C') {
						cakeInRoom = true;
					}
				}
			}
			if(!cakeInRoom && i == roomNum) {
				System.out.println("The cake is a lie!");
			}else {
				checkRoomOp();
			}
			
		}
	}
	
	private void checkRoomOp() {
		cakeInRoom = false;
		doorFound = false;
		
		
		Tile end = new Tile('.',0,0);
		Tile start = new Tile('.',0,0);
		//unchecked tiles
		int col = newMap.room(1)[0].length;
		int row = newMap.room(1).length;
		checked = new boolean[row][col];
		for(int i = 0; i < checked.length; i++) {
			for(int j = 0; j < checked[0].length; j++) {
				checked[i][j] = false;
			}
		}
		//find and push K
		for(int i = 0; i < copy.length; i++) {
			for(int j = 0; j < copy[0].length; j++) {
				if(copy[i][j].getC() == 'K') {
					tilesO.add(copy[i][j]);
					checked[i][j] = true;
				}
				
				if(copy[i][j].getC() == 'C') {
					cakeInRoom = true;
				}
			}
		}
		start = tilesO.peek();
		//System.out.println(start.getTile());
		start.prev = null;
		optNSEW(start);
		for(int i = tilesO.size()-1; i >= 0; i --) {
			if(tilesO.size() > 0 && (tilesO.peek().getC() == 'C' || tilesO.peek().getC() == '|')) {
				i = -1;
			} else {
				tilesO.pop();
			}
		}
		
		for(int i = 0; i < tilesO.size(); i ++) {
			//System.out.println(tilesO.remove(0).getTile());
			path.add(tilesO.remove(0));
			i--;
		}
		
	}
	
	private void optNSEW(Tile tile) {
		int r = tile.getRow();
		int c = tile.getCol();
		//have to use that while statement for r for multiple rooms
		while(r > checked.length) {
			r-=checked.length;
		}
		
		if(cakeInRoom) {
			while(!cakeFound) {
				if(r-1 >= 0 && !checked[r-1][c] && copy[r-1][c].getC() != '@') {
					if(copy[r-1][c].getC() == 'C') {
						tilesO.push(copy[r-1][c]);
						copy[r-1][c].prev = tile;
						cakeFound = true;
					}else if(copy[r-1][c].getC() == '.') {
						tilesO.push(copy[r-1][c]);
						copy[r-1][c].prev = tile;
						checked[r-1][c] = true;
						optNSEW(copy[r-1][c]);
					}
				}
				if(r+1 < copy.length && !checked[r+1][c] && copy[r+1][c].getC() != '@') {
					if(copy[r+1][c].getC() == 'C') {
						tilesO.push(copy[r+1][c]);
						copy[r+1][c].prev = tile;
						cakeFound = true;
					}
					else if(copy[r+1][c].getC() == '.') {
						tilesO.push(copy[r+1][c]);
						copy[r+1][c].prev = tile;
						checked[r+1][c] = true;
						optNSEW(copy[r+1][c]);
					}
				}
				if(c+1 < copy[0].length && !checked[r][c+1] && copy[r][c+1].getC() != '@') {
					if(copy[r][c+1].getC() == 'C') {
						tilesO.push(copy[r][c+1]);
						copy[r][c+1].prev = tile;
						cakeFound = true;
					}
					else if(copy[r][c+1].getC() == '.') {
						tilesO.push(copy[r][c+1]);
						copy[r][c+1].prev = tile;
						checked[r][c+1] = true;
						optNSEW(copy[r][c+1]);
					}
				}
				if(c-1 >= 0 && !checked[r][c-1] && copy[r][c-1].getC() != '@') {
					if(copy[r][c-1].getC() == 'C') {
						tilesO.push(copy[r][c-1]);
						copy[r][c-1].prev = tile;
						cakeFound = true;
					}
					else if(copy[r][c-1].getC() == '.') {
						tilesO.push(copy[r][c-1]);
						copy[r][c-1].prev = tile;
						checked[r][c-1] = true;
						optNSEW(copy[r][c-1]);
					}
				}
			}
		} else {
			while(!doorFound) {
				if(r-1 >= 0 && !checked[r-1][c] && copy[r-1][c].getC() != '@') {
					if(copy[r-1][c].getC() == '|') {
						//take note of door
						tilesO.push(copy[r-1][c]);
						copy[r-1][c].prev = tile;
						doorFound = true;
						
					}
					else if(copy[r-1][c].getC() == '.') {
						tilesO.push(copy[r-1][c]);
						copy[r-1][c].prev = tile;
						checked[r-1][c] = true;
						optNSEW(copy[r-1][c]);
					}
				}
				if(r+1 < copy.length && !checked[r+1][c] && copy[r+1][c].getC() != '@') {
					if(copy[r+1][c].getC() == '|') {
						//take note of door
						tilesO.push(copy[r+1][c]);
						copy[r+1][c].prev = tile;
						doorFound = true;
						
					}
					else if(copy[r+1][c].getC() == '.') {
						tilesO.push(copy[r+1][c]);
						copy[r+1][c].prev = tile;
						checked[r+1][c] = true;
						optNSEW(copy[r+1][c]);
					}
				}
				if(c+1 < copy[0].length && !checked[r][c+1] && copy[r][c+1].getC() != '@') {
					if(copy[r][c+1].getC() == '|') {
						//take note of door
						tilesO.push(copy[r][c+1]);
						copy[r][c+1].prev = tile;
						doorFound = true;
						
					}
					else if(copy[r][c+1].getC() == '.') {
						tilesO.push(copy[r][c+1]);
						copy[r][c+1].prev = tile;
						checked[r][c+1] = true;
						optNSEW(copy[r][c+1]);
					}
				}
				if(c-1 >= 0 && !checked[r][c-1] && copy[r][c-1].getC() != '@') {
					if(copy[r][c-1].getC() == '|') {
						//take note of door
						tilesO.push(copy[r][c-1]);
						copy[r][c-1].prev = tile;
						doorFound = true;
					}
					else if(copy[r][c-1].getC() == '.') {
						tilesO.push(copy[r][c-1]);
						copy[r][c-1].prev = tile;
						checked[r][c-1] = true;
						optNSEW(copy[r][c-1]);
					}
				}
			}
			
		}
		
		
		
	}
	
	
	public void roomPath(){ //leave as void??
		path();
		finalPath = new ArrayList<Tile>();
		ArrayList<Tile> direct = new ArrayList<Tile>();
		ArrayList<Tile> copyPath = pathArr;
		ArrayList<Tile> arr = new ArrayList<Tile>();
		ArrayList<Tile> arr2 = new ArrayList<Tile>();
		
		if(usedQ) {
			
			for(int i = 0; i < roomNum; i++) {
				while(copyPath.get(copyPath.size()-1).getC() != 'K') {
					arr.add(0, copyPath.remove(copyPath.size()-1));
				}
				arr.add(0, copyPath.remove(copyPath.size()-1));
				
				testPath(arr);
				
				while(arr.size() > 0) {
					arr.remove(0);
				}
			}
			
		}else if(usedS || opt) {
			finalPath = pathArr;
		}
		
		//PathMap 
		
		
		//System.out.println();
		for(int i = 0; i < pathmap.length; i++) {
			for(int j = 0; j < pathmap[0].length; j++) {
				for(int h = 0; h < finalPath.size(); h++) {
					if(finalPath.get(h).getRow() == i && finalPath.get(h).getCol() == j) {
						if(finalPath.get(h).getC() != 'K' && finalPath.get(h).getC() != '|' && finalPath.get(h).getC() != 'C') {
							pathmap[i][j] = '+';
						}
						
					}
				}
			}
		}
				
		// coordinate path
		ArrayList<Tile> tempArr = new ArrayList<Tile>();
		for(int i = 0; i < finalPath.size(); i++) {
			tempArr.add(0, finalPath.get(i));
		}
		for(int i = 0; i < tempArr.size(); i++) {
			if(tempArr.get(i).getC() != '.') {
				tempArr.remove(i);
				i--;
			}
		}
		pathcoords = new String[tempArr.size()][3]; 		
		//System.out.println();
		for(int i = 0; i < tempArr.size(); i++) {
			pathcoords[i][0] = "+";
			pathcoords[i][1] = tempArr.get(i).getRow() + "";
			pathcoords[i][2] = tempArr.get(i).getCol() + "";
			//System.out.println(finalPath.get(i).getTile());
		}
		
		
	}
	
	private ArrayList<Tile> testPath(ArrayList<Tile> pathSeg){ //works
		ArrayList<Tile> direct = new ArrayList<Tile>();
		ArrayList<Tile> tempArr = pathSeg;
		
		Tile temp = new Tile('.', 0, 0);
		boolean back = false;
		if(cakeInRoom && usedQ) {
			for(int i = 0; i < tempArr.size(); i++) {
				//tempArr.get(i).getC() == 'C' || 
				if(tempArr.get(i).getC() == 'C' ||tempArr.get(i).getC() == '|') {
					temp = tempArr.get(i);
					direct.add(tempArr.get(i));
					tempArr.remove(i);
				}
			}/**/
			while(tempArr.size() > 0) {
				for(int i = tempArr.size()-1; i >= 0; i--) {
					//back = false;
					//System.out.println(tempArr.get(i).getTile() + " i ="+i);
					if(tempArr.get(i).getRow() == temp.getRow()) {
						//System.out.println("Row test");
						if(tempArr.get(i).getCol() == temp.getCol()-1 || tempArr.get(i).getCol() == temp.getCol()+1){
							direct.add(tempArr.get(i));
							temp = tempArr.get(i);
							tempArr.remove(i);
							i = tempArr.size();
							
						} else {
							back = true;
						}
					} else if(tempArr.get(i).getCol() == temp.getCol()){
						//System.out.println("Col test")
						if(tempArr.get(i).getRow() == temp.getRow()-1 || tempArr.get(i).getRow() == temp.getRow()+1) {
							direct.add(tempArr.get(i));
							temp = tempArr.get(i);
							tempArr.remove(i);
							i = tempArr.size();
							
						} else {
							back = true;
						}
					} else {
						back = true;
					}
					/*
					for(int t = 0; t < direct.size(); t++) {
						System.out.println(direct.get(t).getTile());
					}System.out.println();/*
					for(int l = 0; l < tempArr.size(); l++) {
						System.out.println(tempArr.get(l).getTile());
					} System.out.println();*/
				}
				
				if(direct.size() > 0 && back) {
					direct.remove(direct.size()-1);
					for(int i = 0; i < direct.size(); i++) {
						//System.out.println(direct.get(i).getTile());
					}
					temp = direct.get(direct.size()-1);
					back = false;
				}
			}
			
			
		} else if((cakeInRoom && usedS) || (cakeInRoom && opt)) {
			for(int i = 0; i < pathSeg.size(); i++) {
				direct.add(pathSeg.remove(i));
				i--;
			}
		}
		
		//removes any extra tiles after k is found
		int w = direct.size()-1;/**/
		while(direct.size() > 0 && direct.get(w).getC() != 'K') {
			direct.remove(w);
			w--;
		}
		
		// coordinate path
		
		//System.out.println();
		for(int i = 0; i < direct.size(); i++) { //make into map
			//System.out.println(direct.get(i).getTile());
			finalPath.add(direct.get(i));
		}
		return direct;
		
	}
	
	private void path() {
		int p = path.size();
		for(int i = 0; i < p; i ++) {
			Tile temp = path.remove();
			pathArr.add(temp);
			//System.out.println(temp.getTile());
		}
		//System.out.println();
		/*
		for(int i=0; i < pathArr.size(); i++) {
			System.out.println(pathArr.get(i).getTile());
		}*/
	}
	
	
	public Map getMap() {
		return newMap;
	}
	
	public char[][] getTextMap(){
		for(int i = 0; i < pathmap.length; i++) {
			for(int j = 0; j < pathmap[0].length; j++) {
				System.out.print(pathmap[i][j] + "");
			}
			System.out.println();
		}
		return pathmap;
	}
	
	public String[][] getPathCoordsMap() {
		for(int i = 0; i < pathcoords.length; i++) {
			for(int j = 0; j < pathcoords[0].length; j++) {
				System.out.print(pathcoords[i][j] +" ");
			}System.out.println();
		}
		return pathcoords;
	}

}
