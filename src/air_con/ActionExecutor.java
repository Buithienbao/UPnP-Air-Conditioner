package air_con;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;

import java.rmi.ServerError;

public class ActionExecutor {

    private Controller controller;

    public ActionExecutor(Controller controller)
    {
        this.controller = controller;
    }


    public void setPowerStatus(UpnpService upnpService, Service service, boolean value)
    {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constant.SET_TARGET));
        getTargetInvocation.setInput(Constant.TURN_ON, value);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {
                    @Override
                    public void success(ActionInvocation actionInvocation) {
                        assert actionInvocation.getOutput().length == 0;
                        System.out.println("Call function successfull!");
                    }

                    @Override
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {
                        System.err.println(s);
                    }
                }
        );
    }


    public void setTemperature(UpnpService upnpService, Service service, int temp)
    {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constant.SET_TARGET));
        getTargetInvocation.setInput(Constant.NEW_TARGET_VALUE, temp);
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {
                    @Override
                    public void success(ActionInvocation actionInvocation) {
                        assert actionInvocation.getOutput().length == 0;
                        System.out.println("Call function set temperature successfully!");
                    }

                    @Override
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {
                        System.err.println(s);
                    }
                }
        );
    }


    public void increaseTemperature(UpnpService upnpService, Service service)
    {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constant.INCREASE_TEMPERATURE));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {
                    @Override
                    public void success(ActionInvocation actionInvocation) {
                        assert actionInvocation.getOutput().length == 0;
                        System.out.println("Call function set temperature successfully!");
                    }

                    @Override
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {
                        System.err.println(s);
                    }
                }
        );
    }


    public void decreaseTemperature(UpnpService upnpService, Service service)
    {
        ActionInvocation getTargetInvocation = new ActionInvocation(service.getAction(Constant.DECREASE_TEMPERATURE));
        upnpService.getControlPoint().execute(
                new ActionCallback(getTargetInvocation) {
                    @Override
                    public void success(ActionInvocation actionInvocation) {
                        assert actionInvocation.getOutput().length == 0;
                        System.out.println("Call function set temperature successfully!");
                    }

                    @Override
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String s) {
                        System.err.println(s);
                    }
                }
        );
    }


}
