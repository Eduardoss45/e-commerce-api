import { CreateOrderRequest } from '../models/create-order-request.model';
import { Order } from '../models/order.model';

export function toOrderState(
  request: CreateOrderRequest,
): Omit<Order, 'id' | 'status' | 'createdAt'> {
  return {
    product: String(request.items[0].productId),
    quantity: request.items[0].quantity,
  };
}
