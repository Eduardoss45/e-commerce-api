import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { OrderService } from '../../services/order.service';

@Component({
  standalone: true,
  selector: 'app-create-order',
  imports: [ReactiveFormsModule],
  templateUrl: './create-order.html',
  styleUrl: './create-order.css',
})
export class CreateOrder {
  private fb = inject(FormBuilder);
  private orderService = inject(OrderService);

  form: FormGroup = this.fb.group({
    product: [''],
    quantity: [1],
  });

  submit() {
    this.orderService.create(this.form.value as any);
    this.form.reset({ quantity: 1 });
  }
}
