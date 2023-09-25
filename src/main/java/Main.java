import Assembler.Assembler;
import Tokenizer.Tokenizer;
import Tokenizer.Format;
import java.sql.SQLOutput;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        File myObj = new File("src/Program/Program1.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> Instruction = new ArrayList<String>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            // System.out.println(data);
            StringBuilder strBuild = new StringBuilder();
            Tokenizer tkz = new Tokenizer(data);
            while (tkz.hasNext()) {
                //System.out.print(tkz.next() + " ");
                strBuild.append(tkz.next() + " ");
            }
//            System.out.println(strBuild);

            Instruction.add(strBuild.toString());
        }
        System.out.println(Instruction);
        Assembler Ass = new Assembler(Instruction);
        Ass.printHashMap();
        Ass.printInstruction();
    }
}
//    StringTokenizer tkz = new StringTokenizer("start add 1 2 1");
//    String s = tkz.nextToken();
//        if(s.matches(Format.Label)){
//                s = tkz.nextToken();
//                if(s.matches(Format.Instruction)){
//                System.out.println("True");
//                }
//                } else {
//                System.out.println("False");
//                }