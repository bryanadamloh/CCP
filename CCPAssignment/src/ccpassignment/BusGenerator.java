package ccpassignment;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class BusGenerator implements Runnable {
    
    Depot dpot;
    public boolean closingTime = false;
    
    public BusGenerator(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public void run()
    {
        
    }
}
