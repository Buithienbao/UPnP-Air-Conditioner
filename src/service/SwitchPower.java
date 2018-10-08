package service;


import air_con.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

@UpnpService(
        serviceId = @UpnpServiceId(Constant.SWITCH_POWER),
        serviceType = @UpnpServiceType(value= Constant.SWITCH_POWER, version = 1)
)
public class SwitchPower {

    private final PropertyChangeSupport propertyChangeSupport;

    public SwitchPower(){
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }


    public PropertyChangeSupport getPropertyChangeSupport()
    {
        return propertyChangeSupport;
    }


    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target = false;

    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;

    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = Constant.TURN_ON) boolean newTargetValue)
    {
        boolean targetOldValue = target;
        target = newTargetValue;
        boolean statusOldValue = status;
        status = newTargetValue;
        getPropertyChangeSupport().firePropertyChange(Constant.STATUS, null, null);
    }
}
