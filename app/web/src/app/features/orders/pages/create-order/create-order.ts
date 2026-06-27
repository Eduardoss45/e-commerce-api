import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OrderService } from '../../services/order.service';

@Component({
  standalone: true,
  selector: 'app-create-order',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-order.html',
  styleUrl: './create-order.css',
})
export class CreateOrder {
  private fb = inject(FormBuilder);
  private orderService = inject(OrderService);
  submitted = false;

  form: FormGroup = this.fb.group({
    product: ['', [Validators.required, Validators.minLength(3)]],
    quantity: [1, [Validators.required, Validators.min(1), Validators.max(100)]],
  });

  get product() {
    return this.form.get('product')!;
  }

  get quantity() {
    return this.form.get('quantity')!;
  }

  submit() {
    this.submitted = true;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.orderService.create(this.form.value as any);
    this.form.reset({ quantity: 1 });
    this.submitted = false;
  }
}
