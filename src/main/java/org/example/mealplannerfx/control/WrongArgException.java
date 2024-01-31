package org.example.mealplannerfx.control;

public class WrongArgException extends Exception{
    private String wrongArgumentDescription = "";
    public WrongArgException(){
        super("Wrong argument");
        setWrongArgumentDescription("Wrong argument");
    }
    public WrongArgException(String argumentDescription){
        super(argumentDescription);
        setWrongArgumentDescription(argumentDescription);
    }
    public String getWrongArgumentDescription() {
        return wrongArgumentDescription;
    }
    public void setWrongArgumentDescription(String wrongArgumentDescription) {
        this.wrongArgumentDescription = wrongArgumentDescription;
    }
}