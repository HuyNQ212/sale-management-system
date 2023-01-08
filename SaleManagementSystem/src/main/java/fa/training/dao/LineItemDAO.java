package fa.training.dao;

import fa.training.entities.LineItem;

import java.util.List;

public interface LineItemDAO {
    List<LineItem> getAllLineitems();
    Double computeOrderTotal(int orderId);

    List<LineItem> getAllItemsByOrderId(int orderId);

    boolean addLineItem(LineItem item);

    boolean updatePriceFromProductToLineitem();
}
