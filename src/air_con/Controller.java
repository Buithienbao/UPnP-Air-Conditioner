package air_con;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.action.ActionExecutor;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;


public class Controller {

    private View view;
    private Device device;
    private UpnpService upnpService;
    private ActionExecutor actionExecutor;
    private ScheduledFuture scheduledFuture;
    private RegistryListener registryListener = new DefaultRegistryListener() {

        public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
            System.out.println("Group4 remote device detected.");
            if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS)) {
                System.out.println("" + Constant.MODEL_DESCRIPTION + " is detected");
                device = remoteDevice;
            }
        }

        public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
            if (remoteDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS)) {
                System.out.println("" + Constant.MODEL_DETAILS + " is removed");
                device = null;
            }
        }

        public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
            System.out.println("Local device detected");
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS)) {
                System.out.println("" + Constant.MODEL_DETAILS + " is detected\n");
                device = localDevice;
            }
        }


        public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
            System.out.print("Local device detected\n");
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS)
            {
                System.out.println("" + Constant.MODEL_DETAILS + " is removed");
                device = null;
            }
        }
    };

    private SubscriptionCallback createPowerSwitchSubscriptionCallBack(Service service)
    {
        return  new SubscriptionCallback(service, Integer.MAX_VALUE) {
            @Override
            protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {

            }

            @Override
            protected void established(GENASubscription genaSubscription) {
                System.out.println("Power switch subscription created");
            }

            @Override
            protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {

            }

            @Override
            protected void eventReceived(GENASubscription genaSubscription) {
                System.out.println("Event: " + genaSubscription.getCurrentSequence().getValue());
                Map<String, StateVariableValue> values = genaSubscription.getCurrentValues();
                for(String key: values.keySet())
                {
                    System.out.println(""+key + " changed.");
                }
                if(values.containsKey(Constant.STATUS))
                {
                    boolean value = (boolean) values.get(Constant.STATUS).getValue();

                }
            }

            @Override
            protected void eventsMissed(GENASubscription genaSubscription, int i) {

            }
        }
    }


}
