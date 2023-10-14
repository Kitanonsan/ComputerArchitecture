package Tokenizer;

public class Format {
    public static String Label = "[a-zA-Z]\\w{0,5}";
    public static String Field = "[0-7]|([a-zA-Z]\\w{0,5})";
    public static String Numeric = "-?[0-9]+";
    public static String Symbolic = "[a-zA-Z]\\w{0,5}";
    public static String Opcode = "add|nand|lw|sw|beq|jalr|halt|noop";
    public static String R_Type = "add|nand";
    public static String I_type = "lw|sw|beq";
    public static String J_type = "jalr";
    public static String O_type = "halt|noop";
    public static String Fill = ".fill";
    public static String InstructionFormat = "([a-zA-Z]\\w{0,5}\s)?(\s)*(add|nand|lw|sw|beq|jalr|halt|noop|([a-z])+)(\s+[0-7]|([a-zA-Z]\\w{0,5})){0,3}(\s.*)*";
    public static String R_format = "([a-zA-Z]\\w{0,5}\s)?(\s)*(add|nand)(\s+[0-7]){3,3}(\s.*)*";
    public static String I_format = "([a-zA-Z]\\w{0,5}\s)?(\s)*(lw|sw|beq)(\s+[0-7]){2,2}\s+((\\-?[0-9]+)|([a-zA-Z]\\w{0,5}))(\s.*)*";
    public static String J_format = "([a-zA-Z]\\w{0,5}\s)?(\s)*(jalr)(\s+[0-7]){2,2}(\s.*)*";
    public static String O_format = "([a-zA-Z]\\w{0,5}\s)?(\s)*(halt|noop)(\s.*)*";
    public static String Fill_format = "(\s)*[a-zA-Z]\\w{0,5}(\s)+\\.fill(\s)+((\\-?[0-9]+)|([a-zA-Z]\\w{0,5}))(\s)*(\s.*)*";

}
