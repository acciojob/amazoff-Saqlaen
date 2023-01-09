package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    
    HashMap< String , Order> orderMap = new HashMap<>(); // order partner
    HashMap< String, DeliveryPartner> dMap = new HashMap<>(); // delivery partner
    HashMap< String, List< String > >  pair = new HashMap<>(); // partner and order 
    HashMap< String, Boolean> boolMap = new HashMap<>(); // order assigned

    public void add( Order order ){
        System.out.println( order.toString() );
        orderMap.put( order.getId(), order );
    }

    public void addDeliveryPartner( String partnerid ){
        DeliveryPartner partner = new DeliveryPartner( partnerid );
        dMap.put( partner.getId(), partner );
        System.out.println( partner.toString() );
    }

    public void addPair( String orderId , String partnerId ){

        System.out.println( orderId +" -> "+ partnerId);
        boolMap.put( orderId, true );

        if( pair.containsKey( partnerId) ){
            List< String > li = pair.get( partnerId );
            li.add(  orderId  );
            DeliveryPartner pair = dMap.get( partnerId );
            pair.setNumberOfOrders( li.size() );
        }
        else{
            List< String > li = new ArrayList<>();
            li.add( orderId );

            DeliveryPartner pairObj = dMap.get( partnerId );
            pairObj.setNumberOfOrders( li.size() );

            pair.put( partnerId, li ); 
        }

    }

    public Order getOrder( String orderid ){
        return orderMap.get( orderid );
    }

    public DeliveryPartner getPartner( String partnerid ){
        return dMap.get( partnerid );
    }

    public int getNoOfOrderFromAPartner( String partnerid ){
        return dMap.get( partnerid ).getNumberOfOrders();
    }

    public List getListOfOrderFrmPartner( String partnerid ){
        List< String > li = pair.get( partnerid );
        return li;
    }

    public List getAllOrderInSystem( ){
        List< String > all = new ArrayList<>();
        
            for( String ords : orderMap.keySet() ){
                all.add( ords );
            }

        return  all;
    }

    public int getCountOfOrdersWhichAreUnassigned(){
        int count = 0;

        for( String name : orderMap.keySet() ){
            if( boolMap.containsKey( name ) ){
                if( boolMap.get( name ) == true ){
                    count++;
                }
            }
        }
        return orderMap.size() - count;
    }

    public int getCountOfOrderUndeliveried( String time, String partnerid  ){

        String hh  = time.substring(0,2);
        String mm = time.substring(3);
        int till = Integer.parseInt( hh ) * 60 + Integer.parseInt( mm );
        int count =  0;
        if( pair.containsKey(partnerid) ){
            List< String > li = pair.get( partnerid );
            for( String ord  : li ){
                Order order = orderMap.getOrDefault( ord, null);
                int deliTime = order.getDeliveryTime();
                if( deliTime > till ){
                    count++;
                }
            }
        }
        return count;
    }

    public String getLastDelivTimeByPartnerId( String partnerid ){
        
        if( pair.containsKey(partnerid ) ){
            List< String > li = pair.get( partnerid );
            String lastOrder = li.get( li.size() - 1 );
            Order order = orderMap.getOrDefault(lastOrder, null);
            return order.getTimeInStr(); 
        }
        else{
            return "";
        }
        
    }

    public void deleteAPartnerAndUnassignOrders( String partnerid ){ 

        if( pair.containsKey(partnerid) ){
            List< String > li  = pair.get( partnerid );
            for( String ord : li ){
                if( boolMap.containsKey( ord ) ){
                    boolMap.put( ord , false );
                }
            }
            pair.remove( partnerid );
        }
        if( dMap.containsKey(partnerid) ){
            dMap.remove( partnerid );
        }
        return;
    }


    public void deleteOrderCorrespondingPartnerUnassigned( String orderId ){

        for( Map.Entry< String, List<String> > entry : pair.entrySet() ){
            String partnerId = entry.getKey();
            List< String > li = entry.getValue();

            
            for( int i=0; i < li.size(); i++ ){
                String ord = li.get(i);
                if( ord.equals(orderId) ){
                    li.remove( i );
                }
            }

            DeliveryPartner partner = dMap.getOrDefault( partnerId, null );
                if( partner != null ){
                    int curr = partner.getNumberOfOrders();
                    if( curr > 0 ){
                        partner.setNumberOfOrders( curr -= 1 );
                    }
                }
            
            pair.put( partnerId, li );
        }

        if( orderMap.containsKey(orderId) ){
            orderMap.remove( orderId );
            if( boolMap.containsKey(orderId) ){
                boolMap.remove( orderId );
            }
        }

    }





    
}
