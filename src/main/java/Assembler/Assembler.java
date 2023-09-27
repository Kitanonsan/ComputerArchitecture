package Assembler;

import Tokenizer.Format;

import java.util.*;

public class Assembler {
    private HashMap<String,Integer> hashMap;

    private List instruction;

    private List machine_code;

    public Assembler(List instruction){
       hashMap = new HashMap();
       this.instruction = instruction;
       LabelMapping();
    }

    public void LabelMapping(){
        for(int i = 0 ; i< instruction.size();i++){
            String data = (String) instruction.get(i);
            StringTokenizer tkz =  new StringTokenizer(data);
            String s = tkz.nextToken();
            String LabelMap = s;
            if(s.matches(Format.Label) && tkz.hasMoreTokens()){
                s = tkz.nextToken();
                if(s.matches(Format.Opcode)){
                    hashMap.put(LabelMap,i);
                }
                if(s.matches(Format.Fill)){
                    s = tkz.nextToken();
                    if(s.matches(Format.Numeric)){
                        hashMap.put(LabelMap,Integer.parseInt(s));
                    }else{
                       if(hashMap.containsKey(s)){
                           //System.out.println("same label in Loop1 in address ["+i+"]");
                       }
                    }
                }
            }
        }
        Loop2();
    }

    public void Loop2(){
        for(int i = 0 ; i< instruction.size();i++){
            String data = (String) instruction.get(i);
            StringTokenizer tkz =  new StringTokenizer(data);
            String s = tkz.nextToken();
            while (tkz.hasMoreTokens()){
               s = tkz.nextToken();
            }
            if(hashMap.containsKey(s)){
               data =  data.replaceAll("\\b"+s+"\\b", String.valueOf(hashMap.get(s)));
                System.out.println(data);
            }else{
                //System.out.println("Undefined index ["+i+"]");
            }
            instruction.set(i,data);
        }
    }
    public void printHashMap(){
        System.out.println(hashMap.keySet() + " -- " + hashMap.values());
    }

    public void printInstruction(){
        System.out.println(instruction);
    }

//    public void FindFill (){
//        String[] arrStr = s.split(" ");
//        for(String x : arrStr){
//            if(x.matches(Format.Fill)){
//                FillNum(arrStr);
//            }
//        }
//    }
//    public void FillNum(String[] arrStr){
//        map.put(arrStr[0],arrStr[2]);
//        System.out.println(map);
//    }
    private void MachineCode(){
        for(int i = 0; i < instruction.size(); i++){

        }
    }

    public static String twoComplement(String number){
        int n = Integer.parseInt(number);
        if(-32768 <= n && n <= 32767){
            String twoComplement;
            StringBuilder str;
            String result;
            if(n < 0){
                n = -1*n;
                twoComplement = Integer.toBinaryString(((~n)+1));
                str = new StringBuilder(twoComplement);
                result =str.substring(str.length()-16,str.length());
            }
            else {
                twoComplement =  Integer.toBinaryString(n);
                str = new StringBuilder(twoComplement);
                StringBuilder extend = new StringBuilder();
                while(str.length() + extend.length() < 16){
                    extend.append("0");
                }

                result = extend.append(str).toString();
            }
            return result;
        }
        else{
            throw new ArithmeticException("too many");
        }
    }
}
