import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Extension cop behavior:
 * The spy in cop group could release one random jailed agent in vision each turn
 */
public class AgentSpyInCop extends Cop{

    public void releaseAgent(){
        List<Agent> jailedList=new ArrayList<>();
        Random random=new Random();

        //Get available release list
        for(Person person:RebelMap.personList){
            if(person instanceof Agent)
                if(this.getCell().getCellsInVision().contains(person.getCell())&&
                        person.getPersonStatus().equals(RebelParam.AGENT_JAILED)&&
                        person.getCell().getPersonStatus().equals(RebelParam.EMPTY_SLOT)&&
                        ((Agent) person).getJailTerm()>1){
                    jailedList.add((Agent) person);
                }
        }

        //move to the jailed agent and release if any
        if(jailedList.size()>0){
            Agent agent=jailedList.get(random.nextInt(jailedList.size()));
            //agent.setPersonStatus(RebelParam.AGENT_QUIET);
            agent.setJailTerm(1);
            this.getCell().setPersonStatus(RebelParam.EMPTY_SLOT);
            this.setCell((agent.getCell()));
            this.getCell().setPersonStatus(this.getPersonStatus());
            //System.out.println("agent spy in cop release an agent!");
        }

    }
}
