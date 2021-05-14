public class PercolationDFSFast extends PercolationDFS {
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationDFSFast(int n) {
        super(n);
    }
    /**
     * Overrides updateOnOpen from PercolationDFS to make more efficient
     * @param row is row index of cell
     * @param col is column index of cell
     */
    @Override
    public void updateOnOpen(int row, int col) {
        boolean flag = false;
        if (row==0) {
            flag = true;
        }
        if (row < myGrid.length-1) {
            if (myGrid[row+1][col]==FULL) {
                flag = true;
            }
        }
        if (row > 0) {
            if (myGrid[row-1][col]==FULL) {
                flag = true;
            }
        }
        if (col<myGrid[0].length-1) {
            if (myGrid[row][col+1]==FULL) {
                flag=true;
            }
        }
        if (col>0) {
            if (myGrid[row][col-1]==FULL) {
                flag=true;
            }
        }
        if (flag) {
            dfs(row,col);
        }
    }
}
