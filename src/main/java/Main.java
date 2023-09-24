import Tokenizer.Tokenizer;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Tokenizer tkz = new Tokenizer("five .fill 5");
        while(tkz.hasNext()){
            System.out.println(tkz.next());
        }
    }
}
