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
            placeMine(ix);
            --mines;
        }
    }

    public void placeMine(int ix)
    {
        if (field[ix].isMine)
            return;
        field[ix].isMine = true;

        // find index's of surrounding fields
        int[] indicies = new int[8];
        indicies[0] = ix-width; // top
        indicies[1] = ix+width; // bot
        indicies[2] = ix+1; // right
        indicies[3] = ix-1; // left
        indicies[4] = ix-width+1; // top right
        indicies[5] = ix-width-1; // top left
        indicies[6] = ix+width+1; // bot right
        indicies[7] = ix+width-1; // bot left

        for (int i : indicies) {
            if (i >= 0 && i < field.length)
                field[i].mineCnt++;
        }
    }
    public void reset()
    {
        placeMines();
    }

}
