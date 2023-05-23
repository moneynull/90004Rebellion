package myRebel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.exp;
import static java.lang.Math.floor;

public class Agent {
    private final double governmentLegitimacy;
    private final double perceivedHardship;
    private final double riskAversion;

    private int jailTerm = 0;
    private Cell cell;
    private String personStatus;

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public int getJailTerm() {
        return jailTerm;
    }

    public void setJailTerm(int jailTerm) {
        this.jailTerm = jailTerm;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Agent(double governmentLegitimacy, double perceivedHardship, double riskAversion) {
        this.personStatus = RebelParam.AGENT_QUIET;
        this.governmentLegitimacy = governmentLegitimacy;
        this.perceivedHardship = perceivedHardship;
        this.riskAversion = riskAversion;
        this.jailTerm = 0;
    }

    public void determineBehaviour(){
        if(jailTerm<=0 &&
                (grievance()-riskAversion*arrestProbability()>RebelParam.THRESHOLD)){
            this.personStatus=RebelParam.AGENT_ACTIVE;
            this.cell.setPersonStatus(this.personStatus);
        }

    }

    private double grievance(){
        return perceivedHardship * (1 - governmentLegitimacy);
    }

    private double arrestProbability(){
        double rebelsInVision = 0;
        double copsInVision = 0;
        //count cop and rebel agent in vision
        for (Cell cell : getCell().getCellsInVision()) {
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
