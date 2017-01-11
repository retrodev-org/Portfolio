/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airportmain;

import java.util.ArrayList;

/**
 *
 * @author kwhiting
 */
public class Airline 
{
    private String airlineName;
    private String airlineHubLocation;
    private ArrayList<Flight> flights = new ArrayList<Flight>();
    /**
     * @return the airlineName
     */
    public String getAirlineName() 
    {
        return airlineName;
    }

    /**
     * @param airlineName the airlineName to set
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * @return the airlineHubLocation
     */
    public String getAirlineHubLocation() {
        return airlineHubLocation;
    }

    /**
     * @param airlineHubLocation the airlineHubLocation to set
     */
    public void setAirlineHubLocation(String airlineHubLocation) {
        this.airlineHubLocation = airlineHubLocation;
    }
    
    /**
     * @return the flight
     */
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlights(ArrayList<Flight> flight) {
        this.flights = flight;
    }

    // overrides default public void toString()
    public String toString() {
        return airlineName + "," + airlineHubLocation + ",";
    }
            
}
