package myRebel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RebelMap {
    public static Cell[][] map=new Cell[RebelParam.MAP_ROW][RebelParam.MAP_COL];
    public static List<Agent> agentList=new ArrayList<>();
    public static List<Cop> copList=new ArrayList<>();

    public static void initialMap(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++)
                map[i][j]=new Cell(i,j,RebelParam.EMPTY_SLOT);
        }
    }

    public static void initialAgent(){

    }

    public static void initialCop(){

    }

    public static void randomMove(Cell cell, String personStatus){
        List<Cell> emptyCells=cell.getCellsInVision().stream()
                .filter(c->c.getPersonStatus().equals(RebelParam.EMPTY_SLOT))
                .collect(Collectors.toList());

        //move to a random empty cell
        if(emptyCells.size()>0){
            Random random=new Random();
            Cell newLoc=emptyCells.get(random.nextInt(emptyCells.size()));
            cell.setPersonStatus(RebelParam.EMPTY_SLOT);
            cell=newLoc;
            cell.setPersonStatus(personStatus);
        }
    }

}
