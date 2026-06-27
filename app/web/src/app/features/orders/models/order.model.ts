import { OrdersStatus } from '../../../types';

export interface Order {
  id: string;
  product: string;
  quantity: number;
  status: OrdersStatus;
  createdAt: number;
}
