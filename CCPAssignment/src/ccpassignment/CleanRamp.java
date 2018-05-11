package ccpassignment;

class CleanRamp implements Runnable{
    
    Depot dpot;
    public boolean depotClosingTime = false;
    
    public CleanRamp(Depot dpot)
    {
        this.dpot = dpot;
    }
    
    public void run()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException i)
        {
            i.printStackTrace();
        }
        
        while(!depotClosingTime)
        {
            try
            {
                dpot.cleanRampExit();
            }
            catch(InterruptedException i)
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
            catch(InterruptedException i)
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
