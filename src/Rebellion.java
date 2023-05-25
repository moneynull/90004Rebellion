import java.util.Collections;

public class Rebellion {
    public static void main(String[] args) throws Exception {
        //initialModel();
        MapUI mapUI=new MapUI();
        //runModel();
        //monitor.exportDataToCSV(mapUI.getRunTurn());
    }

    public static void initialModel(){
        RebelMap.cleanMap();
        RebelMap.initialMap();
        RebelMap.initialCellList();
        RebelMap.initialAgent();
        RebelMap.initialCop();
        RebelMap.setupPerson();
    }

    public static void runModel(){
        for(int t=0;t<RebelParam.MAX_TURN;t++)
            modelThread();
    }

    public static void modelThread(){
        RebelMap.personList.forEach(p->{
            if(RebelParam.MOVEMENT){
                if(!p.getPersonStatus().equals(RebelParam.AGENT_JAILED)){
                    p.randomMove();
                }
            }
            else if(p instanceof Cop) p.randomMove();
            if(p instanceof Agent)((Agent) p).determineBehaviour();
            if(p instanceof Cop)((Cop) p).enforce();
            if(p instanceof Agent)((Agent) p).jailByTurn();

        });
//        RebelMap.personList.forEach(Person::randomMove);
//        RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).determineBehaviour();});
//        RebelMap.personList.forEach(p->{if(p instanceof Cop)((Cop) p).enforce();});
//        RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).jailByTurn();});
        Collections.shuffle(RebelMap.personList);
    }
}
