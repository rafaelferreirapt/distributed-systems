package ex1;

public class StackChar {
    private char[] array;
    private int realsize;
    
    public StackChar(char[] array, int size){
        this.array = new char[size];
        this.array = array;
        this.realsize = size;
    }
    
    public char pop(){
        assert !isEmpty();
        char pop = array[realsize-1];
        realsize--;
        return pop;
    }
    
    private boolean isEmpty(){
	return realsize==0;
    }
  
}
