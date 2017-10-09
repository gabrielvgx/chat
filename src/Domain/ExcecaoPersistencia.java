package Domain;

/**
 * @author bella
 */

public class ExcecaoPersistencia extends Exception{
    
    public ExcecaoPersistencia(String msg){
        super(msg);
    }
    public ExcecaoPersistencia(Exception ex){
        super(ex);
    }

    public ExcecaoPersistencia(String message, Exception e) {
        super(message, e);
    }
}
