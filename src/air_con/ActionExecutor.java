package air_con;


import org.fourthline.cling.UpnpService;
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
}
