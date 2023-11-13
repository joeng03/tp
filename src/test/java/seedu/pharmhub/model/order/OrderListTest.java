package seedu.pharmhub.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalOrders.CARL_PANADOL_ORDER;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.order.exceptions.DuplicateOrderException;


class OrderListTest {
    private List<Order> list;
    private OrderList orderList;

    private Order order;
    @BeforeEach
    public void init() {
        order = new Order(new OrderNumber("1"), CARL, new HashSet<>(List.of(PANADOL_MEDICINE)),
                    new Status(Status.OrderStatus.PENDING));
        list = new ArrayList<>();
        list.add(order);
        orderList = new OrderList();
        orderList.add(order);
    }

    @Test
    public void setOrders() {
        OrderList newOrderList = new OrderList();
        newOrderList.setOrders(orderList);
        assertEquals(orderList, newOrderList);
    }

    @Test
    public void equals() {
        assertTrue(orderList.equals(orderList));

        assertFalse(orderList.equals(order));
    }

    @Test
    public void add_duplicateOrder_throwsDuplicateOrderException() {

        assertThrows(DuplicateOrderException.class, () -> orderList.add(CARL_PANADOL_ORDER));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        assertTrue(orderList.contains(CARL_PANADOL_ORDER));
    }

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.contains(null));
    }


}
