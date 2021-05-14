import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationBFS(int n) {
        super(n);
    }
    @Override
    public void dfs(int row, int col) {
        int size = myGrid.length;
        Queue<Integer> q = new LinkedList<>();
        myGrid[row][col]=FULL;
        q.add(row*size + col);
        while (q.size() !=0) {
            Integer r = q.remove();
            int nr = r/size;
            int nc = r%size;
            if (nr>0) {
                if (myGrid[nr-1][nc]==OPEN) {
                    myGrid[nr-1][nc]=FULL;
                    q.add((nr-1)*size + nc);
                }
            }
            if (nc>0) {
                if (myGrid[nr][nc-1]==OPEN) {
                    myGrid[nr][nc-1]=FULL;
                    q.add(nr*size + nc-1);
                }
            }
            if (nr<myGrid.length-1) {
                if (myGrid[nr+1][nc]==OPEN) {
                    myGrid[nr+1][nc]=FULL;
                    q.add((nr+1)*size + nc);
                }
            }
            if (nc<myGrid[0].length -1) {
                if (myGrid[nr][nc+1]==OPEN) {
                    myGrid[nr][nc+1]=FULL;
                    q.add(nr*size + nc+1);
                }
            }
        }
    }
}
