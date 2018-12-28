package com.bateman.rich.rmblibrary.persistence;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** A class to help with serializing an object to and from a String.
 *  From: https://stackoverflow.com/questions/5816695/android-sharedpreferences-with-serializable-object
 */
public class ObjectSerializerHelper {

    static public String objectToString(Serializable object) {
        String encoded = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            encoded = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),0));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
        return encoded;
    }

    @SuppressWarnings("unchecked")
    static public Serializable stringToObject(String string) {
        byte[] bytes = Base64.decode(string,0);
        Serializable object = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream( new ByteArrayInputStream(bytes) );
            object = (Serializable)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
