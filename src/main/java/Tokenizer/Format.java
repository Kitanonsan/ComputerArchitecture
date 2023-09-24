package Tokenizer;
import  java.util.regex.Pattern;

public class Format {
    public static String Label = "[a-zA-Z]\\w{0,5}";
    public static String Instruction = "add|nand|lw|sw|beq|jalr|noop|halt";
    public static String Field = "[0-7]|[a-zA-Z]\\w{0,5}";

    public static Pattern label = Pattern.compile(Label);
    public static Pattern instruction = Pattern.compile(Instruction);
    public static Pattern field = Pattern.compile(Field);

}
