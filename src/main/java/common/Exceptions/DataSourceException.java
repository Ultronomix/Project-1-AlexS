package common.Exceptions;

public class DataSourceException extends RuntimeException{
    public DataSourceException(Throwable cause){
        super("Something went wrong, check logs for details", cause);
    }
    public DataSourceException(String message, Throwable cause){
        super(message,cause);
    }
}
