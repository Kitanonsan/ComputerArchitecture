import java.util.*;


public class Main {
    public static void main(String[] args) {
        StringTokenizer st = new StringTokenizer("lw 0 1 five load reg1 with 5 (uses symbolic address)");
        while(st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
    }
}
