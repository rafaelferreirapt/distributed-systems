package ex1;

public class Stack extends PolAbstract{
    
    public Stack(int size){
        super(size);
    }

    @Override
    public void in(char c) {
        array[i++] = c;
    }

    @Override
    public char out() throws ExceptionEx1 {
        if(i > size && i != -1){
            throw new ExceptionEx1("i < size && i != 0");
        }
        
        return array[--i];
    }
    
}
