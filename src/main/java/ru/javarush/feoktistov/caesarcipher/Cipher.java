package ru.javarush.feoktistov.caesarcipher;


import java.io.*;
import java.util.*;

public class Cipher {

    private static final char[] LETTERS
            = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    private static final Map<Character, Integer> ALPHABET_MAP = new HashMap<>();
    static{
        for(int i = 0; i < LETTERS.length; i++) {
            ALPHABET_MAP.put(LETTERS[i], i);
        }
    }

    public int getLengthOfAlphabet() {
        return lengthOfAlphabet;
    }

    private int lengthOfAlphabet = LETTERS.length;

    public void setKEY(int KEY) {
        this.KEY = KEY;
    }

    private int KEY;
    private int SHIFT;
    private String textFromFile;
    private boolean isEncryptedFile;

    public Cipher(int key) {
        this.KEY = key;;
    }

    public Cipher() {
    }

    public int getKEY() {
        return KEY;
    }

    public void doEncrypt(File pathToFile) {
            FileManager fileManager = new FileManager();
            textFromFile = fileManager.readFile(pathToFile);
            SHIFT = KEY % LETTERS.length;
            char[] charsText = textFromFile.toCharArray();
            for(int i = 0; i < charsText.length; i++) {
                if(Character.isUpperCase(charsText[i])) {
                    char lowerCaseLetter = Character.toLowerCase(charsText[i]);
                    if(ALPHABET_MAP.containsKey(lowerCaseLetter)) {
                        int value = ALPHABET_MAP.get(lowerCaseLetter);
                        int newValue = (value + SHIFT) % LETTERS.length;
                        charsText[i] = Character.toUpperCase(getKeyFromMap(newValue));
                    }
                }else if(ALPHABET_MAP.containsKey(charsText[i])) {
                    int value = ALPHABET_MAP.get(charsText[i]);
                    int newValue = (value + SHIFT) % LETTERS.length;
                    charsText[i] = getKeyFromMap(newValue);
                }
            }
            String encryptedString = new String(charsText);
            isEncryptedFile = true;
            fileManager.writeFile(encryptedString, getPathToEncryptedFile(pathToFile));
    }

    public void doDecrypt(File pathToFile) {
        FileManager fileManager = new FileManager();
        textFromFile = fileManager.readFile(pathToFile);
        SHIFT = KEY % LETTERS.length;
        char[] charsText = textFromFile.toCharArray();

        for(int i = 0; i < charsText.length; i++) {
            if(Character.isUpperCase(charsText[i])) {
                char lowerCaseLetter = Character.toLowerCase(charsText[i]);
                if(ALPHABET_MAP.containsKey(lowerCaseLetter)) {
                    int value = ALPHABET_MAP.get(lowerCaseLetter);
                    int newValue = value - SHIFT;
                    if(newValue < 0) {
                        charsText[i] = Character.toUpperCase(getKeyFromMap(LETTERS.length + newValue));
                    }else{
                        charsText[i] = Character.toUpperCase(getKeyFromMap(newValue));
                    }
                }
            }else if(ALPHABET_MAP.containsKey(charsText[i])) {
                int value = ALPHABET_MAP.get(charsText[i]);
                int newValue = value - SHIFT;
                if(newValue < 0) {
                    charsText[i] = getKeyFromMap(LETTERS.length + newValue);
                }else{
                    charsText[i] = getKeyFromMap(newValue);
                }
            }
        }

        String decryptedString = new String(charsText);
        isEncryptedFile = false;
        fileManager.writeFile(decryptedString, getPathToEncryptedFile(pathToFile));
    }

    public void doBruteForce(File pathToFile) {

    }


    private String getPathToEncryptedFile(File pathToFile) {

        String fileName = pathToFile.getName();
        String parentDirectory = pathToFile.getParent();
        if(parentDirectory == null || parentDirectory.isEmpty()) {
            parentDirectory = "";
        }else {
            if(!parentDirectory.endsWith(File.separator)) {
                parentDirectory += File.separator;
            }
        }
        return isEncryptedFile ? parentDirectory + "encrypted_Key-" + getKEY() + "_" + fileName: parentDirectory + "decrypted_Key-" + getKEY() + "_" + fileName;
    }

    private Character getKeyFromMap(Integer valueToFind) {
        Character keyFound = null;
        for(Map.Entry<Character, Integer> entry : ALPHABET_MAP.entrySet()) {
            if(entry.getValue().equals(valueToFind)) {
                keyFound = entry.getKey();
                break;
            }
        }
        return keyFound;
    }

}
