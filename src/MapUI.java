import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Show the model
 */
public class MapUI extends JFrame {
    private static final long serialVersionUID = 1L;
    public static RebelMonitor monitor=new RebelMonitor();
    private final Thread UIThread;
    private boolean go=false;

    public int getRunTurn() {
        return runTurn;
    }

    private int runTurn;

    public MapUI()
    {

        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        //Panel for input param
        JPanel inputPanel=new JPanel(new GridLayout(2,7));
        JLabel l1=new JLabel("initial-cop-density");
        JTextField t1=new JTextField(String.valueOf(RebelParam.INITIAL_COP_DENSITY),5);

        JLabel l2=new JLabel("initial-agent-density");
        JTextField t2=new JTextField(String.valueOf(RebelParam.INITIAL_AGENT_DENSITY),5);

        JLabel l3=new JLabel("vision");
        JTextField t3=new JTextField(String.valueOf(RebelParam.VISION),5);

        JLabel l4=new JLabel("govern-legitimacy");
        JTextField t4=new JTextField(String.valueOf(RebelParam.GOVERNMENT_LEGITIMACY),5);

        JLabel l5=new JLabel("max-jail-term");
        JTextField t5=new JTextField(String.valueOf(RebelParam.MAX_JAIL_TERM),5);

        JLabel l11=new JLabel("spy_in_cop_density");
        JTextField t11=new JTextField(String.valueOf(RebelParam.SPY_IN_COP_DENSITY),5);

        JLabel l12=new JLabel("spy_in_agent_density");
        JTextField t12=new JTextField(String.valueOf(RebelParam.SPY_IN_AGENT_DENSITY),5);

        inputPanel.add(l1);
        inputPanel.add(l2);
        inputPanel.add(l3);
        inputPanel.add(l4);
        inputPanel.add(l5);
        inputPanel.add(l11);
        inputPanel.add(l12);

        inputPanel.add(t1);
        inputPanel.add(t2);
        inputPanel.add(t3);
        inputPanel.add(t4);
        inputPanel.add(t5);
        inputPanel.add(t11);
        inputPanel.add(t12);

        frame.add(inputPanel,BorderLayout.NORTH);

        //panel for function button
        JPanel buttonPanel=new JPanel();
        JLabel l6=new JLabel("quiet");
        JTextField t6=new JTextField(String.valueOf(monitor.quietNum),5);
        t6.setEnabled(false);
        JLabel l7=new JLabel("jailed");
        JTextField t7=new JTextField(String.valueOf(monitor.jailedNum),5);
        t7.setEnabled(false);
        JLabel l8=new JLabel("active");
        JTextField t8=new JTextField(String.valueOf(monitor.activeNum),5);
        t8.setEnabled(false);

        JLabel l9=new JLabel("interval/ms");
        JTextField t9=new JTextField(String.valueOf(RebelParam.UI_UPDATE_RATE),5);

        buttonPanel.add(l9);
        buttonPanel.add(t9);
        buttonPanel.add(l6);
        buttonPanel.add(t6);
        buttonPanel.add(l7);
        buttonPanel.add(t7);
        buttonPanel.add(l8);
        buttonPanel.add(t8);

        JButton b1=new JButton("setup");
        JButton b2=new JButton("go");
        JButton b4=new JButton("save");
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b4);

        ButtonGroup buttonGroup=new ButtonGroup();
        JRadioButton r1=new JRadioButton("On",true);
        JRadioButton r2=new JRadioButton("Off",false);
        buttonGroup.add(r1);
        buttonGroup.add(r2);
        buttonPanel.add(new JLabel("Movement?"));
        buttonPanel.add(r1);
        buttonPanel.add(r2);

        frame.add(buttonPanel,BorderLayout.SOUTH);

        JPanel grid = new JPanel();
        grid.setPreferredSize(new Dimension(700, 700));
        grid.setLayout(new GridLayout(RebelParam.MAP_COL, RebelParam.MAP_ROW));

        frame.add(grid);
        frame.pack();

        //setup button listener
        b1.addActionListener(e -> {
            RebelParam.INITIAL_COP_DENSITY=Double.parseDouble(t1.getText());
            RebelParam.INITIAL_AGENT_DENSITY=Double.parseDouble(t2.getText());
            RebelParam.VISION=Integer.parseInt(t3.getText());
            RebelParam.GOVERNMENT_LEGITIMACY=Double.parseDouble(t4.getText());
            RebelParam.MAX_JAIL_TERM=Integer.parseInt(t5.getText());
            RebelParam.MOVEMENT=buttonGroup.getSelection().isSelected();
            RebelParam.UI_UPDATE_RATE=Integer.parseInt(t9.getText());
            RebelParam.SPY_IN_COP_DENSITY=Double.parseDouble(t11.getText());
            RebelParam.SPY_IN_AGENT_DENSITY=Double.parseDouble(t12.getText());
            t6.setText(String.valueOf(0));
            t7.setText(String.valueOf(0));
            t8.setText(String.valueOf(0));

            if(RebelParam.INITIAL_AGENT_DENSITY + RebelParam.INITIAL_COP_DENSITY > 1)
                JOptionPane.showConfirmDialog(
                        null,
                        "Sum of INITIAL-COP-DENSITY and INITIAL-AGENT-DENSITY should be less than 1.",
                        "Error",
                        JOptionPane.OK_CANCEL_OPTION
                );
            else{
                Rebellion.initialModel();
                monitor.initMonitor();
                runTurn=0;
                paintMap(grid);
            }

        });

        //go button listener
        b2.addActionListener(e -> {
            go=!go;
        });

        //save button listener
        b4.addActionListener(e -> {
            go = false;
            monitor.exportDataToCSV(runTurn);
        });

        //main thread
        UIThread = new Thread() {
            public void run() {
                super.run();
                while (true) {
                    if(go){
                        monitor.agentsMonitor(RebelMap.personList);
                        t6.setText(String.valueOf(monitor.quietNum));
                        t7.setText(String.valueOf(monitor.jailedNum));
                        t8.setText(String.valueOf(monitor.activeNum));
                        Rebellion.modelThread();
                        runTurn++;
                        //auto save after Max turn running
                        if(runTurn==RebelParam.MAX_TURN) {go=false;monitor.exportDataToCSV(runTurn);}
                        paintMap(grid);
                    }

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

    //paint the map with cell info
    private void paintMap(JPanel grid){
        grid.removeAll();
        for (int i = 0; i < RebelParam.MAP_COL; i++) {
            for (int j = 0; j < RebelParam.MAP_ROW; j++) {
                String cellStatus = RebelMap.map[i][j].getPersonStatus();
                JLabel x = new JLabel();
                x.setOpaque(true);
                x.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (cellStatus.equals(RebelParam.COP))
                    x.setBackground(Color.BLUE);
                if (cellStatus.equals(RebelParam.AGENT_QUIET))
                    x.setBackground(Color.GREEN);
                if (cellStatus.equals(RebelParam.AGENT_ACTIVE))
                    x.setBackground(Color.RED);
                if (cellStatus.equals(RebelParam.EMPTY_SLOT))
                    x.setBackground(Color.GRAY);
                if (RebelMap.map[i][j].isHasJailed())
                    x.setBackground(Color.BLACK);
                grid.add(x);
            }
        }
        grid.updateUI();
    }

}
