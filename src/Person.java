import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author Xiang Guo
 * @date 2023/5/26
 * @Description
 * Basic person class, person could move randomly
 */
public class Person {
    private Cell cell;
    private String personStatus;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    /**
     * Person could move to a cell in vision each turn
     */
    public void randomMove(){
        //get available cells
        List<Cell> emptyCells=cell.getCellsInVision().stream()
                .filter(c->c.getPersonStatus().equals(RebelParam.EMPTY_SLOT))
                .collect(Collectors.toList());

        //move to a random empty cell
        if(emptyCells.size()>0 && !this.getPersonStatus().equals(RebelParam.AGENT_JAILED)){
            Random random=new Random();
            Cell newLoc=emptyCells.get(random.nextInt(emptyCells.size()));
            cell.setPersonStatus(RebelParam.EMPTY_SLOT);
            cell=newLoc;
            cell.setPersonStatus(personStatus);
        }
    }
}
