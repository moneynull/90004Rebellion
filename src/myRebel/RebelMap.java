package myRebel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RebelMap {
    public static Cell[][] map=new Cell[RebelParam.MAP_ROW][RebelParam.MAP_COL];
    public static List<Person> personList=new ArrayList<>();
    public static List<Cell> cellList=new ArrayList<>();

    public static int agentNum=(int)Math.floor(
            RebelParam.INITIAL_AGENT_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));
    public static int copNum=(int)Math.floor(
            RebelParam.INITIAL_COP_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));

    public static void initialMap(){
        //initial cell grid
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++)
                map[i][j]=new Cell(i,j,RebelParam.EMPTY_SLOT);
        }
    }

    public static void initialCellList(){
        //initial cell list in vision for each cell
        for (Cell[] cells : map)
            for (Cell cell : cells){
                cell.setCellsInVision(RebelParam.VISION);
                cellList.add(cell);
            }
    }

    public static void initialAgent(){
        Random random=new Random();
        for(int i=0;i<agentNum;i++)
            personList.add(new Agent(random.nextDouble(), random.nextDouble(), RebelParam.GOVERNMENT_LEGITIMACY));
    }

    public static void initialCop(){
        for(int i=0;i<copNum;i++)
            personList.add(new Cop());
    }

    public static void setupPerson(){
        Random random=new Random();
        for(Person person:personList){
            int index=random.nextInt(cellList.size());
            cellList.get(index).setPersonStatus(person.getPersonStatus());
            person.setCell(cellList.remove(index));
        }
        cellList.clear();
    }


}
