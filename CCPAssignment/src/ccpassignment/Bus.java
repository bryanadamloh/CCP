package ccpassignment;

class Bus implements Runnable {
    Depot dpot;
    private String name;
    private String inTime;
    
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
