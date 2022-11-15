package models;

public class Flight {
    private long id;
    private long companyID;
    private String destination;
    private String departure;

    private Integer passangers;
    public Flight(){

    }
    public Flight(long id, long companyID, String destination, String departure, Integer passangers) {
        this.id = id;
        this.companyID = companyID;
        this.destination = destination;
        this.departure = departure;
        this.passangers = passangers;
    }
    @Override
    public String toString(){
        return "id - " +this.id + "\n" + "companyId - " + this.companyID+"\n" + this.departure + " - " + this.destination + "\n" + "passengers - " + this.passangers;
    }

    public long getCompanyID() {
        return companyID;
    }

    public long getId() {
        return id;
    }

    public Integer getPassangers() {
        return passangers;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setPassangers(Integer passangers) {
        this.passangers = passangers;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setId(long id) {
        this.id = id;
    }
}

