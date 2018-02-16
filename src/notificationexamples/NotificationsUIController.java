/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    
    @FXML
    private Button task2Button;
    
    @FXML
    private Button task3Button;
    
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    private TaskState state1 = TaskState.STOPPED;
    private TaskState state2 = TaskState.STOPPED;
    private TaskState state3 = TaskState.STOPPED;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if(task1 == null || state1 == TaskState.STOPPED){
            System.out.println("start task 1");
            if (task1 == null) {
                task1 = new Task1(2147483647, 1000000);
                task1.setNotificationTarget(this);
                task1.start();
                state1 = TaskState.RUNNING;
            }
            task1Button.setText("End Task 1");
        }
        else if(state1 == TaskState.RUNNING){
            task1.end();
            task1 = null;
            task1Button.setText("Start Task 1");
            textArea.appendText("Task 1 stopped. \n");
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Button.setText("Start Task 1");
        }
        if (message.equals("Task2 done.")) {
            task1 = null;
            task1Button.setText("Start Task 2");
        }
        if (message.equals("Task3 done.")) {
            task1 = null;
            task1Button.setText("Start Task 3");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if(task2Button.getText().equals("Start Task 2")){
            System.out.println("start task 2");
            if (task2 == null || state2 == TaskState.STOPPED) {
                task2 = new Task2(2147483647, 1000000);
                task2.setOnNotification((String message) -> {
                    textArea.appendText(message + "\n");
                });

                task2.start();
                state2 = TaskState.RUNNING;
            }
            task2Button.setText("End Task 2");
        }
        else{
            task2.end();
            state2 = TaskState.STOPPED;
            task2Button.setText("Start Task 2");
            textArea.appendText("Task 2 stopped. \n");
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if(task3Button.getText().equals("Start Task 3")){
            System.out.println("start task 3");
            if (task3 == null || state3 == TaskState.STOPPED) {
                task3 = new Task3(2147483647, 1000000);
                // this uses a property change listener to get messages
                task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                    textArea.appendText((String)evt.getNewValue() + "\n");
                });

                task3.start();
            }
            task3Button.setText("End Task 3");
        }
        else{
            task3.end();
            task3Button.setText("Start Task 3");
            textArea.appendText("Task 3 stopped. \n");
        }
    } 
}
