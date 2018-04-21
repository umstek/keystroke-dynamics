/*
 * 
 * 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.umstek.biometrics.keystrokedynamics.features.FeatureModel;

/**
 *
 * @author wickramaranga
 */
public class FileIO {

    /**
     *
     * @param featureModel
     */
    public static void saveModel(FeatureModel featureModel) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("model.ser");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(featureModel);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public static FeatureModel loadModel() {
        try (
                FileInputStream fileInputStream = new FileInputStream("model.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            FeatureModel featureModel = (FeatureModel) objectInputStream.readObject();
            return featureModel;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public static boolean firstTime() {
        File f = new File("model.ser");
        return !(f.exists() && !f.isDirectory());
    }
}
