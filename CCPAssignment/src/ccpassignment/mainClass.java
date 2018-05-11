package ccpassignment;

import java.util.Scanner;

public class mainClass {
    
    public static void main(String[] args) {
        
        int numBus, numCleaner;
        String weather;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Do you want the weather to rain today? (Y/N):");
        weather = scan.next();
        
        if(weather.equals("Y") || weather.equals("y"))
        {
            System.out.println("Today is raining! Cleaning bay is closed!");
            System.out.println("Enter the number of buses:");
            numBus = scan.nextInt();
            
            Depot dpot = new Depot(weather);
            
            //Ramp for buses
            DepotRamp dr = new DepotRamp(dpot);
            Thread thdr = new Thread(dr);

            //Generate number of buses
            BusGenerator bg = new BusGenerator(dpot, numBus);
            Thread thbg = new Thread(bg);

            //Generate number of mechanics
            Mechanic m = new Mechanic(dpot);
            Thread thM = new Thread(m);

            //Exit Ramp for closed cleaning bay
            CleanRamp cr = new CleanRamp(dpot);
            Thread thcr = new Thread(cr);
            
            //Clock for closing time
            ClosingClock cc = new ClosingClock(dpot, bg, m, dr);
            Thread thcc = new Thread(cc);

            thbg.start();
            thdr.start();
            thM.start();
            thcr.start();
            thcc.start();
        }
        else if(weather.equals("N") || weather.equals("n"))
        {
            System.out.println("Today is a sunny day!");
            System.out.println("Enter the number of buses:");
            numBus = scan.nextInt();
            System.out.println("Enter the number of cleaner:");
            numCleaner = scan.nextInt();
            
            Depot dpot = new Depot(weather);
            
            //Ramp for buses
            DepotRamp dr = new DepotRamp(dpot);
            Thread thdr = new Thread(dr);

            //Generate number of buses
            BusGenerator bg = new BusGenerator(dpot, numBus);
            Thread thbg = new Thread(bg);

            //Generate number of cleaners
            Cleaner c = new Cleaner(dpot, numCleaner);
            Thread thC = new Thread(c);

            //Generate number of mechanics
            Mechanic m = new Mechanic(dpot);
            Thread thM = new Thread(m);

            //Clock for closing time
            ClosingClock cc = new ClosingClock(dpot, bg, c, m, dr);
            Thread thcc = new Thread(cc);

            thbg.start();
            thdr.start();
            thC.start();
            thM.start();
            thcc.start();
        }
        
        System.out.println("Bus Depot is open!\n");
        
        
    }
    
}
