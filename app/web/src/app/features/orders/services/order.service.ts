import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Order } from '../models/order.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private ordersSubject = new BehaviorSubject<Order[]>([]);
  orders$ = this.ordersSubject.asObservable();

  create(order: Order) {
    const newOrder: Order = {
      ...order,
      id: crypto.randomUUID(),
      status: 'CREATED',
      createdAt: Date.now(),
    };

    this.ordersSubject.next([...this.ordersSubject.value, newOrder]);
  }

  updateStatus(id: string, status: Order['status']) {
    const updated = this.ordersSubject.value.map((order) =>
      order.id === id ? { ...order, status } : order,
    );

    this.ordersSubject.next(updated);
  }
}
