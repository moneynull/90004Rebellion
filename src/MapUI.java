import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class MapUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Thread UIThread;

    public int getRunTurn() {
        return runTurn;
    }

    private int runTurn;

    public MapUI()
    {
        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closed");
                if (UIThread != null) {
                    UIThread.interrupt();
                }
                System.exit(0);
            }
        });

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(RebelParam.MAP_COL, RebelParam.MAP_ROW));

        frame.add(grid);
        frame.pack();
        runTurn=0;
        UIThread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    Rebellion.modelThread();
                    runTurn++;
                    grid.removeAll();
                    for (int i = 0; i < RebelParam.MAP_COL; i++) {
                        for (int j = 0; j < RebelParam.MAP_ROW; j++) {
                            String cellStatus = RebelMap.map[i][j].getPersonStatus();
                            String str = cellStatus.substring(0, 0).toUpperCase(Locale.ROOT);
                            JLabel x = new JLabel(str);
                            x.setOpaque(true);
                            x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            if (cellStatus.equals(RebelParam.COP))
                                x.setBackground(Color.BLUE);
                            if (cellStatus.equals(RebelParam.AGENT_QUIET))
                                x.setBackground(Color.GREEN);
                            if (cellStatus.equals(RebelParam.AGENT_ACTIVE))
                                x.setBackground(Color.RED);
                            if (cellStatus.equals(RebelParam.AGENT_JAILED))
                                x.setBackground(Color.BLACK);
                            if (cellStatus.equals(RebelParam.EMPTY_SLOT))
                                x.setBackground(Color.GRAY);
                            grid.add(x);
                        }
                    }
                    grid.updateUI();

                    try {
                        Thread.sleep(RebelParam.UI_UPDATE_RATE);
                    } catch (InterruptedException e) {
                        System.out.println("Thread sleep error");
                        break;
                    }
                }

            }

        };
        UIThread.start();
        frame.setVisible(true);

    }
}
