package Assembler;

import Tokenizer.Format;
import Tokenizer.Tokenizer;

import java.io.*;
import java.util.*;

import Error.OffsetOutofRange;
import Error.InvalidRegister;
import Error.UndefineLabels;
import Error.DuplicateLabel;

public class Assembler {
    private HashMap<String, Integer> LabelValue;
    private HashMap<String, Integer> LabelLine;
    private List<String> instruction;
    private List<String> machine_code;

    public Assembler() {
        File myObj = new File("src/IOFile/Input.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        instruction = new ArrayList<String>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            StringBuilder strBuild = new StringBuilder();
            Tokenizer tkz = new Tokenizer(data);
            while (tkz.hasNext()) {
                strBuild.append(tkz.next() + " ");
            }
            instruction.add(strBuild.toString());
        }
        LabelValue = new HashMap();
        LabelLine = new HashMap();
        machine_code = new ArrayList<>();
        LabelMapping();
        MachineCode();
        try {
            File fileout = new File("src/IOFile/output.txt");
            FileOutputStream fos = new FileOutputStream(fileout);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            for (int i = 0; i < machine_code.size(); i++) {
                String Decimal = "";
                if (machine_code.get(i).length() == 32) {
                    Decimal = String.valueOf(Integer.parseInt(machine_code.get(i), 2));
                } else {
                    Decimal = String.valueOf((short) Integer.parseInt(machine_code.get(i), 2));
                }
                writer.write(Decimal);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LabelMapping() throws UndefineLabels, DuplicateLabel {
        for (int i = 0; i < instruction.size(); i++) {
            String data = (String) instruction.get(i);
            StringTokenizer tkz = new StringTokenizer(data);
            String s = tkz.nextToken();
            String LabelMap = s;
            if (s.matches(Format.Label) && tkz.hasMoreTokens()) {
                s = tkz.nextToken();
                if (!LabelLine.containsKey(LabelMap)) {
                    if (s.matches(Format.Opcode)) {
                        //hashMap.put(LabelMap,i);
                        LabelLine.put(LabelMap, i);
                    }
                    if (s.matches(Format.Fill)) {
                        s = tkz.nextToken();
                        LabelLine.put(LabelMap, i);
                        if (s.matches(Format.Numeric)) {
                            LabelLine.put(LabelMap, i);
                            LabelValue.put(LabelMap, Integer.parseInt(s));
                        } else if (s.matches(Format.Label)) {
                            if (LabelLine.containsKey(s)) {
                                LabelLine.put(LabelMap, i);
                                LabelValue.put(LabelMap, LabelLine.get(s));
                            } else {
                                throw new UndefineLabels(LabelMap);
                            }
                        }
                    }
                } else {
                    throw new DuplicateLabel(LabelMap);
                }
            }
        }
        //printHashMap();
    }


    private void printHashMap() {
        System.out.println(LabelValue.keySet() + " -- " + LabelValue.values());
        System.out.println(LabelLine.keySet() + " -- " + LabelLine.values());
    }

    private void printInstruction() {
        System.out.println(instruction);
    }

    private void MachineCode() {
        for (int i = 0; i < instruction.size(); i++) {
            Tokenizer tkz = new Tokenizer(instruction.get(i));
            StringBuilder binary = new StringBuilder();
            binary.append("0000000");
            if (instruction.get(i).matches(Format.R_format)) {
                String opcode = tkz.next();
                if (opcode.matches(Format.Label) && !opcode.matches(Format.Opcode)) {
                    opcode = tkz.next();
                }
                if (opcode.matches("add")) {
                    binary.append("000");
                } else if (opcode.matches("nand")) {
                    binary.append("001");
                }
                String regA = tkz.next();
                String regB = tkz.next();
                String destReg = tkz.next();
                binary.append(regNumber(regA));
                binary.append(regNumber(regB));
                binary.append("0000000000000");
                binary.append(regNumber(destReg));
            } else if (instruction.get(i).matches(Format.I_format)) {
                String opcode = tkz.next();
                if (opcode.matches(Format.Label) && !opcode.matches(Format.Opcode)) {
                    opcode = tkz.next();
                }
                if (opcode.matches("lw")) {
                    binary.append("010");
                } else if (opcode.matches("sw")) {
                    binary.append("011");
                } else if (opcode.matches("beq")) {
                    binary.append("100");
                }
                String regA = tkz.next();
                String regB = tkz.next();
                String offsetField = tkz.next();
                if (offsetField.matches(Format.Label) && (opcode.matches("beq"))) {
                    if (LabelValue.containsKey(offsetField)) {
                        offsetField = LabelValue.get(offsetField).toString();
                    } else {
                        offsetField = LabelLine.get(offsetField).toString();
                    }
                    int jumpValue = Integer.parseInt(offsetField) - (i + 1);
                    offsetField = Integer.toString(jumpValue);
                } else if (offsetField.matches(Format.Label) && (opcode.matches("lw|sw"))) {
                    if (!LabelLine.containsKey(offsetField))
                        throw new UndefineLabels("using undefine label : " + offsetField);
                    offsetField = LabelLine.get(offsetField).toString();
                }
                binary.append(regNumber(regA));
                binary.append(regNumber(regB));
                binary.append(twoComplement(offsetField));
            } else if (instruction.get(i).matches(Format.J_format)) {
                String opcode = tkz.next();
                if (opcode.matches(Format.Label) && !opcode.matches(Format.Opcode)) {
                    opcode = tkz.next();
                }
                binary.append("101");
                String regA = tkz.next();
                String regB = tkz.next();
                binary.append(regNumber(regA));
                binary.append(regNumber(regB));
                binary.append("0000000000000000");
            } else if (instruction.get(i).matches(Format.O_format)) {
                String opcode = tkz.next();
                if (opcode.matches(Format.Label) && !opcode.matches(Format.Opcode)) {
                    opcode = tkz.next();
                }
                if (opcode.matches("halt")) {
                    binary.append("110");
                } else if (opcode.matches("noop")) {
                    binary.append("111");
                }
                binary.append("0000000000000000000000");
            } else if (instruction.get(i).matches(Format.Fill_format)) {
                tkz.next();
                tkz.next();
                String number = tkz.next();
                binary.delete(0, binary.length());
                if (number.matches(Format.Label)) {
                    number = LabelLine.get(number).toString();
                }
                binary.append(twoComplement(number));
            }
            machine_code.add(binary.toString());
        }
    }

    //convert decimal number into Two's complement
    public static String twoComplement(String number) {
        int n = Integer.parseInt(number);
        if (-32768 <= n && n <= 32767) {
            String twoComplement;
            StringBuilder str;
            String result;
            if (n < 0) {
                n = -1 * n;
                twoComplement = Integer.toBinaryString(((~n) + 1));
                str = new StringBuilder(twoComplement);
                result = str.substring(str.length() - 16, str.length());
            } else {
                twoComplement = Integer.toBinaryString(n);
                str = new StringBuilder(twoComplement);
                StringBuilder extend = new StringBuilder();
                while (str.length() + extend.length() < 16) {
                    extend.append("0");
                }
                result = extend.append(str).toString();
            }
            return result;
        } else {
            throw new OffsetOutofRange(number + " : The number must between -32768 to 32767");
        }
    }

    //require: number of register
    //return: number of register in binary
    private static String regNumber(String number) {
        if (Integer.parseInt(number) < 0 && Integer.parseInt(number) > 7)
            throw new InvalidRegister("Invalid Register number : " + number);
        String s = "";
        switch (number) {
            case "0":
                s = "000";
                break;
            case "1":
                s = "001";
                break;
            case "2":
                s = "010";
                break;
            case "3":
                s = "011";
                break;
            case "4":
                s = "100";
                break;
            case "5":
                s = "101";
                break;
            case "6":
                s = "110";
                break;
            case "7":
                s = "111";
                break;
        }
        return s;
    }

    //print machine code in binary
    private void printBinaryMachineCode() {
        for (int i = 0; i < machine_code.size(); i++)
            System.out.println(machine_code.get(i));
    }

    //print machine code in decimal
    private void printDecimalMachineCode() {
        for (int i = 0; i < machine_code.size(); i++) {
            if (machine_code.get(i).length() == 32) {
                System.out.println(Integer.parseInt(machine_code.get(i), 2));
            } else {
                short labelValue = (short) Integer.parseInt(machine_code.get(i), 2);
                System.out.println(labelValue);
            }

        }
    }
}
