package ccpassignment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class BusGenerator implements Runnable {
    
    Depot dpot;
    public boolean depotClosingTime = false;
    public int num;
    
    public BusGenerator(Depot dpot, int busNum)
    {
        this.dpot = dpot;
        this.num = busNum;
    }
    
    public void run()
    {
        int busCounter = 1;
        
        while(!depotClosingTime && busCounter != num + 1)
        {
            Bus bus = new Bus(dpot);
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            bus.setInTime(ft.format(date));
            
            Thread thBus = new Thread(bus);
            bus.setName("Bus " + busCounter);
            thBus.start();
            busCounter++;
            
            try
            {
                TimeUnit.SECONDS.sleep((long)(Math.random()*10));
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
            
        }
        if(depotClosingTime)
        {
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
        }
        
    }
    
    public synchronized void setDepotClosingTime()
    {
        depotClosingTime = true;
    }
}
