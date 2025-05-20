package model;

public class HealthRecord {
    private int id;
    private String cause;
    private String date;
    private String prescription;
    private double weight;
    private double temperature;
    private int sleep;
    private int petId;

    public HealthRecord(int id, String cause, String date, String prescription, double weight, double temperature, int sleep, int petId) {
        this.id = id;
        this.cause = cause;
        this.date = date;
        this.prescription = prescription;
        this.weight = weight;
        this.temperature = temperature;
        this.sleep = sleep;
        this.petId = petId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    @Override
    public String toString() {
        return "HealthRecord{" +
                "id=" + id +
                ", cause='" + cause + '\'' +
                ", date='" + date + '\'' +
                ", prescription='" + prescription + '\'' +
                ", weight=" + weight +
                ", temperature=" + temperature +
                ", sleep=" + sleep +
                ", petId=" + petId +
                '}';
    }
}
