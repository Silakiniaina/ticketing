package exception;

public class AuthException extends Exception{
    
    private String email;
    private String superMessage;

    /* -------------------------------------------------------------------------- */
    /*                                 Constructor                                */
    /* -------------------------------------------------------------------------- */
    public AuthException(String email, String superMessage){
        this.setEmail(email);
        this.setSuperMessage(superMessage);
    }
    
    /* -------------------------------------------------------------------------- */
    /*                                  Override                                  */
    /* -------------------------------------------------------------------------- */
    @Override
    public String getMessage() {
        return "Error while attempting to log with email : "+this.getEmail()+" : "+this.getSuperMessage();
    }

    /* -------------------------------------------------------------------------- */
    /*                                   Getters                                  */
    /* -------------------------------------------------------------------------- */
    public String getEmail() {
        return email;
    }
    public String getSuperMessage() {
        return superMessage;
    }

    /* -------------------------------------------------------------------------- */
    /*                                   Setters                                  */
    /* -------------------------------------------------------------------------- */
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSuperMessage(String superMessage) {
        this.superMessage = superMessage;
    }
}
