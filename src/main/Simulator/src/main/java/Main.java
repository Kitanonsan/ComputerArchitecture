import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8;      // number of machine registers
    private static final int MAXLINELENGTH = 1000;
    private static final String filename = "src/main/java/machine_code.txt"; // Change this to your file's name

    static class State {
        int pc;
        int[] mem;
        int[] reg;
        int numMemory;

        State() {
            pc = 0;
            mem = new int[NUMMEMORY];
            reg = new int[NUMREGS];
            numMemory = 0;
        }
    }

    public static void main(String[] args) {
        State state = new State();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (state.numMemory >= NUMMEMORY) {
                    System.err.println("error: memory is full");
                    System.exit(1);
                }

                int decimalValue = Integer.parseInt(line); // Parse as a decimal integer
//                System.out.println(decimalValue);
                String binaryValue = toTwosComplementBinary(decimalValue);
//                System.out.println("in main " + binaryValue);
//

                // Add the binaryValue to memory
//                state.mem[state.numMemory] = Integer.parseInt(binaryValue, 2);
                state.mem[state.numMemory] = decimalValue;
                state.numMemory++;

                // Print the memory value
                System.out.println("memory[" + (state.numMemory - 1) + "]=" + decimalValue);
            }

            // Now that memory is initialized, you can display the memory state
//            printMemoryState(state);

            // Execute logic based on opcode (you can call Execute.executeInstruction here)
        } catch (IOException e) {
            System.err.println("error: can't find file " + filename);
            e.printStackTrace();
            System.exit(1);
        }


//        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = fileReader.readLine()) != null) {
//                if (state.numMemory >= NUMMEMORY) {
//                    System.err.println("error: memory is full");
//                    System.exit(1);
//                }
//
//                int decimalValue = Integer.parseInt(line);
//                String binaryValue = toTwosComplementBinary(decimalValue);
//                int opcode = extractOpcode(binaryValue);
//                String opcodeBinary = String.format("%03d", Integer.parseInt(Integer.toBinaryString(opcode))); // Extend opcode to 3 bits
//
////                System.out.println("Opcode: " + opcodeBinary);
//
//
//                state.mem[state.numMemory] = Integer.parseInt(binaryValue, 2);
//                state.numMemory++;
//            }
//
//        } catch (IOException e) {
//            System.err.println("error: can't find file " + filename);
//            e.printStackTrace();
//            System.exit(1);
//        }
    }

//    static void printMemoryState(State state) {
//        System.out.println("\nMemory state:");
//        for (int i = 0; i < state.numMemory; i++) {
//            System.out.println("\tmemory[" + i + "]=" + state.mem[i]);
//        }
//    }

    static int extractOpcode(String binaryValue) {
        // Shift right 22 bits and perform a bitwise AND with 0b111 to extract opcode.
        return (Integer.parseInt(binaryValue, 2) >> 22) & 0b111;
    }

    static String toTwosComplementBinary(int value) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value));
//        System.out.println("in 2's complement " + binary);
        int length = binary.length();

        // Pad with leading 0s to get a fixed-length representation
        while (length < 32) {
            binary.insert(0, "0");
            length++;
        }

        // If the original value was positive, return the binary representation as is
        if (value >= 0) {
            return binary.toString();
        }
        return binary.toString();

//        // For negative values, calculate two's complement
//        StringBuilder complement = new StringBuilder();
//        boolean foundOne = false;
//
//        for (int i = binary.length() - 1; i >= 0; i--) {
//            char bit = binary.charAt(i);
//            if (!foundOne) {
//                complement.insert(0, bit);
//            } else {
//                complement.insert(0, bit == '0' ? '1' : '0');
//            }
//            if (bit == '1') {
//                foundOne = true;
//            }
//        }
//
//        return complement.toString();
    }



    static void printState(State state) {
        System.out.println("\n@@@\nstate:");
        System.out.println("\tpc " + state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < state.numMemory; i++) {
            System.out.println("\t\tmem[ " + i + " ] " + state.mem[i]);
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {
            System.out.println("\t\treg[ " + i + " ] " + state.reg[i]);
        }
        System.out.println("end state");
    }
}

