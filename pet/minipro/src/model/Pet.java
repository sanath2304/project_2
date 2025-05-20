package model;

public class Pet {
    private int id;
    private String name;
    private String birthday;
    private String species;
    private String breed;

    public Pet(int id, String name, String birthday, String species, String breed) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.species = species;
        this.breed = breed;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                '}';
    }
}
