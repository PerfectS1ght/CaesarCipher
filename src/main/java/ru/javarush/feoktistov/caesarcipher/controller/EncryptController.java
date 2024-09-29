package ru.javarush.feoktistov.caesarcipher.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import ru.javarush.feoktistov.caesarcipher.BruteForce;
import ru.javarush.feoktistov.caesarcipher.Cipher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptController {

    private File chosenFile;
    @FXML
    private TextField insertKeyField;

    @FXML
    private TextField insertKeyFieldDec;

    @FXML
    protected void onChoseFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        chosenFile = fileChooser.showOpenDialog(null);
    }

    @FXML
    protected void onDoEncryptButtonClick() {
        try {
            if(chosenFile == null) {
                showErrorAlert("Вы не выбрали файл для шифрования","Выберите файл для шифрования.");
                return;
            }

            Path path = Paths.get(chosenFile.getAbsolutePath());
            String mimeType = Files.probeContentType(path);
            if (!"text/plain".equals(mimeType)) {
                showErrorAlert("Неверный формат файла", "Выберите текстовый файл.");
                return;
            }
            int key = Integer.parseInt(insertKeyField.getText());
            if (key < 0) {
                showErrorAlert("Недопустимый ключ", "Ключ должен быть положительным числом.");
                return;
            }
            Cipher cipher = new Cipher(key);
            cipher.doEncrypt(chosenFile);
            showSuccessMessage();
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка ввода", "Пожалуйста, введите корректное число для ключа.");
        } catch (IOException e) {
            showErrorAlert("Ошибка файла", "Не удалось определить тип файла.");
        }
    }
    @FXML
    protected void onDoDecryptButtonClick() {
        try {
            if(chosenFile == null) {
                showErrorAlert("Вы не выбрали файл для дешифровки","Выберите файл для дешифровки.");
                return;
            }
            Path path = Paths.get(chosenFile.getAbsolutePath());
            String mimeType = Files.probeContentType(path);
            if (!"text/plain".equals(mimeType)) {
                showErrorAlert("Неверный формат файла", "Выберите текстовый файл.");
                return;
            }
            int key = Integer.parseInt(insertKeyFieldDec.getText());
            if (key < 0) {
                showErrorAlert("Недопустимый ключ", "Ключ должен быть положительным числом.");
                return;
            }
            Cipher cipher = new Cipher(key);
            cipher.doDecrypt(chosenFile);
            showSuccessMessage();
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка ввода", "Пожалуйста, введите корректное число для ключа.");
        } catch (IOException e) {
            showErrorAlert("Ошибка файла", "Не удалось определить тип файла.");
        }
    }
    @FXML
    protected void onDoBruteForceButtonClick() {
        try {
            if(chosenFile == null) {
                showErrorAlert("Вы не выбрали файл для взлома","Выберите файл для взлома.");
                return;
            }
            Path path = Paths.get(chosenFile.getAbsolutePath());
            String mimeType = Files.probeContentType(path);
            if (!"text/plain".equals(mimeType)) {
                showErrorAlert("Неверный формат файла", "Выберите текстовый файл.");
                return;
            }
            BruteForce bruteForce = new BruteForce(new Cipher());
            bruteForce.doHack(chosenFile);
            showSuccessMessage();
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка ввода", "Пожалуйста, введите корректное число для ключа.");
        } catch (IOException e) {
            showErrorAlert("Ошибка файла", "Не удалось определить тип файла.");
        }

    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Операция завершена");
        alert.setHeaderText(null);
        alert.setContentText("Задача успешно выполнена!");
        alert.showAndWait();
    }
}
