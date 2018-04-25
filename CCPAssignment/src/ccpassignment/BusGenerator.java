package ccpassignment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class BusGenerator implements Runnable {
    
    Depot dpot;
    public boolean depotClosingTime = false;
    
    public BusGenerator(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public void run()
    {
        while(!depotClosingTime)
        {
            Bus bus = new Bus(dpot);
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm");
            bus.setInTime(ft.format(date));
            Thread thBus = new Thread(bus);
            bus.setName("Thread-Bus " + thBus.getId());
            thBus.start();
            
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
        System.out.println("Its closing time!");
    }
}
