import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Cop extends Person{

    public Cop() {
        super();
        this.setPersonStatus(RebelParam.COP);
    }

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
