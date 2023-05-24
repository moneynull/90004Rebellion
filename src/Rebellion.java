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
        if(RebelParam.MOVEMENT)
            RebelMap.personList.forEach(Person::randomMove);
        else
            RebelMap.personList.forEach(p->{if(p instanceof Cop)p.randomMove();});
        //todo separate or not?
        RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).determineBehaviour();});
        RebelMap.personList.forEach(p->{if(p instanceof Cop)((Cop) p).enforce();});
        RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).jailByTurn(1);});
    }
}
