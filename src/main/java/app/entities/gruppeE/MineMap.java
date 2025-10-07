package app.entities.gruppeE;

public class MineMap {
    public class MineField {
        public int mineCnt;
        public boolean shown;
    }
    public int[] field;

    public MineMap(int w, int h) {
        field = new int[w*h];
        for (int i = 0; i < field.length; ++i) {
            field[i] = i;
        }
    }

    public void placeMine(int x, int y)
    {
        // TODO:

    }

}
