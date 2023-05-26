import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * The RebelMonitor class
 * Used for monitor and record the output data of agent
 */
public class RebelMonitor {
    private List<Integer> activeData=new ArrayList<>();
    private List<Integer> quietData=new ArrayList<>();
    private List<Integer> jailedData=new ArrayList<>();
    public int activeNum=0;
    public int quietNum=0;
    public int jailedNum=0;
    public final static String COL_HEADERS = "Turn,Active,Quiet,Jailed";

    public void initMonitor(){
        activeData=new ArrayList<>();
        quietData=new ArrayList<>();
        jailedData=new ArrayList<>();
    }

    //monitor and record the number of types of agent
    public void agentsMonitor(List<Person> agents) {
        activeNum = 0;
        quietNum = 0;
        jailedNum = 0;
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

    //Export the record data to csv file for each run
    public void exportDataToCSV(int turn) {
        try {
            String time = ""+LocalDateTime.now().getHour()+"-"
                    +LocalDateTime.now().getMinute()+"-"
                    +LocalDateTime.now().getSecond();
            FileWriter myWriter = new FileWriter("ModelData_"+time+ ".csv");
            myWriter.write(dataToString(turn));
            myWriter.close();

            //notice success
            JOptionPane.showConfirmDialog(
                    null,
                    "Model has been run. Run data saved to csv file.",
                    "Success",
                    JOptionPane.OK_CANCEL_OPTION
            );

        } catch (IOException e) {
            //notice error
            JOptionPane.showConfirmDialog(
                    null,
                    "Save to csv file failed.",
                    "Error",
                    JOptionPane.OK_CANCEL_OPTION
            );
            e.printStackTrace();
        }
    }

    //format data structure
    public String dataToString(int turn) {
        StringBuilder out = new StringBuilder(COL_HEADERS + "\n");
        for(int i=0;i<turn;i++) {
            out.append(dataAtTurn(i)).append("\n");
        }
        return out.toString();
    }

    public String dataAtTurn(int turn) {
        return String.format("%d,%d,%d,%d", turn, activeData.get(turn), quietData.get(turn), jailedData.get(turn));
    }
}
