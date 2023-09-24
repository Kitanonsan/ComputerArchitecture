package Tokenizer;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void AddTest(){

    }

    @Test
    void NandText(){

    }

    @Test
    void LwText(){

    }

    @Test
    void SwText(){

    }

    @Test
    void BeqTest(){
        String tests = "beq 0 1 2";
        Tokenizer tkz = new Tokenizer("beq 0 1 2 goto end of program when reg1==0");
        StringBuilder str = new StringBuilder();
        while(tkz.hasNext()){
            str.append(tkz.next());
            if(tkz.hasNext())
                str.append(" ");
        }
        assertEquals(tests,str.toString());
    }

    @Test
    void JalrText(){

    }

    @Test
    void HaltTest(){

    }

    @Test
    void NoopTest(){

    }

}