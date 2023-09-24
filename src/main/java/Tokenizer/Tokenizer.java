package Tokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private String src;
    private String next;

    int pos;

    public Tokenizer(String src){
        this.src = src;

    }

    public boolean hasNextToken(){
        return next != null;
    }

//    public String peek(){
//
//    }
//
//    public String consume(){
//
//    }
//
//    public void computeNext(){
//
//    }
//
//    public boolean peek(String regex){
//
//    }
//
//    public void consume()
}
