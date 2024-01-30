package org.example.mealplannerfx.dao.fs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class to read and write objects of type TypeClass into a file
 * @param <TypeClass> the class of the objects to save into the file
 */
public class FileRW <TypeClass> {
    /**
     * The name of the file to interact
     */
    private final String fileName;

    /**
     * Establish the file route
     * @param fileName the name of the file
     */
    public FileRW(String fileName){
        this.fileName = fileName;
    }

    /**
     * Obtain a list of all objects of type TypeClass from the file
     * @return list of all objects of type TypeClass from the file
     */
    public List<TypeClass> getAllObjects(){
        return getAllObjectsAs(typeClass -> (true));
    }

    /**
     * Obtain a list of all objects of type TypeClass from the file that certificates the lambda function
     * @param toCertificate the lambda function to check
     * @return the list of objects that certificates the lambda function
     */
    public List<TypeClass> getAllObjectsAs(Predicate<TypeClass> toCertificate){
        return getAllObjectsAs(toCertificate, Integer.MAX_VALUE);
    }

    /**
     * Obtain a list of all objects of type TypeClass from the file that certificates the lambda function
     * @param toCertificate the lambda function to check
     * @param maxLength max length of the resulting list
     * @return the list of objects that certificates the lambda function
     */
    @SuppressWarnings("unchecked")
    public List<TypeClass> getAllObjectsAs(Predicate<TypeClass> toCertificate, Integer maxLength){
        if (maxLength == null){
            maxLength = Integer.MAX_VALUE;
        }
        List<TypeClass> typeClasses = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream fileStream = new ObjectInputStream(fileInputStream);
            boolean fileEnded = false;
            while(!fileEnded){
                try {
                    TypeClass typeClass = (TypeClass) fileStream.readObject();
                    if(toCertificate.test(typeClass) && maxLength-- > 0) {
                        typeClasses.add(typeClass);
                    }
                } catch (ClassNotFoundException | IOException e) {
                    // End Of File
                    fileEnded = true;
                }
            }
            fileStream.close();
        } catch (Exception e) {
            // The file doesn't exist
        }
        return typeClasses;
    }

    /**
     * Obtain the object of type TypeClass from the file that certificate the lambda function
     * @param toCertificate the lambda function to check
     * @return the object that certificate the lambda function
     */
    @SuppressWarnings("unchecked")
    public TypeClass getObjectAs(Predicate<TypeClass> toCertificate){
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream fileStream = new ObjectInputStream(fileInputStream);
            boolean fileEnded = false;
            while(!fileEnded){
                try {
                    TypeClass typeClass = (TypeClass) fileStream.readObject();
                    if(toCertificate.test(typeClass)) {
                        return typeClass;
                    }
                } catch (ClassNotFoundException | IOException e) {
                    // End Of File
                    fileEnded = true;
                }
            }
            fileStream.close();
        } catch (Exception e) {
            // The file doesn't exist
        }
        return null;
    }

    /**
     * Save all the objects in the list in the file erasing their actual values
     * @param objectsList the list of objects to save
     */
    public void setAllObjects(List<TypeClass> objectsList){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream fileOutStream = new ObjectOutputStream(new BufferedOutputStream(fileOutputStream));
            for (TypeClass typeClass : objectsList){
                fileOutStream.writeObject(typeClass);
            }
            fileOutStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the object in the file erasing their actual value
     * @param object the object to save
     */
    public void setAllObjects(TypeClass object){
        List<TypeClass> typeClasses = new ArrayList<>();
        typeClasses.add(object);
        setAllObjects(typeClasses);
    }

    /**
     * Append a list of objects into the file
     * @param objectsList the list of objects to append into the file
     */
    public void appendObjects(List<TypeClass> objectsList){
        List<TypeClass> allObjects = getAllObjects();
        allObjects.addAll(objectsList);
        setAllObjects(allObjects);
    }
    /**
     * Append one object into the file
     * @param object the object to append into the file
     */
    public void appendObjects(TypeClass object){
        List<TypeClass> allObjects = getAllObjects();
        allObjects.add(object);
        setAllObjects(allObjects);
    }

    /**
     * Deletes the objects that certificates the lambda function
     * @param toCertificate the lambda function of the objects to delete
     */
    public void deleteObjects(Predicate<TypeClass> toCertificate){
        List<TypeClass> allObjects = getAllObjectsAs(typeClass -> (!toCertificate.test(typeClass)));
        setAllObjects(allObjects);
    }

    /**
     * Append a list of objects into the file and deletes the ones that certificate the lambda
     * @param objectsList the list of objects to append into the file
     * @param toCertificate the lambda function of the objects to delete
     */
    public void appendObjectsWithout(List<TypeClass> objectsList, Predicate<TypeClass> toCertificate){
        List<TypeClass> allObjects = getAllObjectsAs(typeClass -> (!toCertificate.test(typeClass)));
        allObjects.addAll(objectsList);
        setAllObjects(allObjects);
    }
    /**
     * Append one object into the file and deletes the ones that certificate the lambda
     * @param object the object to append into the file
     * @param toCertificate the lambda function of the objects to delete
     */
    public void appendObjectsWithout(TypeClass object, Predicate<TypeClass> toCertificate){
        List<TypeClass> allObjects = getAllObjectsAs(typeClass -> (!toCertificate.test(typeClass)));
        allObjects.add(object);
        setAllObjects(allObjects);
    }
}
