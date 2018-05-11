package ccpassignment;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Depot {
    
    int ramp = 0;
    public boolean cTime = false;
    public String rain;
    Random r = new Random();
    List<Bus> listBus;
    List<Bus> serviceWaitingArea;
    List<Bus> repair;
    List<Bus> clean;
    List<Bus> afterWaitingArea;
    
    public Depot(String weather)
    {
        listBus = new LinkedList<Bus>();
        serviceWaitingArea = new LinkedList<Bus>();
        repair = new LinkedList<Bus>();
        clean = new LinkedList<Bus>();
        afterWaitingArea = new LinkedList<Bus>(); 
        this.rain = weather;
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
        Bus bus;
        
        synchronized (listBus)
        {
            while(listBus.isEmpty())
            {
                System.out.println("Please wait!\n");
                try
                {
                    listBus.wait();
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }
            }
            
            bus = (Bus)((LinkedList<Bus>)listBus).poll();
            System.out.println("Thread-" + bus.getName() + ": Request for entrance to the ramp!");
            
            if(rampCheck(bus) == 0)
            {        
                ramp = 1;
                System.out.println("Thread-" + bus.getName() + ": Acquiring ramp at " + bus.getInTime() + "!");
                busDuration = (long)(Math.random()*2);
                TimeUnit.SECONDS.sleep(busDuration);
                System.out.println("Thread-" + bus.getName() + " took " + busDuration + " seconds to get the ramp!");
                System.out.println("Thread-" + bus.getName() + ": Entering service waiting area at " + bus.getInTime() + "!\n");
                ramp = 0;

                ((LinkedList<Bus>)serviceWaitingArea).offer(bus);
                chooseService(bus);
            }
            else if(rampCheck(bus) == 1)
            {
                System.out.println("\nRamp is full! Please try again!\n");
                ((LinkedList<Bus>)listBus).offer(bus);
                ramp = 0;
            }
            
            if(listBus.size() == 1)
            {
                listBus.notify();
            }
        }
        
    }
    
    public void chooseService(Bus bus)
    {
        int ranNum = (Math.random() <= 0.5) ? 1 : 2;
        
        if(ranNum == 1 && (rain.equals("Y") || rain.equals("y")))
        {
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)repair).offer(bus);
            synchronized(repair)
            {
                repair.notify();
            }
        }
        else if (ranNum == 1 && (rain.equals("N") || rain.equals("n")))
        {
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)repair).offer(bus);
            synchronized(repair)
            {
                repair.notify();
            }
        }
        else if(ranNum == 2 && (rain.equals("N") || rain.equals("n")))
        {
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)clean).offer(bus);
            synchronized(clean)
            {
                clean.notify();
            }
        }
        else if(ranNum == 2 && (rain.equals("Y") || rain.equals("y")))
        {
            System.out.println("Cleaning bay is closed! Please come back again tomorrow!");
            bus = (Bus) ((LinkedList<?>)serviceWaitingArea).poll();
            ((LinkedList<Bus>)afterWaitingArea).offer(bus);
            synchronized(afterWaitingArea)
            {
                afterWaitingArea.notify();
            }
        }
    }
    
    public void cleanBus() throws InterruptedException
    {
        long duration = 0;
        Bus busExit;
        
        //Cleaning bus process
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
                System.out.println("Cleaner is cleaning " + bus.getName());
                duration = (long)(Math.random()*5)+5;
                TimeUnit.SECONDS.sleep(duration);
                System.out.println("Thread-" + bus.getName() + ": Bus cleaning is completed in " + duration + " seconds!");
                System.out.println("Thread-" + bus.getName() + ": Entering depot waiting area at " + bus.getInTime() + "!\n");
                
                ((LinkedList<Bus>)afterWaitingArea).offer(bus);
                //Notifies afterWaitingArea object to wake up a thread
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
        synchronized (afterWaitingArea)
        {
             while(afterWaitingArea.isEmpty())
            {
                System.out.println("Waiting for bus!");
                try
                {
                    afterWaitingArea.wait();
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }                
            }
             
            busExit = (Bus) ((LinkedList<?>)afterWaitingArea).poll();
            System.out.println("Thread-" + busExit.getName() + ": Request for entrance to the ramp!");
               
            if(rampCheck(busExit) == 0)
            {
                long busDuration = 0;
                try
                {
                    ramp = 1;
                    System.out.println("Thread-" + busExit.getName() + ": Acquiring ramp at " + busExit.getInTime() + "!");
                    busDuration = (long)(Math.random()*2);
                    TimeUnit.SECONDS.sleep(busDuration);
                    ramp = 0;
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }

                System.out.println("Thread-" + busExit.getName() + " took " + busDuration + " seconds to exit the ramp!");
                System.out.println("Thread-" + busExit.getName() + ": Exiting depot!\n");               
            }
            else if(rampCheck(busExit) == 1)
            {
                System.out.println("\nRamp is full! Please try again!\n");
                ((LinkedList<Bus>)afterWaitingArea).offer(busExit);
                ramp = 0;
            }
            
            if(afterWaitingArea.size() == 1)
            {
                afterWaitingArea.notify();
            }
        }
    }
    
    public void repairBus() throws InterruptedException
    {
        long duration = 0;
        Bus busExit;
        
        //Repairing bus process
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
                System.out.println("Mechanic is repairing " + bus.getName());
                duration = (long)(Math.random()*5)+5;
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
        synchronized (afterWaitingArea)
        {
            while(afterWaitingArea.isEmpty())
            {
                System.out.println("Waiting for bus!");
                try
                {
                    afterWaitingArea.wait();
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }                
            }
             
            busExit = (Bus) ((LinkedList<?>)afterWaitingArea).poll();
            System.out.println("Thread-" + busExit.getName() + ": Request for entrance to the ramp!");
                
            if(rampCheck(busExit) == 0)
            {
                long busDuration = 0;
                try
                {
                    ramp = 1;
                    System.out.println("Thread-" + busExit.getName() + ": Acquiring ramp at " + busExit.getInTime() + "!");
                    busDuration = (long)(Math.random()*2);
                    TimeUnit.SECONDS.sleep(busDuration);
                    ramp = 0;
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }

                System.out.println("Thread-" + busExit.getName() + " took " + busDuration + " seconds to exit the ramp!");
                System.out.println("Thread-" + busExit.getName() + ": Exiting depot!\n");               
            }
            else if(rampCheck(busExit) == 1)
            {
                System.out.println("\nRamp is full! Please try again!\n");
                ((LinkedList<Bus>)afterWaitingArea).offer(busExit);
                ramp = 0;
            }
                    
            if(afterWaitingArea.size() == 1)
            {
                afterWaitingArea.notify();
            }
        }
        
    }
    
    //Ramp exit when cleaning bay is closed and bus straight goes into afterWaitingArea object
    public void cleanRampExit() throws InterruptedException
    {
        Bus busExit;
        
        synchronized (afterWaitingArea)
        {
            while(afterWaitingArea.isEmpty())
            {
                System.out.println("Waiting for bus!");
                try
                {
                    afterWaitingArea.wait();
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }                
            }
             
            busExit = (Bus) ((LinkedList<?>)afterWaitingArea).poll();
            System.out.println("Thread-" + busExit.getName() + ": Request for entrance to the ramp!");
                
            if(rampCheck(busExit) == 0)
            {
                long busDuration = 0;
                try
                {
                    ramp = 1;
                    System.out.println("Thread-" + busExit.getName() + ": Acquiring ramp at " + busExit.getInTime() + "!");
                    busDuration = (long)(Math.random()*2);
                    TimeUnit.SECONDS.sleep(busDuration);
                    ramp = 0;
                }
                catch (InterruptedException i)
                {
                    i.printStackTrace();
                }

                System.out.println("Thread-" + busExit.getName() + " took " + busDuration + " seconds to exit the ramp!");
                System.out.println("Thread-" + busExit.getName() + ": Exiting depot!\n");               
            }
            else if(rampCheck(busExit) == 1)
            {
                System.out.println("\nRamp is full! Please try again!\n");
                ((LinkedList<Bus>)afterWaitingArea).offer(busExit);
                ramp = 0;
            }
                    
            if(afterWaitingArea.size() == 1)
            {
                afterWaitingArea.notifyAll();
            }
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
