import { Component } from '@angular/core';
import { CreateOrder } from '../create-order/create-order';
import { OrderList } from '../order-list/order-list';

@Component({
  standalone: true,
  selector: 'app-orders-dashboard',
  imports: [CreateOrder, OrderList],
  templateUrl: './orders-dashboard.html',
  styleUrl: './orders-dashboard.css',
})
export class OrdersDashboard {}
