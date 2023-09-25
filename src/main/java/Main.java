import Tokenizer.Tokenizer;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try{
            File myObj = new File("../../Program/Program2.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                // System.out.println(data);
                Tokenizer tkz = new Tokenizer(data);
                while(tkz.hasNext()){
                        System.out.print(tkz.next());
                        System.out.print(" ");
                        }
                System.out.println("");
            
            }
        }catch(Exception e){
            System.out.println(e);
        }
    
}
