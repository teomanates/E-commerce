import com.turkcell.ecommerce.entity.Order;

public class OrderServiceImpl {

    private Map<Integer, Order> orders = new HashMap<>();
    private int orderIdCounter = 1;
    private int orderItemIdCounter = 1;

    public Order getOrderById(int orderId) {
        return orders.get(orderId);
    }
    public Order createOrder(List<OrderItem> cartItems) {
        // Check if cart is empty
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot create an order.");
        }
        // Check stock and calculate total price
        double totalPrice = 0.0;
        for (OrderItem item : cartItems) {
            Product product = item.product;
            if (product.stock < item.quantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.name);
            }
            totalPrice += item.quantity * product.price;
        }
        // Update stock
        for (OrderItem item : cartItems) {
            Product product = item.product;
            product.stock -= item.quantity;
        }
        // Create order
        Order order = new Order(
                orderIdCounter++,
                UUID.randomUUID().toString(),
                totalPrice,
                new ArrayList<>(cartItems),
                new OrderStatus(1, "Hazırlanıyor")
        );
        orders.put(order.orderId, order);
        return order;
    }
    public void updateOrderStatus(int orderId, String newStatus) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        switch (newStatus) {
            case "Kargoda":
                if (!order.status.description.equals("Hazırlanıyor")) {
                    throw new RuntimeException("Order status can only be updated to 'Kargoda' from 'Hazırlanıyor'.");
                }
                order.status = new OrderStatus(order.status.orderStatusId + 1, newStatus);
                break;
            case "Teslim Edildi":
                if (!order.status.description.equals("Kargoda")) {
                    throw new RuntimeException("Order status can only be updated to 'Teslim Edildi' from 'Kargoda'.");
                }
                order.status = new OrderStatus(order.status.orderStatusId + 1, newStatus);
                break;
            default:
                throw new IllegalArgumentException("Invalid status");
        }
        order.updatedAt = LocalDateTime.now();
    }

    public List<Order> listUserOrders() {
        return orders.values().stream()
                .sorted(Comparator.comparing(o -> o.createdAt))
                .collect(Collectors.toList());
    }
}