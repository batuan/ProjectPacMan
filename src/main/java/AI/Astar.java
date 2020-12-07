package AI;

import java.awt.*;
import java.util.*;
import java.util.List;

class Cell implements Comparable<Cell>{
    public int x;
    public int y;
    public int G;
    public int H;
    public Cell parent;
    int value = 0;
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Cell o) {
        if(o == null) return -1;
        if (G + H > o.G + o.H)
            return 1;
        else if (G + H < o.G + o.H) return -1;
        return 0;
    }
}
public class Astar {
    //ref: https://github.com/ClaymanTwinkle/astar
    public final static int BAR = 1;
    public final static int PATH = 2;
    public final static int DIRECT_VALUE = 10;
    public final static int OBLIQUE_VALUE = 14;
    Cell matrix[][];

    Queue<Cell> openList; // 优先队列(升序)
    List<Cell> closeList;

    //We use stack because the path is from the endCell -> startCell, so stack is good idea.
    Stack<Point> solution;

    Cell endCell;
    Cell startCell;

    public static void main(String[] args) {
        int[][] walls = {{2, 2}, {2, 3}, {2, 10}, {2, 11}, {2, 12}, {3, 3}, {3, 9}, {3, 10},
                        {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}, {5, 3},
                         {5, 6}, {6, 3}, {6, 8}};
        Astar astar = new Astar(15, 7, 1, 1, 5, 4, walls);
        astar.start();
//        for (int[] i : walls) {
//            System.out.println(i[0]+" "+ i[1]);
//        }
    }
    static int width;
    static int height;
    public Astar(int w, int h, int xStart, int yStart, int xEnd, int yEnd, int[][] wallPoint) {
        width = w;
        height = h;
        matrix = new Cell[h][w];
        solution = new Stack<>();
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();

        this.startCell = new Cell(xStart, yStart);
        this.endCell = new Cell(xEnd, yEnd);
        for(int i = 0 ; i < h; i++){
            for(int j = 0; j < w; j++){
                matrix[i][j] = new Cell(i,j);
                matrix[i][j].H = heuristicDistance(matrix[i][j], endCell);
            }
        }
        // add wall to the map.
        System.out.println(wallPoint.length);
        System.out.println(wallPoint[0].length);
        for (int[] i : wallPoint) {
            matrix[i[0]][i[1]] = null;
        }
        showMap();
    }

    public void showMap(){
        System.out.println("start: " + startCell.x + " " + startCell.y);
        System.out.println("end: " + endCell.x + " " + endCell.y);
        for(int i = 0 ; i < height; i++){
            for(int j = 0; j < width; j++){
                if (matrix[i][j]!=null) {
                    if (matrix[i][j].x == endCell.x && matrix[i][j].y == endCell.y){
                        System.out.print("E ");
                    } else if(matrix[i][j].x == startCell.x && matrix[i][j].y == startCell.y) {
                        System.out.print("S ");
                    } else {
                        System.out.print(matrix[i][j].value + " ");
                    }
                } else {
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }
    }

    public void start() {
        if(matrix==null) return;
        // clean
        openList.clear();
        closeList.clear();
        openList.add(startCell);
        moveNodes();
    }

    private void moveNodes() {
        while (!openList.isEmpty())
        {
            Cell current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(current);
            if (isCoordInClose(endCell.x, endCell.y)) {
                drawPath(endCell);
                break;
            }
        }
    }

    private void drawPath(Cell end) {
        System.out.println("Total cost：" + endCell.G);
        while (end != null) {
            matrix[end.x][end.y].value = 2;
            solution.push(new Point(end.x, end.y));
//            System.out.print("cell: " + end.x + " - " + end.y + " <- ");
            end = end.parent;
        }
        System.out.println();
        showMap();
        drawSolution();
    }
    public void drawSolution() {
        Stack<Point> temp = (Stack<Point>) solution.clone();
        while (!temp.empty()) {
            Point cell = temp.pop();
            System.out.print("("+cell.x + "; " + cell.y + ") -> ");
        }
        System.out.println();
    }

    public Stack<Point> getSolution() {
        return solution;
    }

    /**
     * add to open
     */
    private void addNeighborNodeInOpen(Cell current)
    {
        int x = current.x;
        int y = current.y;
        // LEFT
        addNeighborNodeInOpen(current, x - 1, y, DIRECT_VALUE);
        // UP
        addNeighborNodeInOpen(current, x, y - 1, DIRECT_VALUE);
        // RIGHT
        addNeighborNodeInOpen(current, x + 1, y, DIRECT_VALUE);
        // DOWN
        addNeighborNodeInOpen(current, x, y + 1, DIRECT_VALUE);
    }

    /**
     * Extend the current cell and find the cell can add to the open list
     * @param current
     * @param x
     * @param y
     * @param value
     */
    private void addNeighborNodeInOpen(Cell current, int x, int y, int value)
    {
        if (canAddNodeToOpen(x, y))
        {
            Cell coord = new Cell(x, y);
            int G = current.G + value; // 计算邻结点的G值
            Cell child = findNodeInOpen(coord);
            if (child == null) {
                int H = heuristicDistance(endCell, coord); // 计算H值
                if(isEndNode(endCell, coord)) {
                    child=endCell;
                    child.parent=current;
                    child.G=G;
                    child.H=H;
                }
                else {
                    //child = new Node(coord, current, G, H);
                    child = new Cell(coord.x, coord.y);
                    child.parent = current;
                    child.G = G;
                    child.H = H;
                }
                openList.add(child);
            }
            else if (child.G > G) {
                child.G = G;
                child.parent = current;
                openList.add(child);
            }
        }
    }

    /**
     * Calculate the H.
     * @param a cell 1
     * @param b cell 2
     * @return the manhattan distance beetween two cell, it is using for calculate the heuristic distance.
     */

    int heuristicDistance(Cell a, Cell b) {
        // Manhattan distance on a square grid
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * Find node in the open list
     * @param cell coordinate
     * @return null if open list not have, node if can find node.
     */

    private Cell findNodeInOpen(Cell cell)
    {
        if (cell.x < 0 || cell.y < 0 || openList.isEmpty()) return null;
        for (Cell node : openList)
        {
            if (node.x == cell.x && node.y ==cell.y )
            {
                return node;
            }
        }
        return null;
    }

    /*
        check if the goal is targeted
     */
    private boolean isEndNode(Cell end , Cell current)
    {
        return end.x == current.x && end.y == current.y;
    }

    /**
     * Determine whether the node can be placed in the Open list
     */
    private boolean canAddNodeToOpen(int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= height || y < 0 || y >= width) return false;
        // 判断是否是不可通过的结点
        if (matrix[x][y] == null) return false;
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)) return false;
        return true;
    }

    /**
     * Determine whether the coordinates are in the close table
     */
//    private boolean isCellInClose(Coord coord)
//    {
//        return coord!=null&&isCoordInClose(coord.x, coord.y);
//    }

    /**
     * Determine whether the coordinates are in the close table
     */
    private boolean isCoordInClose(int x, int y) {
        if (closeList.isEmpty()) return false;
        for (Cell node : closeList)
        {
            if (node.x == x && node.y == y)
            {
                return true;
            }
        }
        return false;
    }

}
/**
 0 1
 1 0
 2 1
 1 2
 2 0
 3 1
 0 2
 1 3
 0 0
 3 0
 4 1
 0 3
 2 3
 1 4
 2 4
 4 0
 5 1
 4 2
 0 4
 1 5
 0 5
 2 5
 1 6
 5 2
 4 3
 5 0
 6 1
 5 3
 2 6
 6 2
 0 6
 6 0
 7 1
 6 3
 7 2
 7 0
 8 1
 7 3
 8 2
 8 0
 9 1
 8 3
 9 2
 9 0
 10 1
 10 0
 11 1
 11 0
 12 1
 12 0
 13 1
 13 0
 14 1
 13 2
 14 2
 13 3
 14 0
 12 3
 14 3
 13 4
 12 4
 14 4
 13 5
 11 3
 11 4
 12 5
 14 5
 13 6
 11 5
 12 6
 10 4
 14 6
 10 5
 11 6
 9 5
 10 6
 8 5
 9 6
 7 5
 7 6
 6 6
 5 6
 4 6
 5 5
 4 5
 */