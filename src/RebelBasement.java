import java.util.ArrayList;
import java.util.List;

/**
 * 1. variable range with cop num in vision, rebel random agent in range
 * 2. fixed range, rebel variable agent in range with cop num in vision
 * 3. variable range, increase agent rebel chance in range
 *
 * 4. fixed range, increase agent rebel chance in range. random position
 */
public class RebelBasement extends Cell{
    private int range;
    private List<Cell> cellInBasement=new ArrayList<>();
    private double rebelFactor;
    public RebelBasement(int cellX, int cellY, String personStatus) {
        super(cellX, cellY, personStatus);
    }

}
