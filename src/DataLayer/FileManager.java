/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataLayer;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author NguyenDuy
 */
public class FileManager  {

    
    public <T> boolean saveDataToFile(List<T> list, String FName, String msg) {
        try {

            File f = new File(FName);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream o = new ObjectOutputStream(fos);
            for (T item : list) {
                o.writeObject(item);
            }
            fos.close();
            o.close();
            System.out.println(msg);
            return true; // Indicates a successful save
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
   
    
    public <T> boolean loadDataFromFile(List<T> list, String FName) {
        try {
            // check file exists
            File f = new File(FName);
            if (!f.exists()) {
                return false;
            }
            // read file
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream oi = new ObjectInputStream(fis);
            if (f.length() == 0) {
                System.err.println("File is empty");
            }
            boolean check = true;
            while (check) {
                try {
                    T c = (T) oi.readObject();
                    list.add(c);
                } catch (EOFException e) {
                    break;
                }
            }
            fis.close();
            oi.close();
        } catch (FileNotFoundException e) {
            // log error or throw exception
            System.err.println("File not found: " + FName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            // log error or throw exception
            System.err.println("Error reading from file: " + FName + e);
            return false;
        } catch (NumberFormatException e) {
            // log error or throw exception
            System.err.println("Error parsing double value from input: " + e.getMessage());
            return false;
        }
        return true;
    }
    
}


