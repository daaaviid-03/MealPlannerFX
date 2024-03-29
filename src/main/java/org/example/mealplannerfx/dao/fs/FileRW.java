package org.example.mealplannerfx.dao.fs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class to read and write objects of type T into a file
 * @param <T> the class of the objects to save into the file
 */
public class FileRW <T> {
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
     * Obtain a list of all objects of type T from the file
     * @return list of all objects of type T from the file
     */
    public List<T> getAllObjects(){
        return getAllObjectsAs(t -> (true));
    }

    /**
     * Obtain a list of all objects of type T from the file that certificates the lambda function
     * @param toCertificate the lambda function to check
     * @return the list of objects that certificates the lambda function
     */
    public List<T> getAllObjectsAs(Predicate<T> toCertificate){
        return getAllObjectsAs(toCertificate, Integer.MAX_VALUE);
    }

    /**
     * Obtain a list of all objects of type T from the file that certificates the lambda function
     * @param toCertificate the lambda function to check
     * @param maxLength max length of the resulting list
     * @return the list of objects that certificates the lambda function
     */
    public List<T> getAllObjectsAs(Predicate<T> toCertificate, Integer maxLength){
        if (maxLength == null){
            maxLength = Integer.MAX_VALUE;
        }
        List<T> ts = new ArrayList<>();
        try (ObjectInputStream fileStream = new ObjectInputStream(new FileInputStream(fileName))){
            boolean fileEnded = false;
            while(!fileEnded){
                T typeClass = getTypeClassFromFileStream(fileStream);
                if (typeClass == null){
                    fileEnded = true;
                } else if(toCertificate.test(typeClass) && maxLength-- > 0) {
                    ts.add(typeClass);
                }
            }
        } catch (Exception e) {
            // The file doesn't exist
        }
        return ts;
    }

    /**
     * Obtain the object of type T from the file that certificate the lambda function
     * @param toCertificate the lambda function to check
     * @return the object that certificate the lambda function
     */
    public T getObjectAs(Predicate<T> toCertificate){
        try (ObjectInputStream fileStream = new ObjectInputStream(new FileInputStream(fileName))){
            boolean fileEnded = false;
            while(!fileEnded){
                T typeClass = getTypeClassFromFileStream(fileStream);
                if (typeClass == null){
                    fileEnded = true;
                } else if(toCertificate.test(typeClass)) {
                    return typeClass;
                }
            }
        } catch (Exception e) {
            // The file doesn't exist
        }
        return null;
    }

    /**
     * Get the object cast if there is one, if end of line, then returns null
     * @param fileStream the file stream to search the object
     * @return the object cast if there is one, if end of line, then returns null
     */
    @SuppressWarnings("unchecked")
    private T getTypeClassFromFileStream(ObjectInputStream fileStream){
        try{
            return (T) fileStream.readObject();
        } catch (Exception e){
            return null;
        }
    }


    /**
     * Save all the objects in the list in the file erasing their actual values
     * @param objectsList the list of objects to save
     */
    public void setAllObjects(List<T> objectsList){
        try (ObjectOutputStream fileOutStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))){
            for (T typeClass : objectsList){
                fileOutStream.writeObject(typeClass);
            }
        } catch (Exception e) {
            // No action
        }
    }

    /**
     * Save the object in the file erasing their actual value
     * @param object the object to save
     */
    public void setAllObjects(T object){
        List<T> ts = new ArrayList<>();
        ts.add(object);
        setAllObjects(ts);
    }

    /**
     * Deletes the objects that certificates the lambda function
     * @param toCertificate the lambda function of the objects to delete
     */
    public void deleteObjects(Predicate<T> toCertificate){
        List<T> allObjects = getAllObjectsAs(t -> (!toCertificate.test(t)));
        setAllObjects(allObjects);
    }

    /**
     * Append a list of objects into the file and deletes the ones that certificate the lambda
     * @param objectsList the list of objects to append into the file
     * @param toCertificate the lambda function of the objects to delete
     */
    public void appendObjectsWithout(List<T> objectsList, Predicate<T> toCertificate){
        List<T> allObjects = getAllObjectsAs(t -> (!toCertificate.test(t)));
        allObjects.addAll(objectsList);
        setAllObjects(allObjects);
    }
    /**
     * Append one object into the file and deletes the ones that certificate the lambda
     * @param object the object to append into the file
     * @param toCertificate the lambda function of the objects to delete
     */
    public void appendObjectsWithout(T object, Predicate<T> toCertificate){
        List<T> allObjects = getAllObjectsAs(t -> (!toCertificate.test(t)));
        allObjects.add(object);
        setAllObjects(allObjects);
    }
}
