import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';
import { OrdersStatus } from '../../../../types';

@Component({
  standalone: true,
  selector: 'app-order-list',
  imports: [CommonModule],
  templateUrl: './order-list.html',
  styleUrl: './order-list.css',
})
export class OrderList {
  private orderService = inject(OrderService);
  orders$ = this.orderService.orders$;
  statusColor: Record<OrdersStatus, string> = {
    CREATED: 'bg-gray-400',
    PROCESSING: 'bg-yellow-400',
    PAID: 'bg-blue-400',
    FAILED: 'bg-red-500',
    COMPLETED: 'bg-green-500',
  };
}
