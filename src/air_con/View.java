package air_con;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.application.Platform;
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

    Controller controller;
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

        powerIcon.setImage( new Image("/resources/powerOnBtn.png"));
        statusImg.setImage( new Image("/resources/air_con.gif"));
        curTemp.setText(Integer.toString(Constant.DEFAULT_TEMP));
        curMode.setText(modeList.get(1));
        status.setText("On");
        mode = 1;
    }


    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @FXML
    private void switchPower(){
        if(isDeviceOn)
        {
            powerIcon.setImage( new Image("/resources/powerOffBtn.png"));
            statusImg.setImage( new Image("/resources/air_con.jpg"));
            status.setText("Off");
            isDeviceOn = false;
        } else {
            powerIcon.setImage( new Image("/resources/powerOnBtn.png"));
            statusImg.setImage( new Image("/resources/air_con.gif"));
            status.setText("On");
            isDeviceOn = true;
        }
    }

    @FXML
    private void increaseTemp(){
        int temp;
        temp = Integer.parseInt(curTemp.getText());
        if (isDeviceOn && temp < Constant.MAX_TEMP)
        {
            curTemp.setText(String.valueOf(temp+1));
            controller.increaseTemperature();
        } else {
        }
    }

    @FXML
    private void decreaseTemp(){
        int temp;
        temp = Integer.parseInt(curTemp.getText());
        if (isDeviceOn && temp > Constant.MIN_TEMP)
        {
            curTemp.setText(String.valueOf(temp-1));
            controller.decreaseTemperature();
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


    public void onPowerStatusChange(boolean statusPower)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!statusPower)
                {
                    powerIcon.setImage( new Image("/resources/powerOffBtn.png"));
                    statusImg.setImage( new Image("/resources/air_con.jpg"));
                    status.setText("Off");
                    isDeviceOn = false;
                } else {
                    powerIcon.setImage( new Image("/resources/powerOnBtn.png"));
                    statusImg.setImage( new Image("/resources/air_con.gif"));
                    status.setText("On");
                    isDeviceOn = true;
                }
            }
        });
    }


    public void onTemperatureChange(int temperature)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(temperature <= Constant.MAX_TEMP && temperature >= Constant.MIN_TEMP)
                {
                    curTemp.setText(Integer.toString(temperature));
                }
            }
        });
    }
}




