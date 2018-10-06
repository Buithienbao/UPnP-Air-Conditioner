package air_con;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class View implements Initializable{
    private static Boolean isDeviceOn = true;
    public static final List<String> fanModeList = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("dry");
                add("heat");
                // etc
            }});

    @FXML
    Button powerBtn;
    @FXML
    Button tempInBtn;
    @FXML
    Button tempDeBtn;
    @FXML
    Button fanBtn;
    @FXML
    Label curTemp;
    @FXML
    Label curFanMode;
    @FXML
    Label status;
    @FXML
    ImageView powerIcon;
    @FXML
    ImageView statusImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        powerIcon.setImage( new Image("/resources/images/powerOnBtn.png"));
        statusImg.setImage( new Image("/resources/images/air_con.gif"));
        status.setText("On");
    }

    @FXML
    private void switchPower(){
        if(isDeviceOn)
        {
            powerIcon.setImage( new Image("/resources/images/powerOffBtn.png"));
            statusImg.setImage( new Image("/resources/images/air_con.jpg"));
            status.setText("Off");
            isDeviceOn = false;
        } else {
            powerIcon.setImage( new Image("/resources/images/powerOnBtn.png"));
            statusImg.setImage( new Image("/resources/images/air_con.gif"));
            status.setText("On");
            isDeviceOn = true;
        }
    }
}
