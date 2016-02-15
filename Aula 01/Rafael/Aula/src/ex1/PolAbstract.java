package ex1;

public abstract class PolAbstract {
    
    protected final int size;
    protected int i = 0;
    protected char[] array;
    
    public PolAbstract(int size){
        this.size = size;
        this.array = new char[size];
    }
    
    public void in(char c) throws ExceptionEx1{
        if(i >= size){
            throw new ExceptionEx1("Tamanhos errados!");
        }
        this.array[i++] = c;
    }
    
    public abstract char out() throws ExceptionEx1;
    
    @Override
    public boolean equals(Object b){
        PolAbstract bb = (PolAbstract)b;
        PolAbstract aa = (PolAbstract)this;
                
        if(bb.size != size){
            return false;
        }
        
        boolean comp = true;
        
        try{
            for(int i = 0; i<size; i++){
                comp = comp && (bb.out() == aa.out());
            }
            return comp;
        }catch(ExceptionEx1 e){
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    public boolean full(){
        return (i-1) == size;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
