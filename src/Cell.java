import java.util.ArrayList;
import java.util.List;

public class Cell {
    //location points
    private final int cellX;
    private final int cellY;
    private String personStatus;

    private List<Cell> cellsInVision;

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public List<Cell> getCellsInVision() {
        return cellsInVision;
    }

    public void setCellsInVision(List<Cell> cellsInVision) {
        this.cellsInVision = cellsInVision;
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public Cell(int cellX, int cellY, String personStatus) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.personStatus = personStatus;
        this.cellsInVision = new ArrayList<>();
    }


    /**
     * using bfs to reset cells in vision
     * @param vision
     */
    public void setCellsInVision(int vision){
        cellBFS(vision,cellX,cellY);
        this.cellsInVision.remove(this);
    }

    //bfs all cells in vision
    private void cellBFS(int vision,int x, int y){
        if (vision > 0) {
            // coordinates of adjacent cells
            int[][] neighbours = {{x,y-1},{x-1,y},{x,y+1},{x+1,y}};
            for (int[] neighbour : neighbours) {
                int newX = neighbour[0];
                int newY = neighbour[1];

                // handle edge of map
                if (newX < 0) newX = RebelParam.MAP_COL - 1;
                if (newX >= RebelParam.MAP_COL) newX = 0;
                if (newY < 0) newY = RebelParam.MAP_ROW - 1;
                if (newY >= RebelParam.MAP_ROW) newY = 0;

                // add cell if not yet visited and recurse deeper
                Cell cell = RebelMap.map[newX][newY];
                if (!cellsInVision.contains(cell)) {
                    cellsInVision.add(cell);
                    cellBFS(vision-1, newX, newY);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "cellX=" + cellX +
                ", cellY=" + cellY +
                ", personStatus='" + personStatus + '\'' +
                '}';
    }
}
