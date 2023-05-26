import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Spy in agent performance identical with agent
 * But spy in agent could jail active agent when at least one cop in vision
 */
public class CopSpyInAgent extends Agent{
    public CopSpyInAgent(double perceivedHardship, double riskAversion, double governmentLegitimacy) {
        super(perceivedHardship, riskAversion, governmentLegitimacy);
    }

    /**
     * Spy in agent with at least one cop in vision
     * could enforce and jail one active agent each turn
     */
    public void spyEnforce(){
        List<String> stringList=this.getCell().getCellsInVision()
                .stream().map(Cell::getPersonStatus).collect(Collectors.toList());
        if(stringList.contains(RebelParam.COP)){
            enforce();
            //System.out.println("cop spy in agent jailed an agent!");
        }
    }

    /**
     * Same with the enforce behavior of Cop
     */
    public void enforce(){
        Random random=new Random();
        //find rebel cells
        List<Person> rebelList=RebelMap.personList.stream()
                .filter(p->(
                        p.getPersonStatus().equals(RebelParam.AGENT_ACTIVE)&&
                                this.getCell().getCellsInVision().contains(p.getCell())
                ))
                .collect(Collectors.toList());

        //find rebel agents based on cells
        if(rebelList.size()>0&&RebelParam.MAX_JAIL_TERM>0)
            //jail one random agent
            jailAgent((Agent) rebelList.get(random.nextInt(rebelList.size())),
                    random.nextInt(RebelParam.MAX_JAIL_TERM));
    }

    private void jailAgent(Agent agent,int jailTerm){
        if(jailTerm!=0){
            //change agent status
            agent.setPersonStatus(RebelParam.AGENT_JAILED);

            agent.setJailTerm(jailTerm);

            //reset jailed agent cell
            this.getCell().setPersonStatus(RebelParam.EMPTY_SLOT);
            this.setCell(agent.getCell());
            this.getCell().setPersonStatus(this.getPersonStatus());
        }

    }

}
