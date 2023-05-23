package myRebel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Cop {
    private Cell cell;
    private String personStatus;

    public void enforce(){
        Random random=new Random();
        //find rebel cells
        List<Cell> cellList=cell.getCellsInVision();
        cellList=cellList.stream()
                .filter(c-> c.getPersonStatus().equals(RebelParam.AGENT_ACTIVE))
                .collect(Collectors.toList());

        //find rebel agents based on cells
        if(cellList.size()>0){
            List<Agent> rebelList=new ArrayList<>();
            for (Cell cell:cellList){
                rebelList.add(RebelMap.agentList.stream().filter(a-> a.getCell()==cell).findAny().get());
            }
            //jail one random agent
            jailAgent((rebelList.get(random.nextInt(rebelList.size()))));
        }
    }

    private void jailAgent(Agent agent){
        Random random=new Random();
        //change agent status
        agent.setPersonStatus(RebelParam.AGENT_JAILED);
        agent.setJailTerm(random.nextInt(RebelParam.MAX_JAIL_TERM));
        //reset jailed agent cell
        cell.setPersonStatus(RebelParam.EMPTY_SLOT);
        cell=agent.getCell();
        cell.setPersonStatus(personStatus);
    }

}
