package ccpassignment;

public class mainClass {

    
    public static void main(String[] args) {
        
        Depot dpot = new Depot();
        
        BusGenerator bg = new BusGenerator(dpot);
        
        Thread thbg = new Thread(bg);
        thbg.start();
    }
    
}
