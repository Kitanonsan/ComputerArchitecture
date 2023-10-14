package Tokenizer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import Error.UndefineOpcode;
import Error.IncorrectFormat;

public class Tokenizer {
    private StringTokenizer tkz;
    private Queue<String> queue;
    public Tokenizer(String src){
        queue = new LinkedList<>();
        this.tokenize(src);
    }
    public Tokenizer(String src,int line){
        queue = new LinkedList<>();
        this.tokenize(src,line);
    }

    private void tokenize(String src){
        //check format of input string
        if(FormatCheck(src)){
            tkz = new StringTokenizer(src);
            String s = tkz.nextToken();
            if(s.matches(Format.Label) && !s.matches(Format.Opcode)){
                queue.add(s);
                s = tkz.nextToken();
            }
            if(s.matches(Format.Opcode)){
                queue.add(s);
                if(s.matches(Format.R_Type ) && src.matches(Format.R_format) ){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String destReg = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(destReg);

                }
                else if(s.matches(Format.I_type ) && src.matches(Format.I_format) ){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String OffsetField = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(OffsetField);
                }
                else if(s.matches(Format.J_type ) && src.matches(Format.J_format) ){
                    String regA = tkz.nextToken();
                    String regB =tkz.nextToken();

                    queue.add(regA);
                    queue.add(regB);
                }
                else if(s.matches(Format.O_type) && src.matches(Format.O_format)){

                }
                else{
                    throw new IncorrectFormat(src + " : Incorrect format.");
                }
            }
            else if(s.matches(Format.Fill)){
                queue.add(s);
                String number = tkz.nextToken();
                queue.add(number);
            }
            else{
                throw new UndefineOpcode(src + " : Undefine opcode.");
            }
        }
        //
        else{
            throw new IncorrectFormat(src + " : Incorrect format.");
        }

    }
    private void tokenize(String src , int line){
        //check format of input string
        if(FormatCheck(src)){
            tkz = new StringTokenizer(src);
            String s = tkz.nextToken();
            if(s.matches(Format.Label) && !s.matches(Format.Opcode)){
                queue.add(s);
                s = tkz.nextToken();
            }
            if(s.matches(Format.Opcode)){
                queue.add(s);
                if(s.matches(Format.R_Type) && src.matches(Format.R_format) ){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String destReg = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(destReg);

                }
                else if(s.matches(Format.I_type) && src.matches(Format.I_format)){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String OffsetField = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(OffsetField);
                }
                else if(s.matches(Format.J_type ) && src.matches(Format.J_format) ){
                    String regA = tkz.nextToken();
                    String regB =tkz.nextToken();

                    queue.add(regA);
                    queue.add(regB);
                }
                else if(s.matches(Format.O_type) && src.matches(Format.O_format)){

                }
                else{
                    throw new IncorrectFormat("line " + line + " | error : incorrect instruction format | "+ src);
                }

            }
            else if(s.matches(Format.Fill)){
                queue.add(s);
                String number = tkz.nextToken();
                queue.add(number);
            }
            else{
                throw new UndefineOpcode("line " + line + " | error : undefined opcode | "+ queue.peek());
            }
        }
        //
        else{
            throw new IncorrectFormat("line " + line + " | error : incorrect instruction format | "+ src);
        }

    }



    //check format of input string
    public static boolean FormatCheck(String s){
       return  s.matches(Format.Fill_format) || (s.matches(Format.InstructionFormat));
    }

    //return next token of tokenizer
    public String next(){
        return queue.poll();
    }

    //check if tokenizer is empty
    public boolean hasNext(){
        return !queue.isEmpty();
    }
}
