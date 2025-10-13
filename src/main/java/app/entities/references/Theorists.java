package app.entities.references;

public class Theorists {
    private int id;
    private String name;
    private int birthyear;
    private int deathyear;

    public Theorists(int id, String name, int birthyear, int deathyear) {
        this.id = id;
        this.name = name;
        this.birthyear = birthyear;
        this.deathyear = deathyear;
    }

    public Theorists(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

    public int getDeathyear() {
        return deathyear;
    }

    public void setDeathyear(int deathyear) {
        this.deathyear = deathyear;
    }
}
