package ccpassignment;

class Cleaner implements Runnable{
    
    Depot dpot;
    public boolean depotClosingTime = false;
    public int num;
    
    public Cleaner(Depot dpot, int cleanerNum)
    {
        this.dpot = dpot;
        this.num = cleanerNum;
    }
    
    public void run()
    {
        int counter = 1;
        
        while (counter != num + 1)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
            
            System.out.println("Cleaner " + counter + " is ready!");           
            counter++;
        }
        
        while(!depotClosingTime)
        {
            try 
            {
                dpot.cleanBus();
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
        System.out.println("Cleaning Service is closed now!");
    }
}
