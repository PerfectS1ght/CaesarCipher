package ru.javarush.feoktistov.caesarcipher;

import java.io.*;

public class FileManager {
    String textFromFile;

    public String readFile(File pathToFile) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            textFromFile = sb.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return textFromFile;
    }

    public void writeFile(String content, String pathToFile) {
        try(FileWriter fileWriter = new FileWriter(pathToFile)) {
            fileWriter.write(content);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
