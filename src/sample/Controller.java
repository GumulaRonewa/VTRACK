package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML private ImageView feed;
    @FXML private Button start;
    @FXML public static ImageView processed;
    @FXML private Text object;
    @FXML private Text cycle;
    @FXML private Text radius;
    @FXML private Text radius1;
    @FXML private Text radius11;
    public File file;

    @FXML
    public void fileChooser(ActionEvent actionEvent) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Video feed");
        int count=0;
        file = chooser.showOpenDialog(new Stage());
        feed.setImage(new Image(file.toURI().toString()));

    }
    @FXML
    public  void Process(ActionEvent e){

        if (file != null) {

            ImageProcessor im=new ImageProcessor();
             int[] array= im.Process(file);
            Image img=new Image("file:/C:/Users/gumula%20ronewa/IdeaProjects/VTRACK/boundingbox.png");
            object.setText(String.valueOf(array[1]));
            cycle.setText(String.valueOf(array[0]));
            radius.setText(String.valueOf(array[2]));
            radius1.setText(String.valueOf(array[3]));
            radius11.setText(String.valueOf(array[4]));
            feed.setImage(img);


        }



    }


    @FXML
    public void  Close(ActionEvent e){
        System.exit(0);
    }

}
