package ru.javarush.feoktistov.caesarcipher;

import java.io.File;

public class BruteForce {
    private Cipher cipher;

    public BruteForce(Cipher cipher) {
        this.cipher = cipher;
    }


    public void doHack(File pathToFile) {
        for(int i = 1; i < cipher.getLengthOfAlphabet(); i++) {
            cipher.setKEY(i);
            cipher.doDecrypt(pathToFile);
        }
    }
}
