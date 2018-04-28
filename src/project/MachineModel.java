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
        
        //Thomas's code starts here
        //NOP
        INSTRUCTIONS.put(0, arg -> { 
        	cpu.incrementIP(1);});
        
        //LODI
        INSTRUCTIONS.put(1, arg -> { 
        	cpu.accumulator = arg;
        	cpu.incrementIP(1);
        });
        
        //LOD
        INSTRUCTIONS.put(2, arg -> {
        	int arg1 = memory.getData(cpu.memoryBase + arg);
        	cpu.accumulator = arg1;
        	cpu.incrementIP(1);
        });
        
        //LODN
        INSTRUCTIONS.put(3, arg -> {
        	int val = memory.getData(cpu.memoryBase + arg);
        	cpu.accumulator = memory.getData(cpu.memoryBase + val);
        	cpu.incrementIP(1);
        });
        
        //STO
        INSTRUCTIONS.put(4, arg -> {
        	memory.setData(cpu.memoryBase += arg, cpu.accumulator);
        	cpu.incrementIP(1);
        });
        
        //STON
        INSTRUCTIONS.put(5, arg -> {
        	int val = memory.getData(cpu.memoryBase + arg);
        	memory.setData(cpu.memoryBase + val, cpu.accumulator);
        });
        
        //JMPR
        INSTRUCTIONS.put(6, arg -> {
        	cpu.instructionPointer += arg;
        });
        
        //JUMP
        INSTRUCTIONS.put(7, arg -> {
        	cpu.instructionPointer += memory.getData(cpu.memoryBase + arg);
        });
        
        //JUMPI
        INSTRUCTIONS.put(8, arg -> {
        	cpu.instructionPointer = arg;
        });
        
        //JUMPZR
        INSTRUCTIONS.put(9, arg -> {
        	if(cpu.accumulator == 0) {
        		cpu.instructionPointer += arg;
        	}
        	else {
        		cpu.incrementIP(1);
        	}
        });
        
        //JMPZ
        INSTRUCTIONS.put(0xA, arg -> {
        	if(cpu.accumulator == 0) {
        		cpu.instructionPointer += memory.getData(cpu.memoryBase + arg);
        	}
        	else {
        		cpu.incrementIP(1);
        	}
        });
        
        //JMPZI
        INSTRUCTIONS.put(0xB, arg -> {
        	if(cpu.accumulator == 0) {
        		cpu.instructionPointer = arg;
        	}
        	else {
        		cpu.incrementIP(1);
        	}        	
        });
        
        //SUBI
        INSTRUCTIONS.put(0xF, arg -> {
        	cpu.accumulator -= arg;
        	cpu.incrementIP(1);
        });
        
        //SUB
        INSTRUCTIONS.put(0x10
    }

}
