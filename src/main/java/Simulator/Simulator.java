package Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
    private static final int NUMMEMORY = 65536; //Maximum number of words in memory
    private static final int NUMREGS = 8; //number of machine registers
    private static final String filename = "src/IOFile/Machine-code.txt"; //File receive from Assembler (Machine-Code)

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
        //Initialize the simulator state
        State state = new State();

        //Read machine code instructions from the file
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (state.numMemory >= NUMMEMORY) {
                    System.err.println("error: memory is full");
                    System.exit(1);
                }

                //Parse as a decimal integer
                int decimalValue = Integer.parseInt(line);

                //Add the binaryValue to memory
                state.mem[state.numMemory] = decimalValue;
                state.numMemory++;

                //Print the memory value
                System.out.println("memory[" + (state.numMemory - 1) + "]=" + decimalValue);
            }

        } catch (IOException e) {
            //Handle file not found
            System.err.println("error: can't find file " + filename);
            e.printStackTrace();
            System.exit(1);
        }


        int sum=0;
        while (true) {

            //Print the current state before executing each instruction
            printState(state);

            //Fetch the current instruction from memory
            int intValue = state.mem[state.pc];
            String binaryValue = toTwosComplementBinary(intValue);
            int opcode = extractOpcode(binaryValue);
            String opcodeBinary = String.format("%03d", Integer.parseInt(Integer.toBinaryString(opcode)));
            int regA,regB,destReg,offsetField;
            int memoryAddress;
            switch (opcodeBinary) {
                case "000" -> { //add operation
                    //Seperate register and destination position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    destReg = Integer.parseInt(binaryValue.substring(29, 32), 2);

                    //Execute
                    state.reg[destReg] = state.reg[regA] + state.reg[regB];

                    //program counter Increase
                    state.pc++;
                    sum++;
                }
                case "001" -> { //nand operation
                    // Separate register and destination position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    destReg = Integer.parseInt(binaryValue.substring(29, 32), 2);

                    //Execute
                    state.reg[destReg] = ~(state.reg[regA] & state.reg[regB]);
                    //program counter Increase
                    state.pc++;
                    sum++;
                }
                case "010" -> { //load operation
                    //Separate register and offset field position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);

                    //Calculate memory address(adding offsetField to regA)
                    memoryAddress = state.reg[regA] + offsetField;
                    //Check if memoryAddress is within bounds
                    if (memoryAddress < 0 || memoryAddress >= NUMMEMORY) {
                        System.err.println("ERROR: Invalid memory address");
                        System.exit(1);
                    }

                    //Load value from memory to regB
                    state.reg[regB] = state.mem[memoryAddress];

                    //program counter Increase
                    state.pc++;
                    sum++;
                }
                case "011" -> { // store operation
                    //Separate register and offset field position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);

                    //Calculate memory address(adding offsetField to regA)
                    memoryAddress = state.reg[regA] + offsetField;
                    //Check if memoryAddress is within bounds
                    if (memoryAddress < 0 || memoryAddress >= NUMMEMORY) {
                        System.err.println("ERROR: Invalid memory address");
                        System.exit(1);
                    }

                    //Store value from regBto memory at destination address
                    state.mem[memoryAddress] = state.reg[regB];
                    //program counter Increase
                    state.pc++;
                    sum++;
                }
                case "100" -> { //branch equal operation
                    //Separate register and offset field position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(16, 32), 2);
                    offsetField = convertNum(offsetField);

                    // Calculate the new address
                    int newAddress = state.pc + 1 + offsetField;

                    // Check if the new address is non-negative and within valid memory range
                    if (newAddress >= 0 && newAddress < NUMMEMORY) {
                        // Check regA and regB is equal?
                        if (state.reg[regA] == state.reg[regB]) {
                            //Jump to the address PC + 1 + offsetField
                            sum++;
                            state.pc = newAddress;
                        } else {
                            //program counter Increase
                            state.pc++;
                            sum++;
                        }
                    } else {
                        // Handle error: Print an error message and exit
                        System.err.println("ERROR: Invalid memory address in branch equal operation");
                        System.exit(1);
                    }

                }
                case "101" -> { // jump and link return
                    //Separate register  position
                    regA = Integer.parseInt(binaryValue.substring(10, 13), 2);
                    regB = Integer.parseInt(binaryValue.substring(13, 16), 2);

                    //Store value PC + 1 to regB
                    state.reg[regB] = state.pc + 1;


                    // Check if the new address is non-negative and within valid memory range
                    if (state.reg[regA] >= 0 && state.reg[regA] < NUMMEMORY) {
                        //If regA and regB equal, store PC + 1 in regB
                        if (regA == regB) {
                            state.pc++;
                        } else {
                            //Jump to address that stored in regA
                            state.pc = state.reg[regA];
                        }
                        sum++;
                    } else {
                        // Handle error: Print an error message and exit
                        System.err.println("ERROR: Invalid memory address in jump and link return operation");
                        System.exit(1);
                    }

                }
                case "110" -> { // halt end of program
                    //stop program
                    sum++;
                    System.out.println("machine halted");
                    System.out.println("total of " + sum + " instructions executed");
                    System.out.println("final state of machine:");
                    state.pc++;

                    printState(state);
                    return;
                }
                case "111" -> { // noop
                    //Do nothing
                    sum++;
                }

                default -> {
                    //Handle unknown opcode
                    System.err.println("ERROR: Memory at location " + state.pc + " illegible.");
                    System.exit(1);
                }
            }

        }
    }

    //Define the convertNum function
    static int convertNum(int num) {
        //Change 16bit 2's complement number to signed integer
        if ((num & (1 << 15)) != 0) {
            num -= (1 << 16);
        }
        return num;
    }
    //Separate opcode out of binary instruction
    static int extractOpcode(String binaryValue) {
        // Shift right 22 bit then AND with 0b111 to get opcode
        return (Integer.parseInt(binaryValue, 2) >> 22) & 0b111;
    }

    //Change integer value to a 32bit 2's complement binary
    static String toTwosComplementBinary(int value) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(value));
        int length = binary.length();

        //Make sure that binary is in 32bit long by adding 0
        while (length < 32) {
            binary.insert(0, "0");
            length++;
        }

        //If value is positive, return the 2's complement binary
        if (value >= 0) {
            return binary.toString();
        }
        //If value is negative, return the 2's complement binary format

        return binary.toString();

    }

    //Print state of the simulator
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

