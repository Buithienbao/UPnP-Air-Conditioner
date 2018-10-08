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
    ) private int fan_mode;


    @UpnpAction
    public void changeFanMode()
    {
        fan_mode = (fan_mode + 1)%3;
        getPropertyChangeSupport().firePropertyChange(Constant.FAN_MODE, null, null);
    }
}
