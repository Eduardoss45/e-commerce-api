import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CreateOrderRequest } from '../models/create-order-request.model';
import { OrderResponse } from '../models/order-response.model';

@Injectable({
  providedIn: 'root',
})
export class OrderApiService {
  private http = inject(HttpClient);

  create(order: CreateOrderRequest) {
    return this.http.post<OrderResponse>('/api/orders', order);
  }
}
