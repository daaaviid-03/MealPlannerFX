package org.example.mealplannerfx.control;

public class WrongArgException extends Exception{
    private final String wrongArgumentDescription;
    public WrongArgException(){
        super("Wrong argument");
        this.wrongArgumentDescription = "Wrong argument";
    }
    public WrongArgException(String argumentDescription){
        super(argumentDescription);
        this.wrongArgumentDescription = argumentDescription;
    }
    public String getWrongArgumentDescription() {
        return wrongArgumentDescription;
    }
}