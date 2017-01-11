/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package airportmain;

/**
 *
 * @author kwhiting
 */
public class Airplane {
    private String airplaneType;
    private int numberOfSeats;
    private int weightLimit;
    private int yearsInService;	

    /**
     * @return the airplaneType
     */
    public String getAirplaneType() {
        return airplaneType;
    }

    /**
     * @param airplaneType the airplaneType to set
     */
    public void setAirplaneType(String airplaneType) {
        this.airplaneType = airplaneType;
    }

    /**
     * @return the numberOfSeats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * @param numberOfSeats the numberOfSeats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * @return the weightLimit
     */
    public int getWeightLimit() {
        return weightLimit;
    }

    /**
     * @param weightLimit the weightLimit to set
     */
    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    /**
     * @return the yearsInService
     */
    public int getYearsInService() {
        return yearsInService;
    }

    /**
     * @param yearsInService the yearsInService to set
     */
    public void setYearsInService(int yearsInService) {
        this.yearsInService = yearsInService;
    }

    public String toString()
    {
        return "";
    }
}
