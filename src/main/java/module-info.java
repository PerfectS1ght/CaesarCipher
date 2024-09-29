module ru.javarush.feoktistov.caesarcipher {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.xml;


    opens ru.javarush.feoktistov.caesarcipher to javafx.fxml;
    exports ru.javarush.feoktistov.caesarcipher;
    exports ru.javarush.feoktistov.caesarcipher.controller;
    opens ru.javarush.feoktistov.caesarcipher.controller to javafx.fxml;
}