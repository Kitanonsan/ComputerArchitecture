import Tokenizer.Tokenizer;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        Tokenizer tkz = new Tokenizer("start add 1 2 1 decrement reg1");
        while(tkz.hasNext()){
            System.out.print(tkz.next());
            System.out.print(" ");
        }
    }
}
