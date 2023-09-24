package Tokenizer;
import  java.util.regex.Pattern;

public class AssemblyFormat {

    public static String Label = "[a-zA-Z]\\w{0,5}";

    public static String Opcode = "add|nand|lw|sw|beq|jalr|noop|halt";

}
