import java.util.Collections;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Main class
 */
public class Rebellion {
    public static void main(String[] args) throws Exception {
        MapUI mapUI=new MapUI();
    }

    /**
     * initial map and basic information
     */
    public static void initialModel(){
        RebelMap.cleanMap();
        RebelMap.initialMap();
        RebelMap.initialCellList();
        RebelMap.initialAgent();
        RebelMap.initialCop();
        RebelMap.setupPerson();
    }

    /**
     * simulate agent and cop one turn behavior
     */
    public static void modelThread(){
        RebelMap.personList.forEach(p->{
            //movement control
            if(RebelParam.MOVEMENT){
                if(!p.getPersonStatus().equals(RebelParam.AGENT_JAILED)){
                    p.randomMove();
                }
            }
            else if(p instanceof Cop) p.randomMove();

            //normal agent and cop do behavior
            if(p instanceof Agent)((Agent) p).determineBehaviour();
            if(p instanceof CopSpyInAgent) ((CopSpyInAgent) p).determineBehaviour();
            if(p instanceof Cop)((Cop) p).enforce();

            //spy agent and cop do behavior
            if(p instanceof AgentSpyInCop) ((AgentSpyInCop) p).releaseAgent();
            if(p instanceof CopSpyInAgent)((CopSpyInAgent) p).spyEnforce();

            if(p instanceof Agent)((Agent) p).jailByTurn();
        });

        //refresh jail information for all cells to paint map
        RebelMap.personList.forEach(p->{
            p.getCell().setHasJailed(
                    p.getPersonStatus().equals(RebelParam.AGENT_JAILED) &&
                            p.getCell().getPersonStatus().equals(RebelParam.EMPTY_SLOT));
        });

        //shuffle list to refresh the order of each element in list
        Collections.shuffle(RebelMap.personList);

    }
}
