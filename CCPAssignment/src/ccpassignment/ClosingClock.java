package ccpassignment;

class ClosingClock implements Runnable {
    
    public BusGenerator BG;
    public Cleaner CL;
    public Mechanic ME;
    public DepotRamp DR;
    public Boolean closingTime;
    
    public ClosingClock (BusGenerator bg, Cleaner c, Mechanic m, DepotRamp dr)
    {
        BG = bg;
        CL = c;
        ME = m;
        DR = dr;
        closingTime = false;
    }
    
    public void run()
    {
        try
        {
            Thread.sleep(30000);
            notifyDepotClosingTime();
        }
        catch (InterruptedException i)
        {
            i.printStackTrace();
        }
    }
    
    public void notifyDepotClosingTime()
    {
        System.out.println("Bus Depot is closing now!");
        BG.setDepotClosingTime();
        CL.setDepotClosingTime();
        ME.setDepotClosingTime();
        DR.setDepotClosingTime();
    }
}
