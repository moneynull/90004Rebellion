import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Agent spy in cop could help active agent in range to escape
 */
public class AgentSpyInCop extends Cop{

    public void releaseAgent(){
        List<Agent> jailedList=new ArrayList<>();
        Random random=new Random();

        for(Person person:RebelMap.personList){
            if(person instanceof Agent)
                if(this.getCell().getCellsInVision().contains(person.getCell())&&
                        person.getPersonStatus().equals(RebelParam.AGENT_JAILED)&&
                        ((Agent) person).getJailTerm()>1){
                    jailedList.add((Agent) person);
                }
        }

        if(jailedList.size()>0){
            Agent agent=jailedList.get(random.nextInt(jailedList.size()));
            //agent.setPersonStatus(RebelParam.AGENT_QUIET);
            agent.setJailTerm(1);
            System.out.println("agent spy in cop release an agent!");
        }

    }
}
