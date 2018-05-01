package ccpassignment;

class Mechanic implements Runnable {
    
    Depot dpot;
    public boolean depotClosingTime = false;
    public int num;
    
    public Mechanic(Depot dpot, int mechanicNum)
    {
        this.dpot = dpot;
        this.num = mechanicNum;
    }
    
    public void run()
    {
        int counter = 1;
        
        while(counter != num + 1)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }

            System.out.println("Mechanic " + counter + " is ready!");
            
            counter++;
        }
        
        while(!depotClosingTime)
        {
           try
           {
               dpot.repairBus();
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
        System.out.println("Mechanic Service is closed now!");
    }
        
}
