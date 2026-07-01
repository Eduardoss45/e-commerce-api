import { Injectable } from '@angular/core';
import { concat, delay, Observable, of } from 'rxjs';
import { OrdersStatus } from '../../../types';

@Injectable({
  providedIn: 'root',
})
export class OrderFlowService {
  process(): Observable<OrdersStatus> {
    return concat(
      of('PROCESSING' as OrdersStatus).pipe(delay(2000)),
      of('PAID' as OrdersStatus).pipe(delay(2000)),
      of('COMPLETED' as OrdersStatus).pipe(delay(2000)),
    );
  }
}
