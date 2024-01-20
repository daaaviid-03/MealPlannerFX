package org.example.mealplannerfx.control;

public class WrongArgumentException extends Exception{
    private String wrongArgumentDescription = "";
    public WrongArgumentException(){
        setWrongArgumentDescription("Wrong argument");
    }
    public WrongArgumentException(String argumentDescription){
        setWrongArgumentDescription(argumentDescription);
    }
    public String getWrongArgumentDescription() {
        return wrongArgumentDescription;
    }
    public void setWrongArgumentDescription(String wrongArgumentDescription) {
        this.wrongArgumentDescription = wrongArgumentDescription;
    }
}