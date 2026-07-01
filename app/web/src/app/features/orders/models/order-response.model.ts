import { OrdersStatus } from '../../../types';

export interface OrderResponse {
  id: string;
  status: OrdersStatus;
  product: string;
  quantity: number;
}
