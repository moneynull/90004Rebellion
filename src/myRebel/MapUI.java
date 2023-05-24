package myRebel;

import javax.swing.*;
import java.awt.*;

public class MapUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Thread UIThread;

    public MapUI()
    {
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(RebelParam.MAP_COL, RebelParam.MAP_ROW));
        frame.add(grid);

        UIThread=new Thread(){
            public void run(){
                super.run();
                while(true){

                    for (int i = 0; i < RebelParam.MAP_COL; i++) {
                        for (int j = 0; j < RebelParam.MAP_ROW; j++) {
                            String cellStatus=RebelMap.map[i][j].getPersonStatus();
                            String str = cellStatus.substring(0,1);
                            JLabel x = new JLabel(str);
                            x.setOpaque(true);

                            x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            if(cellStatus.equals(RebelParam.COP))
                                x.setBackground(Color.BLUE);
                            if(cellStatus.equals(RebelParam.AGENT_QUIET))
                                x.setBackground(Color.GREEN);
                            if(cellStatus.equals(RebelParam.AGENT_ACTIVE))
                                x.setBackground(Color.RED);
                            if(cellStatus.equals(RebelParam.AGENT_JAILED))
                                x.setBackground(Color.BLACK);
                            if(cellStatus.equals(RebelParam.EMPTY_SLOT))
                                x.setBackground(Color.GRAY);
                            grid.add(x);
                        }
                    }

                    grid.removeAll();

                }
            }

        };
        UIThread.start();
        frame.setVisible(true);

    }
}
