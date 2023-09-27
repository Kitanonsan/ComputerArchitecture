package Tokenizer;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void InstructionFormatTest(){
        String s = "five .fill 5 asdfasdfasd asdfasd asdfasdf ";
        assertTrue(s.matches(Format.Fill_format));

    }

    @Test
    void AddTest(){

    }

    @Test
    void NandTest(){

    }

    @Test
    void LwTest(){

    }

    @Test
    void SwTest(){

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