/*
 *
 */
package delaytransports;

/**
 * @authors Sergio Vega     (43480752B)
 *          Andreas Korn    (X4890193W)
 */
public class DelayTram extends DelayTransport{

    private final int power;
    
    /**
     * DelayTram constructor, inherits from DelayTramsport, adds its power.
     * @param delay
     * @param vehicleType
     * @param lineNumber
     * @param direction
     * @param stopId
     * @param weekDay
     * @param date
     * @param hour
     * @param power
     */
    public DelayTram(int delay, String vehicleType, int lineNumber, int direction, int stopId, int weekDay, String date, String hour, int power) {
        super(delay, vehicleType, lineNumber, direction, stopId, weekDay, date, hour);
        this.power = power;
    }
    
    /**
     * Delay getter
     * @return 
     */
    @Override
    public int getDelay() {
        return delay;
    }

    /**
     * VehicleType getter
     * @return 
     */
    @Override
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * LineNumber getter
     * @return 
     */
    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Direction getter
     * @return 
     */
    @Override
    public int getDirection() {
        return direction;
    }

    /**
     * Stop ID getter
     * @return 
     */
    @Override
    public int getStopID() {
        return stopID;
    }    

    /**
     * Day getter
     * @return 
     */
    @Override
    public int getWeekDay() {
        return weekDay;
    }

    /**
     * Date getter
     * @return 
     */
    @Override
    public String getDate() {
        return date;
    }
    
    /**
     * Hour getter
     * @return 
     */
    @Override
    public String getHour() {
        return hour;
    }
}
