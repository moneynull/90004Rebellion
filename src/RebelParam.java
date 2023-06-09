/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Parameter setting file
 */
public class RebelParam {
    /**
     * system param
     */
    public static int MAX_TURN=200;
    public static int UI_UPDATE_RATE=100;
    public static int MAP_COL=40;
    public static int MAP_ROW=40;

    /**
     * model param
     */
    public static double INITIAL_COP_DENSITY=0.04;
    public static double INITIAL_AGENT_DENSITY=0.700;
    public static double GOVERNMENT_LEGITIMACY=0.82;
    public static int MAX_JAIL_TERM=30;
    public static int VISION=7;
    public static boolean MOVEMENT=true;

    /**
     * extension param
     */
    public static double SPY_IN_COP_DENSITY=0.1;
    public static double SPY_IN_AGENT_DENSITY=0.02;

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
    public static String AGENT_QUIET=AGENT+" quiet";
    public static String AGENT_ACTIVE=AGENT+" active";
    public static String AGENT_JAILED=AGENT+" jailed";
}
