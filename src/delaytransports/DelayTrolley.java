/*
 *
 */
package delaytransports;

/**
 * @authors Sergio Vega     (43480752B)
 *          Andreas Korn    (X4890193W)
 */
public class DelayTrolley extends DelayTransport{

    private final int floors;
    
    /**
     * DelayTrolley constructor, inherits from DelayTramsport, adds Nº of floors
     * @param delay
     * @param vehicleType
     * @param lineNumber
     * @param direction
     * @param stopID
     * @param weekDay
     * @param date
     * @param hour
     * @param floors
     */
    public DelayTrolley(int delay, String vehicleType, int lineNumber, int direction, int stopID, int weekDay, String date, String hour, int floors) {
        super(delay, vehicleType, lineNumber, direction, stopID, weekDay, date, hour);
        this.floors = floors;
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
