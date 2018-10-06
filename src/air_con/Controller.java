package air_con;


import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.action.ActionExecutor;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.concurrent.ScheduledFuture;


public class Controller{

    private View view;
    private Device device;
    private UpnpService upnpService;
    private ActionExecutor actionExecutor;
    private ScheduledFuture scheduledFuture;
    private RegistryListener registryListener = new DefaultRegistryListener();


    public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice)
    {
        System.out.println("Group4 remote device detected.");
        if(remoteDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS))
        {
            System.out.println(""+Constant.MODEL_DESCRIPTION+ " is detected");
            device = remoteDevice;
        }
    }
}
