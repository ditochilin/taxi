package dao;

import entities.Share;
import entities.TaxiOrder;

import java.util.List;

public interface IShareDao extends IDao<Share> {

    List<Share> findSharesByOrder(TaxiOrder taxiOrder);

}
