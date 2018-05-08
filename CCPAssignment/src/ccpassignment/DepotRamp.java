package ccpassignment;

class DepotRamp implements Runnable{
    
    Depot dpot;
    public boolean depotClosingTime = false;
    
    public DepotRamp(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public void run()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException i)
        {
            i.printStackTrace();
        }
        
        System.out.println("Checking ramp!");
        
        while(!depotClosingTime)
        {
            try 
            {
                dpot.rampEntrance();
            } 
            catch (InterruptedException ex) 
            {
                ex.printStackTrace();
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
