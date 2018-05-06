package ccpassignment;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Depot {
    
    int ramp = 0; 
    Random r = new Random();
    List<Bus> listBus;
    List<Bus> serviceWaitingArea;
    List<Bus> repair;
    List<Bus> clean;
    List<Bus> afterWaitingArea;
    
    public Depot()
    {
        listBus = new LinkedList<Bus>();
        serviceWaitingArea = new LinkedList<Bus>();
        repair = new LinkedList<Bus>();
        clean = new LinkedList<Bus>();
        afterWaitingArea = new LinkedList<Bus>();
    }
    
    public void addBus(Bus bus)
    {
        System.out.println("Thread-" + bus.getName() + ": Entering the waiting area at " + bus.getInTime());
        
        synchronized (listBus)
        {
            ((LinkedList<Bus>)listBus).offer(bus);
            listBus.notify();
        }
    }
    
    public void rampEntrance() throws InterruptedException
    {
        long busDuration = 0;
        
        synchronized (listBus)
        {
            while(listBus.isEmpty())
            {
                System.out.println("Please wait!\n");
                listBus.wait();
            }
            
            Bus bus = (Bus)((LinkedList<Bus>)listBus).poll();
            System.out.println("Thread-" + bus.getName() + ": Request for entrance to the ramp!");
            
            if(rampCheck(bus) == 0)
            {        
                ramp = 1;
                System.out.println("Thread-" + bus.getName() + ": Acquiring ramp at " + bus.getInTime() + "!");
                busDuration = (long)(Math.random()*5);
                TimeUnit.SECONDS.sleep(busDuration);
                System.out.println("Thread-" + bus.getName() + " took " + busDuration + " seconds to get the ramp!");
                System.out.println("Thread-" + bus.getName() + ": Entering service waiting area at " + bus.getInTime() + "!\n");
                ramp = 0;

                ((LinkedList<Bus>)serviceWaitingArea).offer(bus);
                if(listBus.size() == 1)
                {
                    listBus.notify();
                }
                chooseService(bus);
            }
        }    
    }
    
    public void chooseService(Bus bus)
    {
        int ranNum = r.nextInt(2)+1;
        
        if(ranNum == 1)
        {
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)repair).offer(bus);
            synchronized(repair)
            {
                repair.notify();
            }
        }
        else if(ranNum == 2)
        {
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)clean).offer(bus);
            synchronized(clean)
            {
                clean.notify();
            }
        }
    }
    
    public void cleanBus() throws InterruptedException
    {
        long duration = 0;
        
        synchronized (clean)
        {
            while(clean.isEmpty())
            {
                System.out.println("Cleaner is waiting for the bus!");
                clean.wait();
            }
            
            Bus bus = (Bus) ((LinkedList<?>)clean).poll();
            System.out.println("Thread-" + bus.getName()+ ": Reached cleaning bay at " + bus.getInTime() + "!");
            
            try
            {
                System.out.println("Cleaning " + bus.getName());
                duration = (long)(Math.random()*5) + 5;
                TimeUnit.SECONDS.sleep(duration);
                System.out.println("Thread-" + bus.getName() + ": Bus cleaning is completed in " + duration + " seconds!");
                System.out.println("Thread-" + bus.getName() + ": Entering depot waiting area at " + bus.getInTime() + "!\n");
                
                ((LinkedList<Bus>)afterWaitingArea).offer(bus);
                synchronized (afterWaitingArea)
                {
                    afterWaitingArea.notify();
                }
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
        }
        
        //When the bus reaches the ramp and exiting the depot
        long busDuration = 0;

        while(afterWaitingArea.isEmpty())
        {
            System.out.println("Waiting for bus!");
            afterWaitingArea.wait();
        }
        Bus busCount = (Bus) ((LinkedList<?>)afterWaitingArea).poll();

        if(rampCheck(busCount) == 0)
        {
            try
            {
                ramp = 1;
                System.out.println("Thread-" + busCount.getName() + ": Acquiring ramp at " + busCount.getInTime() + "!");
                busDuration = (long)(Math.random()*5);
                TimeUnit.SECONDS.sleep(busDuration);
                ramp = 0;
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }

            System.out.println("Thread-" + busCount.getName() + " took " + busDuration + " seconds to exit the ramp!");
            System.out.println("Thread-" + busCount.getName() + ": Exiting depot!\n");               
        }    
   
    }
    
    public void repairBus() throws InterruptedException
    {
        long duration = 0;
        
        synchronized (repair)
        {
            while(repair.isEmpty())
            {
                System.out.println("Mechanic is waiting for the bus!");
                repair.wait();
            }
            
            Bus bus = (Bus)((LinkedList<?>)repair).poll();
            System.out.println("Thread-" + bus.getName()+ ": Reached mechanic bay at " + bus.getInTime() + "!");
            
            try
            {
                System.out.println("Repairing " + bus.getName());
                duration = (long)(Math.random()*5) + 5;
                TimeUnit.SECONDS.sleep(duration);
                System.out.println("Thread-" + bus.getName() + ": Bus repairing is completed in " + duration + " seconds!");
                System.out.println("Thread-" + bus.getName() + ": Entering depot waiting area at " + bus.getInTime() + "!\n");
                
                ((LinkedList<Bus>)afterWaitingArea).offer(bus);
                synchronized (afterWaitingArea)
                {
                    afterWaitingArea.notify();
                }
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
        }
        
        //When the bus reaches the ramp and exiting the depot
        long busDuration = 0;

        while(afterWaitingArea.isEmpty())
        {
            System.out.println("Waiting for bus!");
            afterWaitingArea.wait();
        }
        Bus busCount = (Bus) ((LinkedList<?>)afterWaitingArea).poll();

        if(rampCheck(busCount) == 0)
        {
            try
            {
                ramp = 1;
                System.out.println("Thread-" + busCount.getName() + ": Acquiring ramp at " + busCount.getInTime() + "!");
                busDuration = (long)(Math.random()*5);
                TimeUnit.SECONDS.sleep(busDuration);
                ramp = 0;
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }

            System.out.println("Thread-" + busCount.getName() + " took " + busDuration + " seconds to exit the ramp!");
            System.out.println("Thread-" + busCount.getName() + ": Exiting depot!\n");               
        }
    }
    
    //Check whether the ramp is empty or not
    public int rampCheck(Bus bus)
    {
        synchronized (this)
        {
            if(ramp == 0)
            {
                ramp = 0;
            }
            else
            {
                ramp = 1;
            }
        }
        
        return ramp;
    }
}
