package myRebel;

public class RebelParam {
    /**
     * system param
     */
    public static int MAX_TURN=100;
    public static boolean GUI_PRINT=false;
    public static int GUI_PRINT_SPEED=2000;
    public static int MAP_COL=5;
    public static int MAP_ROW=5;

    /**
     * model param
     */
    public static double INITIAL_COP_DENSITY=0.500;
    public static double INITIAL_AGENT_DENSITY=0.500;
    public static double GOVERNMENT_LEGITIMACY=0.50;
    public static int MAX_JAIL_TERM=50;
    public static int VISION=1;
    public static boolean MOVEMENT=false;

    /**
     * global param
     */
    public static double THRESHOLD=0.1;
    public static double K=2.3;

    /**
     * person state
     */
    public static String EMPTY_SLOT="slot is empty";
    public static String AGENT="agent";
    public static String COP="cop";
    public static String AGENT_QUIET="agent quiet";
    public static String AGENT_ACTIVE="agent active";
    public static String AGENT_JAILED="agent jailed";
}
