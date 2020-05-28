/* 
 *
 */
package delaytransports;

/**
 * @authors Sergio Vega     (43480752B)
 *          Andreas Korn    (X4890193W)
 */
public abstract class DelayTransport implements Comparable <DelayTransport> {

    protected int delay;
    protected String vehicleType;
    protected int lineNumber;
    protected int direction;
    protected int stopID;
    protected int weekDay;
    protected String date;
    protected String hour;
    /**
     * DelayTransport constructor
     * @param delay
     * @param vehicleType
     * @param lineNumber
     * @param direction
     * @param stopId
     * @param weekDay
     * @param date
     * @param hour
     */
    public DelayTransport(int delay, String vehicleType, int lineNumber, int direction, int stopId, int weekDay, String date, String hour) {
        this.delay = delay;
        this.vehicleType = vehicleType;
        this.lineNumber = lineNumber;
        this.direction = direction;
        this.stopID = stopId;
        this.weekDay = weekDay;
        this.date = date;
        this.hour = hour;
    }

    //Abstract methods for DelayTram, DeltayBus, DelayTrolley
    public abstract int getDelay();
    public abstract String getVehicleType();
    public abstract int getLineNumber();
    public abstract int getDirection();
    public abstract int getStopID();
    public abstract int getWeekDay();
    public abstract String getDate();
    public abstract String getHour();
    
    /**
     * Compares two transports by their delay.
     * @param t
     * @return
     */
    @Override
    public int compareTo(DelayTransport t) {
        return (this.getDelay()-t.getDelay());
    }
}
