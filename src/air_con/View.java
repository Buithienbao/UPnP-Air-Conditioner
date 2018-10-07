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
import java.lang.Integer;
public class View implements Initializable{
    private static Boolean isDeviceOn = true;
    private Integer mode = 0;
    public static final List<String> modeList = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("Dry");
                add("Auto");
                add("Cool");
                // etc
            }});

    @FXML
    Button powerBtn;
    @FXML
    Button tempInBtn;
    @FXML
    Button tempDeBtn;
    @FXML
    Button modeBtn;
    @FXML
    Label curTemp;
    @FXML
    Label curMode;
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
        curTemp.setText("23");
        curMode.setText(modeList.get(1));
        status.setText("On");
        mode = 1;
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

    @FXML
    private void increaseTemp(){
        int temp;
        temp = Integer.parseInt(curTemp.getText());
        if (isDeviceOn && temp < 32)
        {
            curTemp.setText(String.valueOf(temp+1));
        } else {
        }
    }

    @FXML
    private void decreaseTemp(){
        int temp;
        temp = Integer.parseInt(curTemp.getText());
        if (isDeviceOn && temp > 16)
        {
            curTemp.setText(String.valueOf(temp-1));
        } else {

        }
    }

    @FXML
    private void changeMode(){
        if(!isDeviceOn) return;
        if(this.mode == 1 || this.mode == 0)
        {
            this.mode++;
            curMode.setText(modeList.get(this.mode));
        } else if(this.mode == 2)
            {
                this.mode = 0;
                curMode.setText(modeList.get(this.mode));
            }
    }
}




