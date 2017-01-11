/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airportmain;

/**
 *
 * @author kwhiting
 */
public class Airport {
    private String airportName;
    private String airportCity;
    private String airportState;
    private String airportCode;

    /**
     * @return the airportName
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * @param airportName the airportName to set
     */
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    /**
     * @return the airportCity
     */
    public String getAirportCity() {
        return airportCity;
    }

    /**
     * @param airportCity the airportCity to set
     */
    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    /**
     * @return the airportState
     */
    public String getAirportState() {
        return airportState;
    }

    /**
     * @param airportState the airportState to set
     */
    public void setAirportState(String airportState) {
        this.airportState = airportState;
    }

    /**
     * @return the airportCode
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * @param airportCode the airportCode to set
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String toString() {
        return airportName + "," + airportCity + "," + airportState + "," + airportCode + ",";
    }
    
    public Airport(String inName, String inCity, String inState, String inCode)
    {
        airportName = inName;
        airportCity = inCity;
        airportState = inState;
        airportCode = inCode;
    }

    public Airport()
    {
        
    }
}
