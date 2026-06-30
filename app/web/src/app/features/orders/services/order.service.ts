import { Injectable } from '@angular/core';
import { BehaviorSubject, concat, delay, of } from 'rxjs';
import { Order } from '../models/order.model';
import { OrdersStatus } from '../../../types';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private ordersSubject = new BehaviorSubject<Order[]>([]);
  orders$ = this.ordersSubject.asObservable();

  private processOrder(id: string) {
    concat(
      of<OrdersStatus>('PROCESSING').pipe(delay(2000)),
      of<OrdersStatus>('PAID').pipe(delay(2000)),
      of<OrdersStatus>('COMPLETED').pipe(delay(2000)),
    ).subscribe((status) => {
      this.updateStatus(id, status);
    });
  }

  create(order: Omit<Order, 'id' | 'status' | 'createdAt'>) {
    const newOrder: Order = {
      ...order,
      id: crypto.randomUUID(),
      status: 'CREATED',
      createdAt: Date.now(),
    };

    this.ordersSubject.next([...this.ordersSubject.value, newOrder]);

    this.processOrder(newOrder.id);
  }

  updateStatus(id: string, status: OrdersStatus) {
    const orders = this.ordersSubject.value.map((order) => {
      if (order.id === id) {
        return {
          ...order,
          status,
        };
      }
      return order;
    });

    this.ordersSubject.next(orders);
  }
}
