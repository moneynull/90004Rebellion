import static java.lang.Math.exp;
import static java.lang.Math.floor;

public class Agent extends Person{
    private final double governmentLegitimacy;
    private final double perceivedHardship;
    private final double riskAversion;

    private int jailTerm = 0;

    public int getJailTerm() {
        return jailTerm;
    }

    public void setJailTerm(int jailTerm) {
        this.jailTerm = jailTerm;
    }

    public Agent( double perceivedHardship, double riskAversion, double governmentLegitimacy) {
        this.setPersonStatus(RebelParam.AGENT_QUIET);
        this.governmentLegitimacy = governmentLegitimacy;
        this.perceivedHardship = perceivedHardship;
        this.riskAversion = riskAversion;
        this.jailTerm = 0;
    }

    public void jailByTurn(int turn){
        if(this.jailTerm>0){
            this.jailTerm-=turn;
            if(this.jailTerm==0){
                this.setPersonStatus(RebelParam.AGENT_QUIET);
                this.getCell().setPersonStatus(this.getPersonStatus());
            }
        }

    }

    public void determineBehaviour(){
        if(jailTerm<=0){
            if(grievance()-riskAversion*arrestProbability()>RebelParam.THRESHOLD)
                this.setPersonStatus(RebelParam.AGENT_ACTIVE);
            else
                this.setPersonStatus(RebelParam.AGENT_QUIET);

            this.getCell().setPersonStatus(this.getPersonStatus());
        }

    }

    private double grievance(){
        return perceivedHardship * (1 - governmentLegitimacy);
    }

    private double arrestProbability(){
        double rebelsInVision = 0;
        double copsInVision = 0;
        //count cop and rebel agent in vision
        for (Cell cell : this.getCell().getCellsInVision()) {
            if (cell.getPersonStatus().equals(RebelParam.COP))
                copsInVision += 1;
            if (cell.getPersonStatus().equals(RebelParam.AGENT_ACTIVE)) {
                rebelsInVision += 1;
            }
        }

        // calculate estimated arrest probability
        return 1 - exp(-RebelParam.K * floor(copsInVision / rebelsInVision));
    }


}