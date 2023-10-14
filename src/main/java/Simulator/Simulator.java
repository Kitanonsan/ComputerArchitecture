package Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
    private static final int NUMMEMORY = 65536; // maximum number of words in memory
    private static final int NUMREGS = 8; // number of machine registers
    private static final int MAXLINELENGTH = 1000;
    private static final String filename = "src/IOFile/output.txt"; //Change this to your file's name

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

    public Simulator() {
        State state = new State();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (state.numMemory >= NUMMEMORY) {
                    System.err.println("error: memory is full");
                    System.exit(1);
                }

                // Parse as a decimal integer
                int decimalValue = Integer.parseInt(line);

                // Add the binaryValue to memory
                state.mem[state.numMemory] = decimalValue;
                state.numMemory++;

                // Print the memory value
                System.out.println("memory[" + (state.numMemory - 1) + "]=" + decimalValue);
            }


            // Execute logic based on opcode (you can call Execute.executeInstruction here)
        } catch (IOException e) {
            System.err.println("error: can't find file " + filename);
            e.printStackTrace();
            System.exit(1);
        }


        int sum=0;
        while (true) {

            // Print the current state before executing each instruction
            printState(state);

            // Fetch the current instruction from memory
            int intValue = state.mem[state.pc];
            String binaryValue = toTwosComplementBinary(intValue);
            int opcode = extractOpcode(binaryValue);
            String opcodeBinary = String.format("%03d", Integer.parseInt(Integer.toBinaryString(opcode)));
            int regA,regB,destReg,offsetField;
            int memoryAddress;
            switch (opcodeBinary) {
                case "000" -> { // add

                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    destReg = Integer.parseInt(binaryValue.substring(29, 32), 2);

                    // Execute add instruction logic
                    state.reg[destReg] = state.reg[regA] + state.reg[regB];
                    state.pc++;
                    sum++;
                }
                case "001" -> { // nand
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    destReg = Integer.parseInt(binaryValue.substring(29, 32), 2);

                    // Execute nand instruction logic
                    state.reg[destReg] = ~(state.reg[regA] & state.reg[regB]);
                    // Increment the program counter
                    state.pc++;
                    sum++;
                }
                case "010" -> { // lw
                    // string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);

                    // Calculate the memory address by adding offsetField to regA
                    memoryAddress = state.reg[regA] + offsetField;
                    // Check if memoryAddress is within bounds
                    if (memoryAddress < 0 || memoryAddress >= NUMMEMORY) {
                        System.err.println("ERROR: Invalid memory address");
                        System.exit(1);
                    }

                    // Load the value from memory into regB
                    state.reg[regB] = state.mem[memoryAddress];

                    // Increment the program counter
                    state.pc++;
                    sum++;
                }
                case "011" -> { // sw
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);

                    // Calculate the memory address by adding offsetField to regA
                    memoryAddress = state.reg[regA] + offsetField;
                    // Check if memoryAddress is within bounds
                    if (memoryAddress < 0 || memoryAddress >= NUMMEMORY) {
                        System.err.println("ERROR: Invalid memory address");
                        System.exit(1);
                    }

                    // Store the value from regB into memory at the calculated address
                    state.mem[memoryAddress] = state.reg[regB];
                    // Increment the program counter
                    state.pc++;
                    sum++;
                }
                case "100" -> { // beq
                    // string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);


                    // Check if regA and regB are equal
                    if (state.reg[regA] == state.reg[regB]) {
                        // Jump to the address PC + 1 + offsetField
                        sum++;
                        state.pc += (1 + offsetField);
                    } else {
                        // Increment the program counter
                        sum++;
                        // Increment the program counter
                        state.pc++;
                    }
                }
                case "101" -> { // jalr
                    // string cutting
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);

                    // Store the value PC + 1 in regB
                    state.reg[regB] = state.pc + 1;

                    if (regA == regB) {
                        // If regA and regB are the same, store PC + 1 in regB first
                        state.pc++;
                    } else {
                        // Jump to the address stored in regA
                        state.pc = state.reg[regA];
                    }
                    sum++;
                }
                case "110" -> { // halt
                    // string cutting
                    sum++;
                    System.out.println("machine halted");
                    System.out.println("total of " + sum + " instructions executed");
                    System.out.println("final state of machine:");
                    state.pc++;

                    printState(state);
                    return;
                }
                case "111" -> { // noop
                    // Do nothing
                    sum++;
                }

                default -> {
                    // Handle unknown opcode
                    System.err.println("ERROR: Memory at location " + state.pc + " illegible.");
                    System.exit(1);
                }
            }

        }
    }


    // Define the convertNum function
    static int convertNum(int num) {
        if ((num & (1 << 15)) != 0) {
            num -= (1 << 16);
        }
        return num;
    }

    static int extractOpcode(String binaryValue) {
        // Shift right 22 bits and perform a bitwise AND with 0b111 to extract opcode.
        return (Integer.parseInt(binaryValue, 2) >> 22) & 0b111;
    }

    static String toTwosComplementBinary(int value) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value));
        //System.out.println("in 2's complement " + binary);
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

