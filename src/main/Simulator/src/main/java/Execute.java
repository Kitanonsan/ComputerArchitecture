public class Execute {

    public static void executeInstruction(Main.State state, String opcodeBinary, String binaryValue) {

    }

    public static void main(String[] args) {
        Main.State state = new Main.State();

        // Read the binary instruction into a String variable binaryValue
        String binaryValue = "";

        while (state.pc < state.numMemory) {
            System.out.println("start");
            Main.printState(state); // Print the state before executing each instruction

            int opcode = Main.extractOpcode(binaryValue);
            String opcodeBinary = String.format("%03d", Integer.parseInt(Integer.toBinaryString(opcode)));

            Main.printState(state);
            switch (opcodeBinary) {
                case "000": // add
                    //string cutting
                    int regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    int regB = Integer.parseInt(binaryValue.substring(16, 19), 2);
                    int destReg = Integer.parseInt(binaryValue.substring(0, 3), 2);

                    // Execute add instruction logic
                    state.reg[destReg] = state.reg[regA] + state.reg[regB];
                    state.pc++;
                    break;

                case "001": // nand
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    regB = Integer.parseInt(binaryValue.substring(16, 19), 2);
                    destReg = Integer.parseInt(binaryValue.substring(0, 3), 2);

                    // Execute nand instruction logic
                    state.reg[destReg] = ~(state.reg[regA] & state.reg[regB]);
                    state.pc++;
                    break;

                case "010": // lw
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    regB = Integer.parseInt(binaryValue.substring(16, 19), 2);
                    int offsetField = Integer.parseInt(binaryValue.substring(0, 16), 2);

                    // Execute lw instruction logic
                    state.reg[regB] = state.mem[state.reg[regA] + offsetField];
                    state.pc++;
                    break;

                case "011": // sw
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    regB = Integer.parseInt(binaryValue.substring(16, 19), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(0, 16), 2);

                    // Execute sw instruction logic
                    state.mem[state.reg[regA] + offsetField] = state.reg[regB];
                    state.pc++;
                    break;

                case "100": // beq
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    regB = Integer.parseInt(binaryValue.substring(16, 19), 2);
                    offsetField = Integer.parseInt(binaryValue.substring(0, 16), 2);

                    // Execute beq instruction logic
                    if (state.reg[regA] == state.reg[regB]) {
                        state.pc += (offsetField + 1);
                    } else {
                        state.pc++;
                    }
                    break;

                case "101": // jalr
                    //string cutting
                    regA = Integer.parseInt(binaryValue.substring(19, 22), 2);
                    int regDest = Integer.parseInt(binaryValue.substring(16, 19), 2);

                    // Execute jalr instruction logic
                    state.reg[regDest] = state.pc + 1;
                    state.pc = state.reg[regA];
                    break;

                case "110": // halt
                    // Halt the machine (no additional logic required)
                    state.pc=-1;
                    break;

                case "111": // noop
                    // Do nothing except increment PC
                    state.pc++;
                    break;

                default:
                    // Handle unknown opcode
                    System.err.println("ERROR: Memory at location " + state.pc + " illegible.");
                    System.exit(1);
                    break;
            }
        }
        Main.printState(state);
    }
}