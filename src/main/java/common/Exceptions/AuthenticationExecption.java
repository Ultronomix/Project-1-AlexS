package common.Exceptions;

public class AuthenticationExecption  extends RuntimeException{
    public AuthenticationExecption(){
        super("Could not find a user account with the provided credentials");
    }
}


