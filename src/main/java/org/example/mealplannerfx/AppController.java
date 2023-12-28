package org.example.mealplannerfx;

public class AppController{
    private DBController dbController;
    private GraphicController graphicController;

    public AppController(){
        this.getDB();
        this.startView();
    }

    public void getDB(){
        this.dbController = new DBFileController();
        //this.dbController = new DBJDBCController();
    }
    public void saveData(){
        this.dbController.saveDataToDB();
    }
    public void startView(){
        this.graphicController = new GraphicControllerColored();
        //this.graphicController = new GraphicControllerBW();

        this.graphicController.startView();
    }
    public void endProgram(){
        this.saveData();
        this.graphicController.endView();
    }
}
