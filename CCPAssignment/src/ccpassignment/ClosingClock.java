package ccpassignment;

import java.util.concurrent.TimeUnit;

class ClosingClock implements Runnable {
    
    public BusGenerator BG;
    public Cleaner CL;
    public Mechanic ME;
    public DepotRamp DR;
    public CleanRamp CR;
    public Depot D;
    public Boolean closingTime;
    
    public ClosingClock (Depot dpot, BusGenerator bg, Cleaner c, Mechanic m, DepotRamp dr)
    {
        D = dpot;
        BG = bg;
        CL = c;
        ME = m;
        DR = dr;
        closingTime = false;
    }
    
    public ClosingClock (Depot dpot, BusGenerator bg, Mechanic m, DepotRamp dr, CleanRamp cr)
    {
        D = dpot;
        BG = bg;
        ME = m;
        DR = dr;
        CR = cr;
        closingTime = false;
    }
    
    public void run()
    {
        String weather = D.rain;
       
        try
        {
            TimeUnit.MINUTES.sleep(1);
            if(weather.equals("Y") || weather.equals("y"))
            {
                notifyClosingTimeRain();
                D.setClosingTime();
            }
            else if(weather.equals("N") || weather.equals("n"))
            {
                notifyClosingTimeSunny();
                D.setClosingTime();
            }
        }
        catch (InterruptedException i)
        {
            i.printStackTrace();
        }
    }
    
    public void notifyClosingTimeRain()
    {
        System.out.println("\nBus Depot is closing now!");
        BG.setDepotClosingTime();
        ME.setDepotClosingTime();
        DR.setDepotClosingTime();
        CR.setDepotClosingTime();
    }
    
    public void notifyClosingTimeSunny()
    {
        System.out.println("\nBus Depot is closing now!");
        BG.setDepotClosingTime();
        CL.setDepotClosingTime();
        ME.setDepotClosingTime();
        DR.setDepotClosingTime();
    }
    
}
