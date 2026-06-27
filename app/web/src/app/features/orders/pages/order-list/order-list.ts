import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';

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
}
