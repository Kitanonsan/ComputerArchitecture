package Tokenizer;

public class Tokenizer {
    private String src;
    private String next;
    int pos;

    public Tokenizer(){

    }

    public boolean hasNextToken(){
        return next != null;
    }

}
