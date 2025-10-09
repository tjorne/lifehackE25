package app.entities;

public class Word {
    String word;

    public Word(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }
}
