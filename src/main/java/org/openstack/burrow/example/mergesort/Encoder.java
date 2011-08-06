package org.openstack.burrow.example.mergesort;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import java.io.*;

public class Encoder {
    //Method for packing/unpacking an array into a base64 encoded string
    //adapted from OscarRyz's sample code at
    //http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string/134918#134918
    public static String pack(int[] A) {
        try {
            ByteArrayOutputStream base = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new Base64OutputStream(base));

            oos.writeObject(A);
            oos.close();
            return new String(base.toByteArray());
        } catch (IOException ioe) {
            //Impossible with memory output
            return null;
        }
    }

    public static int[] unpack(String S) {
        try {
            ByteArrayInputStream base = new ByteArrayInputStream(S.getBytes());
            ObjectInputStream ois = new ObjectInputStream(new Base64InputStream(base));
            int[] result = (int[]) ois.readObject();
            ois.close();
            return result;
        } catch (ClassCastException cce) {
            System.err.println("Error: Malformed job");
            System.exit(1);
        } catch (IOException ioe) {
            //Shouldn't be possible from an in memory source
        } catch (ClassNotFoundException cnfe) {
            //Impossible, pretty sure int[] exists everywhere
        }
        throw new Error("Should not be reached.");
    }

}
