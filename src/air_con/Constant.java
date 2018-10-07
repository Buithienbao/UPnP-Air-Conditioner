package air_con;

public class Constant {

    //device
    public static final String DEVICE_NAME = "AirconditionalSystem";
    public static final String MANUFACTURER_DETAILS = "2018";
    public static final String MODEL_DETAILS = "TOSHIBA2018";
    public static final String MODEL_DESCRIPTION = "Air-Conditional System";
    public static final String MODEL_NUMBER = "21111996";

    //switchpower
    public static final String SWITCH_POWER = "SwitchPower";
    public static final String STATUS = "Status";
    public static final boolean POWER_STATUS_DEFAULT = true;
    public static final String GET_STATUS = "GetStatus";
    public static final String TARGET = "Target";
    public static final String SET_TARGET = "SetTarget";
    public static final String GET_TARGET = "GetTarget";
    public static final String NEW_TARGET_VALUE = "NewTargetValue";
    public static final String RET_TARGET_VALUe = "RetTargetValue";
    public static final String RESULT_STATUS = "ResultStatus";
    public static final String TURN_ON = "TurnOn";


    //temprature control service
    public static final String TEMP_CONTROL = "TempControl";
    public static final String TEMPERATURE = "Temperature";
    public static final String GET_TEMPERATURE = "GetTemperature";
    public static final String SET_TEMPERATURE = "SetTemperature";
    public static final String INCREASE_TEMPERATURE = "IncreaseTemperature";
    public static final String DECREASE_TEMPERATURE = "DecreaseTemperature";

    public static final int MIN_TEMP = 16;
    public static final int MAX_TEMP = 31;


    //Fan control service
    public static final String FAN_CONTROL = "FanControl";
    public static final String FAN_STRENGTH = "FanStrength";
    public static final String GET_FAN_STRENGTH = "GetFanStrength";
    public static final String SET_FAN_STRENGTH = "SetFanStrength";
    public static final String INCREASE_FAN = "IncreaseFan";
    public static final String DECREASE_FAN = "DecreaseFan";
    public static final String CHANGE_FAN_DIRECTION = "ChangeFanDirection";
    public static final String UP_FAN_DIRECTION = "UpFanDirection";
    public static final String DOWN_FAN_DIRECTION = "DownFanDirection";

    public static final int DEFAULT_FAN_STRENGTH = 5;
    public static final int MAX_FAN_STRENGTH = 5;
    public static final int MIN_FAN_STRENGTH = 0;
    public static final int FAN_STRENGTH_CHANGE = 1;
    public static final int DEFAULT_FAN_DIRECTION = 3;
    public static final int MAX_FAN_DIRECTION = 5;
    public static final int MIN_FAN_DIRECTION = 1;
    public static final int FAN_DIRECTION_CHANGE = 1;



    public static final String AC_ICON = "/resources/toshiba.jpg";


}
