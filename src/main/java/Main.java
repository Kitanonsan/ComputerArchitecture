import Assembler.Assembler;
import Simulator.Simulator;
import java.math.*;

public class Main {
    public static void main(String[] args) {
        try{
            new Assembler();
            new Simulator();
        }
        catch (Error e){
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

}
