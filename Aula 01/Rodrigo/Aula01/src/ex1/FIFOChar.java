package ex1;

public class FIFOChar {
    private char[] array;
    private int realsize, actual;
    
    public FIFOChar(char[] array, int size){
        this.array = new char[size];
        this.array = array;
        this.realsize = size;
        this.actual = 0;
    }
    
    public char out(){
        assert !isEmpty();
        char pop = array[actual];
        actual++;
        realsize--;
        return pop;
    }
    
    private boolean isEmpty(){
	return realsize==0;
    }
}
