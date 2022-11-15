package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class AirCompany {
    private long id;
    private String name;
    private List<Flight> flights;

    public AirCompany(){
        flights=new ArrayList<>();
    }

    public AirCompany(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight){
        flights.add(flight);
    }

    public void removeFlight(Flight flight){
        flights.remove(flight);
    }

    @Override
    public String toString(){
        return "id - " + this.id + "\n" + "name - "+this.name + flights.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirCompany that = (AirCompany) o;
        return Objects.equals(id, that.id);
    }
}
