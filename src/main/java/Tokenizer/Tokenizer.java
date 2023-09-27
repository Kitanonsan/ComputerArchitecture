package Tokenizer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Tokenizer {
    private StringTokenizer tkz;
    private Queue<String> queue;
    public Tokenizer(String src){
        queue = new LinkedList<>();
        this.tokenize(src);
    }
    private void tokenize(String src){
        if(FormatCheck(src)){
            tkz = new StringTokenizer(src);
            String s = tkz.nextToken();
            if(s.matches(Format.Field) && !s.matches(Format.Opcode)){
                queue.add(s);
                s = tkz.nextToken();
            }
            if(s.matches(Format.Opcode)){
                queue.add(s);
                if(s.matches(Format.R_Type)){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String destReg = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(destReg);

                }
                else if(s.matches(Format.I_type)){
                    String regA = tkz.nextToken();
                    String regB = tkz.nextToken();
                    String OffsetField = tkz.nextToken();
                    queue.add(regA);
                    queue.add(regB);
                    queue.add(OffsetField);
                }
                else if(s.matches(Format.J_type)){
                    String regA = tkz.nextToken();
                    String regB =tkz.nextToken();

                    queue.add(regA);
                    queue.add(regB);
                }
            }
            else if(s.matches(Format.Fill)){
                queue.add(s);
                String number = tkz.nextToken();
                queue.add(number);
            }
        }
        else{
            throw new IllegalArgumentException();
        }

    }
    public boolean FormatCheck(String s){
       return  s.matches(Format.Fill_format) || (s.matches(Format.InstructionFormat) && (s.matches(Format.R_format) || s.matches(Format.I_format) || s.matches(Format.J_format) || s.matches(Format.O_format)));
    }
    public String next(){
        return queue.poll();
    }
    public boolean hasNext(){
        return !queue.isEmpty();
    }
}
