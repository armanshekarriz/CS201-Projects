public class PercolationUF implements IPercolate {
    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private final int VTOP;
    private final int VBOTTOM;

    PercolationUF(IUnionFind finder, int size) {
        finder.initialize((size * size) + 2);
        myFinder = finder;
        myGrid = new boolean[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                myGrid[i][j]=false;
            }
        }
        myOpenCount=0;
        VTOP=size*size;
        VBOTTOM=size*size + 1;
    }

    /**
     * Open is a site is not open
     * throw IndexOutOfBoundsException if invalid indexes
     * @param row
     *            row index in range [0,N-1]
     * @param col
     */
    @Override
    public void open(int row, int col) {
        if (! bounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (myGrid[row][col]) {
            return;
        }
        int x = row * myGrid.length + col;
        myGrid[row][col] = true;
        myOpenCount += 1;
        if (row==0) {
            myFinder.union(x, VTOP);
        }
        if (row==myGrid.length - 1) {
            myFinder.union(x, VBOTTOM);
        }
        if (row>0) {
            if (myGrid[row-1][col]) {
                int q = (row-1)*(myGrid.length) + col;
                myFinder.union(x,q);
            }
        }
        if (col>0) {
            if (myGrid[row][col-1]) {
                int q = row*(myGrid.length) + col-1;
                myFinder.union(x,q);
            }
        }
        if (row<myGrid.length -1) {
            if (myGrid[row+1][col]) {
                int q = (row+1)*(myGrid.length) + col;
                myFinder.union(x,q);
            }
        }
        if (col<myGrid[0].length - 1) {
            if (myGrid[row][col+1]) {
                int q = row*(myGrid.length) + col+1;
                myFinder.union(x,q);
            }
        }
    }

    /**
     * Is (row, col) open or not
     * throw IndexOutOfBoundsException if invalid index
     * @param row
     *            row index in range [0,N-1]
     * @param col
     * @return boolean, is cell open or not
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (! bounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return myGrid[row][col];
    }

    /**
     * True if (row, col) is full
     * throw IndexOutOfBoundsException if invalid index
     * @param row
     *            row index in range [0,N-1]
     * @param col
     * @return boolean, is cell full or not
     */
    @Override
    public boolean isFull(int row, int col) {
        if (! bounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return myFinder.connected((row * myGrid.length + col), VTOP);
    }

    /**
     * Connected path from VTOP to VBOTTTOM means that system percolates
     * @return boolean, true is system percolates
     */
    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * Distinct sites opened, stored in myOpenCount
     * @return # of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    /**
     * Check row and col indexes
     * @param row
     * @param col
     * @return are indexes valid
     */
    private boolean bounds(int row, int col) {
        if (row < 0 || col < 0 || row >= myGrid.length || col >= myGrid[0].length) {
            return false;
        }
        return true;
    }
}
