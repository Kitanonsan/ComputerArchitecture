package Tokenizer;
import  java.util.regex.Pattern;

public class Format {
    public static String Label = "[a-zA-Z]\\w{0,5}";
    public static String Field = "[0-7]|[a-zA-Z]\\w{0,5}";
    public static String Numeric = "-?[0-9]+";
    public static String Symbolic = "[0-7]|[a-zA-Z]\\w{0,5}";
    public static String Instruction = "add|nand|lw|sw|beq|jalr|halt|noop";
    public static String R_Type = "add|nand";
    public static String I_type = "lw|sw|beq";
    public static String J_type = "jalr";
    public static String O_type = "halt|noop";
    public static String Fill = ".fill";
}
