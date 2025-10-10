package app.entities.gruppeE;

import java.util.Arrays;
import java.util.Random;

public class MineMap {
    public class MineField {
        public int mineCnt;
        public boolean shown;
        public boolean isMine;
    }
    public MineField[] field;
    public int width;
    public int height;
    public int numMines; // total mine count
    public boolean gameover;
    public int shownFields;

    public MineMap(int w, int h)
    {
        field = new MineField[w*h];
        for (int i = 0; i < field.length; ++i) {
            field[i] = new MineField();
        }
        numMines = (int)(w*h*0.2f);
        this.width = w;
        this.height = h;
        placeMines();
    }

    public void placeMines()
    {
        int mines = numMines;
        Random rand = new Random();
        while (mines > 0) {
            int ix;
            ix = rand.nextInt(field.length);
            if (field[ix].isMine)
                continue;
            placeMine(ix);
            --mines;
        }
    }

    private int[] getSurroundingFileds(int ix)
    {
        int x = (ix != 0) ? ix % width : 0;
        boolean left = x == 0;
        boolean right = x == width-1;
        int[] indicies = new int[8];
        Arrays.fill(indicies, -1);
        indicies[0] = ix-width; // top
        indicies[1] = ix+width; // bot
        if (!right) {
            indicies[2] = ix + 1; // right
            indicies[4] = ix - width + 1; // top right
            indicies[6] = ix + width + 1; // bot right
        }
        if (!left) {
            indicies[3] = ix - 1; // left
            indicies[5] = ix - width - 1; // top left
            indicies[7] = ix + width - 1; // bot left
        }
        return indicies;
    }

    public void placeMine(int ix)
    {
        if (field[ix].isMine)
            return;
        field[ix].isMine = true;

        int[] indicies = getSurroundingFileds(ix);

        for (int i : indicies) {
            if (i >= 0 && i < field.length)
                field[i].mineCnt++;
        }
    }

    public void reveal(int ix)
    {
        if (ix < 0 || ix >= field.length) {
            System.out.println("index out of bounds");
            return;
        }
        field[ix].shown = true;
        ++shownFields;
        if (field[ix].isMine) {
            gameover = true;
            return;
        }

        // auto reveal
        if (field[ix].mineCnt == 0) {
            int[] indicies = getSurroundingFileds(ix);
            for (int i : indicies) {
                if (i < 0 || i >= field.length) {
                    continue;
                }
                if (field[i].shown)
                    continue;
                field[i].shown = true;
                ++shownFields;
            }
        }
    }
    public void reset()
    {
        placeMines();
    }

}
