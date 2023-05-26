import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Static map, the system will only have one map
 */
public class RebelMap {
    //map consist of cells
    public static Cell[][] map=new Cell[RebelParam.MAP_ROW][RebelParam.MAP_COL];
    //record all the person info
    public static List<Person> personList=new ArrayList<>();
    //record all the cell info
    public static List<Cell> cellList=new ArrayList<>();

    //init number of agent and cop
    public static int agentNum=(int)Math.floor(
            RebelParam.INITIAL_AGENT_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));
    public static int copNum=(int)Math.floor(
            RebelParam.INITIAL_COP_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));

    //init number of spy
    public static int spyInAgentNum=(int)Math.floor(agentNum*RebelParam.SPY_IN_AGENT_DENSITY);
    public static int spyInCopNum=(int)Math.floor(copNum*RebelParam.SPY_IN_COP_DENSITY);

    //refresh the map every time setup
    public static void cleanMap(){
        map=new Cell[RebelParam.MAP_ROW][RebelParam.MAP_COL];
        personList=new ArrayList<>();
        cellList=new ArrayList<>();
        agentNum=(int)Math.floor(RebelParam.INITIAL_AGENT_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));
        copNum=(int)Math.floor(RebelParam.INITIAL_COP_DENSITY*(RebelParam.MAP_ROW* RebelParam.MAP_COL));
        spyInAgentNum=(int)Math.floor(agentNum*RebelParam.SPY_IN_AGENT_DENSITY);
        spyInCopNum=(int)Math.floor(copNum*RebelParam.SPY_IN_COP_DENSITY);
    }

    //init cells in map
    public static void initialMap(){
        //initial cell grid
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++)
                map[i][j]=new Cell(i,j,RebelParam.EMPTY_SLOT);
        }
    }

    //calculate and store cell list in vision for each cells
    public static void initialCellList(){
        //initial cell list in vision for each cell
        for (Cell[] cells : map)
            for (Cell cell : cells){
                cell.setCellsInVision(RebelParam.VISION);
                cellList.add(cell);
            }
    }

    //new agent
    public static void initialAgent(){
        Random random=new Random();
        for(int i=0;i<agentNum-spyInAgentNum;i++)
            personList.add(new Agent(
                    random.nextDouble(), random.nextDouble(), RebelParam.GOVERNMENT_LEGITIMACY));

        //System.out.println(spyInAgentNum);
        for(int i=0;i<spyInAgentNum;i++)
            personList.add(new CopSpyInAgent(
                    random.nextDouble(), random.nextDouble(), RebelParam.GOVERNMENT_LEGITIMACY));
    }

    //new cop
    public static void initialCop(){
        for(int i=0;i<copNum-spyInCopNum;i++)
            personList.add(new Cop());

        //System.out.println(spyInCopNum);
        for(int i=0;i<spyInCopNum;i++)
            personList.add(new AgentSpyInCop());
    }

    //arrange person to random cell
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
