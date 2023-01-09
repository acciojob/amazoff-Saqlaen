package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public void addOrder( Order order ){
        this.orderRepository.add( order );
    }

    public void addDelivery( String  partnerid ){
        this.orderRepository.addDeliveryPartner( partnerid );
    }

    public void addP( String order,  String partnerId ){
        this.orderRepository.addPair(order, partnerId );
    }

    public Order getOrd( String orderId ){
        return this.orderRepository.getOrder(orderId);
    }

    public DeliveryPartner getDelivPart( String id ){
        return this.orderRepository.getPartner(id);
    }

    public int getNoOfOrderfrmPartner( String id ){
        return this.orderRepository.getNoOfOrderFromAPartner(id);
    }

    public List getListOfOrder( String id ){
        return this.orderRepository.getListOfOrderFrmPartner(id);
    }

    public List getAllOrder( ){
        return this.orderRepository.getAllOrderInSystem();
    }

    public int getUnassignedOrders(){
        return this.orderRepository.getCountOfOrdersWhichAreUnassigned();
    }

    public int getUndeliverd( String time, String partner ){
        return this.orderRepository.getCountOfOrderUndeliveried(null, null);
    }

    public String getLastDelivryTime( String id ){
        return this.orderRepository.getLastDelivTimeByPartnerId(id);
    }

    public void deletePartner( String id ){
        this.orderRepository.deleteAPartnerAndUnassignOrders(id);
        return;
    }

    public void deleteOrder( String id ){
        this.orderRepository.deleteOrderCorrespondingPartnerUnassigned(id);
        return;
    }




}
