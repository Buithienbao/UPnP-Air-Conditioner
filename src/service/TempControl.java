package service;

import air_con.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

@UpnpService(
       serviceId = @UpnpServiceId(Constant.TEMP_CONTROL),
        serviceType =  @UpnpServiceType(value = Constant.TEMP_CONTROL, version = 1)
)
public class TempControl {
    private final PropertyChangeSupport propertyChangeSupport;


    public TempControl()
    {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport()
    {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(
            defaultValue = "20",
            allowedValueMinimum = Constant.MIN_TEMP,
            allowedValueMaximum = Constant.MAX_TEMP
    )private int temperature = 20;


    @UpnpAction
    public void increaseTemperature()
    {
        if(temperature + 1 <= Constant.MAX_TEMP)
        {
            temperature += 1;
            getPropertyChangeSupport().firePropertyChange(Constant.TEMPERATURE, null, null);
        }
    }

    @UpnpAction
    public void decreaseTemperature()
    {
        if(temperature - 1 >= Constant.MIN_TEMP)
        {
            temperature -= 1;
            getPropertyChangeSupport().firePropertyChange(Constant.TEMPERATURE, null, null);
        }
    }


    @UpnpAction
    public void setTemperature(@UpnpInputArgument(name = Constant.TEMPERATURE) int temperature)
    {
        if(temperature <= Constant.MAX_TEMP && temperature >= Constant.MIN_TEMP)
        {
            this.temperature = temperature;
            getPropertyChangeSupport().firePropertyChange(Constant.TEMPERATURE, null, null);
        }
    }


}
