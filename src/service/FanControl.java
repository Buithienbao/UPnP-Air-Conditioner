package service;

import air_con.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constant.FAN_CONTROL),
        serviceType = @UpnpServiceType(value = Constant.FAN_CONTROL)
)
public class FanControl {

    private final PropertyChangeSupport propertyChangeSupport;


    public FanControl()
    {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }


    public PropertyChangeSupport getPropertyChangeSupport()
    {
        return this.propertyChangeSupport;
    }


    @UpnpStateVariable(
            defaultValue = "0",
            allowedValueMaximum = Constant.MAX_FAN,
            allowedValueMinimum = Constant.MIN_FAN
    ) private int fanMode;


    @UpnpAction
    public void changeFanMode()
    {
        fanMode = (fanMode + 1)%3;
        getPropertyChangeSupport().firePropertyChange(Constant.FAN_MODE, null, null);
    }

    @UpnpAction
    public void setFanMode(@UpnpInputArgument(name = Constant.FAN_MODE)int fanMode)
    {
        if(fanMode <= Constant.MAX_FAN && fanMode >= Constant.MIN_FAN)
        {
            this.fanMode = fanMode;
            getPropertyChangeSupport().firePropertyChange(Constant.FAN_MODE, null, null);
        }
    }
}
