public class Rebellion {
    public static RebelMonitor monitor=new RebelMonitor();
    public static void main(String[] args) throws Exception {
        initialModel();
        //MapUI mapUI=new MapUI();
        runModel();
        monitor.exportDataToCSV();
    }

    public static void initialModel() throws Exception {
        // check densities valid
        if (RebelParam.INITIAL_AGENT_DENSITY + RebelParam.INITIAL_COP_DENSITY > 1) {
            throw new Exception("Sum of INITIAL-COP-DENSITY and INITIAL-AGENT-DENSITY should be less than 1.");
        }
        RebelMap.initialMap();
        RebelMap.initialCellList();
        RebelMap.initialAgent();
        RebelMap.initialCop();
        RebelMap.setupPerson();
    }

    public static void runModel(){
        for(int t=0;t<RebelParam.MAX_TURN;t++){
            monitor.agentsMonitor(RebelMap.personList);
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

    public static void modelThread(){
        while(true){
            monitor.agentsMonitor(RebelMap.personList);
            RebelMap.personList.forEach(Person::randomMove);
            //todo separate or not?
            RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).determineBehaviour();});
            RebelMap.personList.forEach(p->{if(p instanceof Cop)((Cop) p).enforce();});
            RebelMap.personList.forEach(p->{if(p instanceof Agent)((Agent) p).jailByTurn(1);});
        }
    }
}
