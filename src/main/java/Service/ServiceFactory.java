package Service;

import Service.Custom.Impl.CustomerServiceImpl;
import Service.Custom.Impl.ItemServiceImpl;
import Util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance==null ? instance = new ServiceFactory() : instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case CUSTOMER:return (T) CustomerServiceImpl.getInstance();  // Type casting
            case ITEM:return (T) ItemServiceImpl.getInstance();
        }
        return null;
    }
}
