import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

import { Order } from '../models/order.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private ordersSubject = new BehaviorSubject<Order[]>([]);
  orders$ = this.ordersSubject.asObservable();

  create(order: Order) {
    const current = this.ordersSubject.value;

    this.ordersSubject.next([...current, order]);
  }
}
