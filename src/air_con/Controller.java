package air_con;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.UDADeviceTypeHeader;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.transport.Router;
import sun.util.locale.LocaleSyntaxException;

import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


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
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constant.SWITCH_POWER)));
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        setPowerStatus(Constant.POWER_STATUS_DEFAULT);

                    }
                }, 500, TimeUnit.MILLISECONDS);
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
                upnpService.getControlPoint().execute(createPowerSwitchSubscriptionCallBack(getServiceById(device, Constant.SWITCH_POWER)));
                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        setPowerStatus(Constant.POWER_STATUS_DEFAULT);

                    }
                }, 500, TimeUnit.MILLISECONDS);
            }
        }


        public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
            System.out.print("Local device detected\n");
            if (localDevice.getDetails().getModelDetails().getModelName().equals(Constant.MODEL_DETAILS))
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
                    view.onPowerStatusChange(value);
                    System.out.println("New Value: " + value);
                }
            }

            @Override
            protected void eventsMissed(GENASubscription genaSubscription, int i) {
                    System.out.println("Missed events : " + i);
            }
        };
    }


    private Service getServiceById(Device device, String serviceId){
        if(device == null){
            return null;
        }
        return device.findService(new UDAServiceId(serviceId));
    }

    public Controller(View view)
    {
        this.view = view;
        init();
    }


    public void init()
    {
        actionExecutor = new ActionExecutor(this);
        upnpService = new UpnpServiceImpl();
        upnpService.getRegistry().addListener(registryListener);
        try {
            upnpService.getRegistry().addDevice(createDevice());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        UDADeviceTypeHeader header = new UDADeviceTypeHeader(new UDADeviceType(Constant.DEVICE_NAME));
        upnpService.getControlPoint().search(header);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                upnpService.shutdown();
            }
        } );
    }


    public LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {
        DeviceIdentity deviceIdentity = new DeviceIdentity(UDN.uniqueSystemIdentifier(Constant.DEVICE_NAME));

        DeviceType deviceType = new UDADeviceType(Constant.DEVICE_NAME, 1);

        DeviceDetails deviceDetails = new DeviceDetails(Constant.DEVICE_NAME, new ManufacturerDetails(Constant.MANUFACTURER_DETAILS),
                new ModelDetails(Constant.MODEL_DETAILS, Constant.MODEL_DESCRIPTION, Constant.MODEL_NUMBER));

        Icon icon = new Icon("image/jpg", 1,1, 2, getClass().getResource(Constant.AC_ICON));

        LocalService<SwitchPower> switchPowerLocalService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
        switchPowerLocalService.setManager(new DefaultServiceManager<>(switchPowerLocalService, SwitchPower.class));


        return new LocalDevice(deviceIdentity, deviceType, deviceDetails, icon, new LocalService[]{switchPowerLocalService});
    }


    public void setPowerStatus(boolean status)
    {
        Service service = getServiceById(device, Constant.SWITCH_POWER);
        if(service != null)
        {
            actionExecutor.setPowerStatus(upnpService, service, status);
            //TODO next
        }
    }
}
