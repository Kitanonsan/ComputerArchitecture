package Tokenizer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Tokenizer {
    private StringTokenizer tkz;
    private Queue<String> queue;

    public Tokenizer(String src){
        tkz = new StringTokenizer(src);
        queue = new LinkedList<>();
        this.tokenize();
    }

    private void tokenize(){
        String s = tkz.nextToken();
        if(s.matches(Format.Field) && !s.matches(Format.Instruction)){
            queue.add(s);
            s = tkz.nextToken();
        }
        if(s.matches(Format.Instruction)){
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

    public String next(){
        return queue.poll();
    }
    public boolean hasNext(){
        return !queue.isEmpty();
    }
}
