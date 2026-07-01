import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Order } from '../models/order.model';
import { OrdersStatus } from '../../../types';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private ordersSubject = new BehaviorSubject<Order[]>([]);

  orders$ = this.ordersSubject.asObservable();

  create(order: Omit<Order, 'id' | 'status' | 'createdAt'>) {
    const newOrder: Order = {
      ...order,
      id: crypto.randomUUID(),
      status: 'CREATED',
      createdAt: Date.now(),
    };

    this.ordersSubject.next([...this.ordersSubject.value, newOrder]);
    return newOrder.id;
  }

  updateStatus(id: string, status: OrdersStatus) {
    const updated = this.ordersSubject.value.map((order) => {
      if (order.id === id) {
        return {
          ...order,
          status,
        };
      }
      return order;
    });

    this.ordersSubject.next(updated);
  }
}
