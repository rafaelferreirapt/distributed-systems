package ex1;

import java.util.Scanner;

public class Palindrome {
    
    public static void main(String[] args){
        System.out.println("Introduce a word or number: ");
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        char[] ola = a.toCharArray();
        StackChar s = new StackChar(ola, ola.length);
        FIFOChar f = new FIFOChar(ola, ola.length);
        for(int i = 0; i<ola.length; i++){
            if(s.pop() != f.out()){
                System.out.println("Not a palindrome");
                System.exit(0);
            }
        }
        
        System.out.println("It's a palindrome");
    }
}
