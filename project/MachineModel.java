package project;

import java.util.Map;
import java.util.TreeMap;

public class MachineModel{
    private class CPU{
        private int accumulator, instructionPointer, memoryBase;

        public void incrementIP(int val){
            instructionPointer += val;
        }
    }
    public final Map<Integer, Instruction> INSTRUCTIONS = new TreeMap();
    private CPU cpu = new CPU();
    private Memory memory = new Memory();
    private HaltCallback callback;
    private boolean withGUI;

    public MachineModel(){
        this(false, null);
    }

    public MachineModel(boolean withGUI, HaltCallback callback){
        this.withGUI = withGUI;
        this.callback = callback;

        //INSTRUCTION_MAP entry for "ADDI"
        INSTRUCTIONS.put(0xC, arg -> {
            cpu.accumulator += arg;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADD"
        INSTRUCTIONS.put(0xD, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            cpu.accumulator += arg1;
            cpu.incrementIP(1);
        });

        //INSTRUCTION_MAP entry for "ADDN"
        INSTRUCTIONS.put(0xE, arg -> {
            int arg1 = memory.getData(cpu.memoryBase+arg);
            int arg2 = memory.getData(cpu.memoryBase+arg1);
            cpu.accumulator += arg2;
            cpu.incrementIP(1);
        });

    }

}
