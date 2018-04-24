package ccpassignment;

import java.util.Date;

class Bus implements Runnable {
    Depot dpot;
    String name;
    Date inTime;
    
    public Bus(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Date getInTime()
    {
        return inTime;
    }
    
    public void setName(String busName)
    {
        this.name = name;
    }
    
    public void setInTime(Date inTime)
    {
        this.inTime = inTime;
    }
    
    public void run()
    {
        goforBusService();
    }
    
    private synchronized void goforBusService()
    {
        dpot.addBus(this);
    }
}
