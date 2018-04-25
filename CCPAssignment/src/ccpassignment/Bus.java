package ccpassignment;

import java.util.Date;

class Bus implements Runnable {
    Depot dpot;
    String name;
    String inTime;
    
    public Bus(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getInTime()
    {
        return inTime;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setInTime(String inTime)
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
