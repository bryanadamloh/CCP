package ccpassignment;

import java.util.Scanner;

public class mainClass {
    
    public static void main(String[] args) {
        
        int numBus, numCleaner, numMechanics;
        
        Depot dpot = new Depot();
        DepotRamp dr = new DepotRamp(dpot);
        Thread thdr = new Thread(dr);
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Enter the number of buses:");
        numBus = scan.nextInt();
        System.out.println("Enter the number of cleaner:");
        numCleaner = scan.nextInt();
        System.out.println("Enter the number of mechanics:");
        numMechanics = scan.nextInt();
        
        System.out.println("Bus Depot is open!");
        
        //Generate number of buses
        BusGenerator bg = new BusGenerator(dpot, numBus);
        Thread thbg = new Thread(bg);
        
        //Generate number of cleaners
        Cleaner c = new Cleaner(dpot, numCleaner);
        Thread thC = new Thread(c);
        
        //Generate number of mechanics
        Mechanic m = new Mechanic(dpot, numMechanics);
        Thread thM = new Thread(m);
        
        thbg.start();
        thdr.start();
        thC.start();
        thM.start();
        
        
    }
    
}
