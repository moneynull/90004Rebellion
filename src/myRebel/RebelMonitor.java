package myRebel;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RebelMonitor {
    private List<Integer> activeData=new ArrayList<>();
    private List<Integer> quietData=new ArrayList<>();
    private List<Integer> jailedData=new ArrayList<>();
    public final static String COL_HEADERS = "Turn,Active,Quiet,Jailed";

    public void agentsMonitor(List<Person> agents) {
        int activeNum = 0;
        int quietNum = 0;
        int jailedNum = 0;
        for (Person agent : agents) {
            if (agent.getPersonStatus().equals(RebelParam.AGENT_ACTIVE))
                activeNum += 1;
            if(agent.getPersonStatus().equals(RebelParam.AGENT_QUIET))
                quietNum += 1;
            if(agent.getPersonStatus().equals(RebelParam.AGENT_JAILED))
                jailedNum += 1;
        }
        activeData.add(activeNum);
        quietData.add(quietNum);
        jailedData.add(jailedNum);
    }

    public void exportDataToCSV() {
        try {
            String time = ""+LocalDateTime.now().getHour()+"-"
                    +LocalDateTime.now().getMinute()+"-"
                    +LocalDateTime.now().getSecond();
            FileWriter myWriter = new FileWriter("ModelData_"+time+ ".csv");
            myWriter.write(dataToString());
            myWriter.close();
            System.out.println("Model has been run. Run data saved to csv file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String dataToString() {
        StringBuilder out = new StringBuilder(COL_HEADERS + "\n");
        for(int i=0;i<RebelParam.MAX_TURN;i++) {
            out.append(dataAtTurn(i)).append("\n");
        }
        return out.toString();
    }

    public String dataAtTurn(int turn) {
        return String.format("%d,%d,%d,%d", turn, activeData.get(turn), quietData.get(turn), jailedData.get(turn));
    }
}
