import { inject, Injectable } from '@angular/core';
import { OrderService } from './order.service';
import { OrderFlowService } from './order-flow.service';
import { CreateOrderRequest } from '../models/create-order-request.model';
import { toOrderState } from '../mappers/order.mapper';

@Injectable({
  providedIn: 'root',
})
export class OrderFacade {
  private orderService = inject(OrderService);
  private flowService = inject(OrderFlowService);

  orders$ = this.orderService.orders$;

  create(order: CreateOrderRequest) {
    const id = this.orderService.create(toOrderState(order));

    this.flowService.process().subscribe((status) => {
      this.orderService.updateStatus(id, status);
    });
  }
}
