package myRebel;

public class Rebellion {
    public static void main(String[] args) throws Exception {
        RebelMap.initialMap();
        RebelMap.initialAgent();
        RebelMap.initialCop();
        Cell cell=new Cell(0,0,RebelParam.AGENT_ACTIVE);
        cell.setCellsInVision(RebelParam.VISION);
        RebelMap.randomMove(cell,cell.getPersonStatus());
        for(int i=0;i<RebelMap.map.length;i++)
            for(int j=0;j<RebelMap.map[i].length;j++)
            System.out.println(RebelMap.map[i][j]);
    }
}
