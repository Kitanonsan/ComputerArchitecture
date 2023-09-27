package Tokenizer;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void InstructionFormatTest(){
            String s = "start2 start2 add 1 2 3 45";
            assertTrue(s.matches(Format.InstructionFormat));

    }

    @Test
    void AddTest(){
            String s = "add add 4 3 2";
            assertTrue(Tokenizer.FormatCheck(s));
    }

    @Test
    void NandTest(){
        int j = 3;
        System.out.println(Integer.toBinaryString((~j)+1));
    }

    @Test
    void LwTest(){

    }

    @Test
    void SwTest(){
        String s = "FF .fill 5 alsk;djf;l asdfasd";
        assertTrue(s.matches(Format.Fill_format));
    }

    @Test
    void BeqTest(){

    }

    @Test
    void JalrTest(){

    }

    @Test
    void HaltTest(){

    }

    @Test
    void NoopTest(){

    }

}