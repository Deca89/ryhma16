package Lukuvinkisto;

import lukuvinkisto.io.BookIO;
import lukuvinkisto.io.InputIO;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        run();
    }
    
    public static void run(){
        while (true){
            
            System.out.println("\nAnna sy�te: ");
            String[] input = InputIO.readInput().split(" ", 3);
            
            if(input[0].equals("")) break;
            else InputIO.manageInput(input);
            
        }
    }
    
    
        
    
    
}