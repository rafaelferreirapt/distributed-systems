package ex1;

public class Fifo extends PolAbstract{
    
    private int tail, head = 0;
    
    public Fifo(int size){
        super(size);
    }

    @Override
    public void in(char c) {
        array[head++] = c;
    }

    @Override
    public boolean equals(Object b) {
        return true;
    }

    @Override
    public char out() throws ExceptionEx1 {
        if(tail>=head){
            throw new ExceptionEx1("head > tail :s");
        }
        return array[tail++];
    }

}
