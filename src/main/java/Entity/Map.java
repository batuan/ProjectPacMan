package Entity;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Map {
	private int[][] mapGraphic; 
	private ArrayList<Point> coinPositions;
    private ArrayList<Point> puCoinPositions;
    //private ArrayList<TeleportTunnel> teleports;
    private ArrayList<Ghost> ghostsData;
    private ArrayList<Point> wallPositions;
    private Point pacmanPosition;
    private Point ghostBasePosition;
	public ArrayList<Point> getCoinPositions() {
		return coinPositions;
	}
	public void setCoinPositions(ArrayList<Point> coinPositions) {
		this.coinPositions = coinPositions;
	}

	public ArrayList<Point> getPuCoinPositions() {
		return puCoinPositions;
	}

	public void setPuCoinPositions(ArrayList<Point> puCoinPositions) {
		this.puCoinPositions = puCoinPositions;
	}

	public ArrayList<Ghost> getGhostsData() {
		return ghostsData;
	}

	public void setGhostsData(ArrayList<Ghost> ghostsData) {
		this.ghostsData = ghostsData;
	}

	public Point getPacmanPosition() {
		return pacmanPosition;
	}

	public void setPacmanPosition(Point pacmanPosition) {
		this.pacmanPosition = pacmanPosition;
	}

	public Point getGhostBasePosition() {
		return ghostBasePosition;
	}

	public void setGhostBasePosition(Point ghostBasePosition) {
		this.ghostBasePosition = ghostBasePosition;
	}

    public ArrayList<Point> getWallPositions() {
        return wallPositions;
    }

    public void setWallPositions(ArrayList<Point> wallPositions) {
        this.wallPositions = wallPositions;
    }

    public static void main(String[] args) {
		Map map =  new Map();
		map.getMapFromResource("resources/mapdata/map.txt");
	}

	public int[][] getMapGraphic() {
		return mapGraphic;
	}


	public void setMapGraphic(int[][] mapGraphic) {
		this.mapGraphic = mapGraphic;
	}
	
	public void getMapFromResource(String relPath){
        System.out.println("path: "+ relPath);
        BufferedReader reader;
        mapGraphic = new int[29][27];
        coinPositions = new ArrayList<>();
        puCoinPositions = new ArrayList<>();
        ghostsData = new ArrayList<>();
        try {
            File file = new File(this.getClass().getClassLoader().getResource(relPath).toURI());
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int i = 0;
            int j = 0;
            while(line != null){
                // Set Matrix position is reverse with Jpanel position, so j -> x, i -> y
            	System.out.println(line);
                for(char c : line.toCharArray()) {
                	if(c == '1'){
                        mapGraphic[i][j] = 0;
                        this.ghostsData.add(new Ghost(j, i, 1));
                    }
                    if(c == '2'){
                        mapGraphic[i][j] = 0;
                        this.ghostsData.add(new Ghost(j, i, 2));
                    }
                    if(c == '3'){
                        mapGraphic[i][j] = 0;
                        this.ghostsData.add(new Ghost(j, i, 3));
                    }
                    if(c == 'P'){
                        mapGraphic[i][j] = 0;
                        this.setPacmanPosition(new Point(j, i));
                    }
                    if(c == 'X'){
                        mapGraphic[i][j] = 23;
                        this.getWallPositions().add(new Point(j, i));
                    }
                    if(c == 'Y'){
                        mapGraphic[i][j] = 26;
                    }
                    if(c == '_'){
                        mapGraphic[i][j] = 0;
                        this.getCoinPositions().add(new Point(j, i));
                    }
                    if(c == '='){
                        mapGraphic[i][j] = 0;
                    }
                    if(c == 'O'){
                        mapGraphic[i][j] = 0;
                        this.getCoinPositions().add(new Point(j, i));
                    }
                    if(c == 'F'){
                        mapGraphic[i][j] = 0;
                        this.getPuCoinPositions().add(new Point(j, i));
                    }
                    if(c == 'B'){
                        mapGraphic[i][j] = 0;
                        this.setGhostBasePosition(new Point(j, i));
                    }
                    j+=1;
                    if(j==27) {
                    	i += 1;
                    	j = 0;
                    }
                }
                line = reader.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("read complete");
    }

    void showMapString(int[][] map) {
	    int mx = map.length;
	    int my = map[0].length;
        System.out.println("======================");
        for(int x=0;x<mx;x++) {
            for (int y = 0; y < my; y++) {
                int a = map[x][y];
                System.out.print(a);
                if(a < 10) {
                    System.out.print(" ");
                }
                if(y != (my-1)) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public void adjustNewMap(){
        int[][] map = this.mapGraphic.clone();
        showMapString(map);
        int mx=map.length;
        int my=map[0].length;
        for(int x = 0; x < mx; x ++){
            for(int y = 0; y < my; y++) {
                if(map[x][y]!=0 && map[x][y]!=26 ) {
                    int mustSet = checkMap(x, y, this.mapGraphic);
                    map[x][y] = mustSet;
                }
            }
        }
        showMapString(map);
        this.setMapGraphic(map);
    }

    int checkMap(int x, int y, int[][] map) {
        int mx=map.length;
        int my=map[0].length;
	    boolean left = false;
	    boolean right = false;
	    boolean top = false;
	    boolean bottom = false;
        // check top an bottom
        if(x == 0 ) {top = true;}
        else if(x == (mx-1)) {bottom = true;}
        else {
            if(map[x-1][y] == 0) top=true;
            if(map[x+1][y] == 0) bottom = true;
        }
	    //check left and right
        if(y==0) { left = true;}
        else if(y==(my-1)) { right = true; }
        else {
            if(map[x][y-1]==0) left = true;
            if(map[x][y+1]==0) right = true;
        }
        // set wall
        if(top && left) {
            if(right) return 19;
            return 10;
        }  else if(bottom && left) {
            if (right) return 21;
            return 11;
        } else  if(bottom && right) {
            return 12;
        } else if(top && right) {
            return 13;
        } else if(left || right) {
            return 20;
        } else if(top || bottom) {
            return 24;
        }
	    return 9;
    }

    //Dynamically Generate Map Segments
    public void adjustMap(){
        int[][] map = this.mapGraphic.clone();
        int mx=map.length;
        int my=map[0].length;
        showMapString(map);
        for(int y=0;y<my;y++){
            for(int x=0;x<mx;x++){
                boolean l = false;
                boolean r = false;
                boolean t = false;
                boolean b = false;
                boolean tl = false;
                boolean tr = false;
                boolean bl = false;
                boolean br = false;

                if(map[x][y]>0 && map[x][y]<26) {
                    int mustSet = 0;
                    //LEFT
                    if (x > 0 && map[x - 1][y] > 0 && map[x-1][y]<26) {
                        l = true;
                    }
                    //RIGHT
                    if (x < mx - 1 && map[x + 1][y] > 0 && map[x+1][y]<26) {
                        r = true;
                    }
                    //TOP
                    if (y > 0 && map[x][y - 1] > 0 && map[x][y-1]<26) {
                        t = true;
                    }
                    //Bottom
                    if (y < my - 1 && map[x][y + 1] > 0 && map[x][y+1]<26) {
                        b = true;
                    }
                    //TOP LEFT
                    if (x > 0 && y > 0 && map[x - 1][y - 1] > 0 && map[x-1][y-1]<26) {
                        tl = true;
                    }
                    //TOP RIGHT
                    if (x < mx - 1 && y > 0 && map[x + 1][y - 1] > 0 && map[x+1][y-1]<26) {
                        tr = true;
                    }
                    //Bottom LEFT
                    if (x > 0 && y < my - 1 && map[x - 1][y + 1] > 0 && map[x-1][y+1]<26) {
                        bl = true;
                    }
                    //Bottom RIGHT
                    if (x < mx - 1 && y < my - 1 && map[x + 1][y + 1] > 0 && map[x+1][y+1]<26) {
                        br = true;
                    }

                    //Decide Image to View
                    if (!r && !l && !t && !b) {
                        mustSet = 23;
                    }
                    if (r && !l && !t && !b) {
                        mustSet = 22;
                    }
                    if (!r && l && !t && !b) {
                        mustSet = 25;
                    }
                    if (!r && !l && t && !b) {
                        mustSet = 21;
                    }
                    if (!r && !l && !t && b) {
                        mustSet = 19;
                    }
                    if (r && l && !t && !b) {
                        mustSet = 24;
                    }
                    if (!r && !l && t && b) {
                        mustSet = 20;
                    }
                    if (r && !l && t && !b && !tr) {
                        mustSet = 11;
                    }
                    if (r && !l && t && !b && tr) {
                        mustSet = 2;
                    }
                    if (!r && l && t && !b && !tl) {
                        mustSet = 12;
                    }
                    if (!r && l && t && !b && tl) {
                        mustSet = 3;
                    }
                    if (r && !l && !t && b && br) {
                        mustSet = 1;
                    }
                    if (r && !l && !t && b && !br) {
                        mustSet = 10;
                    }
                    if (!r && l && !t && b && bl) {
                        mustSet = 4;
                    }
                    if (r && !l && t && b && !tr) {
                        mustSet = 15;
                    }
                    if (r && !l && t && b && tr) {
                        mustSet = 6;
                    }
                    if (!r && l && t && b && !tl) {
                        mustSet = 17;
                    }
                    if (!r && l && t && b && tl) {
                        mustSet = 8;
                    }
                    if (r && l && !t && b && !br) {
                        mustSet = 14;
                    }
                    if (r && l && !t && b && br) {
                        mustSet = 5;
                    }
                    if (r && l && t && !b && !tr) {
                        mustSet = 16;
                    }
                    if (r && l && t && !b && tr) {
                        mustSet = 7;
                    }
                    if (!r && l && !t && b && !bl) {
                        mustSet = 13;
                    }
                    if (r && l && t && b && br && tl) {
                        mustSet = 9;
                    }
                    if (r && l && t && b && !br && !tl) {
                        mustSet = 18;
                    }
                    //System.out.println("MAP SEGMENT : " + mustSet);
                    map[x][y] = mustSet;
                }
            }
        }
        this.setMapGraphic(map);
        showMapString(map);
        System.out.println("Map Adjust OK !");
    }

}
