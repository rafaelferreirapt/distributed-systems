package ex1;

import java.util.Scanner;

public class Palindroma {
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Palavra: ");
        char[] pal = sc.next().toCharArray();
        
        Fifo f = new Fifo(pal.length);
        Stack s = new Stack(pal.length);
        
        for(int i=0; i<pal.length; i++){
            f.in(pal[i]);
            s.in(pal[i]);
        }
        
        if(s.equals(f)){
            System.out.println("É um Polindroma!");
        }else{
            System.out.println("Não é um polindroma!");
        }
    }
}
