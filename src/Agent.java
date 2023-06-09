import static java.lang.Math.exp;
import static java.lang.Math.floor;

/**
 * Author: Xiang Guo
 * The Agent class is used to simulate the agent behavior
 * Calculate individual risk of rebelling
 */

public class Agent extends Person{
    private final double governmentLegitimacy;
    private final double perceivedHardship;
    private final double riskAversion;

    private int jailTerm;

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

    /**
     * decrease jailed agent one term each turn
     */
    public void jailByTurn(){
        if(this.jailTerm>0){
            this.jailTerm-=1;
            if(this.jailTerm==0){
                this.setPersonStatus(RebelParam.AGENT_QUIET);
                this.getCell().setPersonStatus(this.getPersonStatus());
            }
        }

    }

    /**
     * determine active or not
     */
    public void determineBehaviour(){
        if(jailTerm<=0){
            if((grievance()-riskAversion*arrestProbability())>RebelParam.THRESHOLD){
                this.setPersonStatus(RebelParam.AGENT_ACTIVE);
                this.getCell().setPersonStatus(this.getPersonStatus());
            }
        }
    }

    private double grievance(){
        return perceivedHardship * (1 - governmentLegitimacy);
    }

    /**
     * calculate the probability to be arrested
     * @return result in double
     */
    private double arrestProbability(){
        double rebelsInVision = 0;
        double copsInVision = 0;
        //count cop and active agent in vision
        for (Cell cell : this.getCell().getCellsInVision()) {
            if (cell.getPersonStatus().equals(RebelParam.COP))
                copsInVision += 1;
            if (cell.getPersonStatus().equals(RebelParam.AGENT_ACTIVE)) {
                rebelsInVision += 1;
            }
        }

        if(RebelParam.VISION<6) rebelsInVision+=1;
        else if(copsInVision==0) copsInVision=1;

        return 1 - exp(-RebelParam.K * floor((copsInVision) / rebelsInVision));
    }

}
