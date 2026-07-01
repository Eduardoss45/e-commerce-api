import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { OrderFacade } from '../../services/order.facade';

@Component({
  standalone: true,
  selector: 'app-create-order',
  imports: [ReactiveFormsModule],
  templateUrl: './create-order.html',
  styleUrl: './create-order.css',
})
export class CreateOrder {
  private fb = inject(FormBuilder);
  private orderFacade = inject(OrderFacade);

  submitted = false;

  products = [
    {
      id: 1,
      name: 'Mouse',
      price: 99.9,
    },
    {
      id: 2,
      name: 'Keyboard',
      price: 189.9,
    },
    {
      id: 3,
      name: 'Monitor',
      price: 1299.9,
    },
    {
      id: 4,
      name: 'Headset',
      price: 249.9,
    },
    {
      id: 5,
      name: 'USB Cable',
      price: 19.9,
    },
  ];

  paymentProfiles = [
    {
      id: 'SUCCESS',
      label: 'Pagamento aprovado',
      cardNumber: '1111111111111111',
      cvv: '200',
    },
    {
      id: 'FAIL',
      label: 'Pagamento recusado',
      cardNumber: '2222222222222222',
      cvv: '100',
    },
  ];

  form: FormGroup = this.fb.group({
    productId: [undefined, Validators.required],

    quantity: [1, [Validators.required, Validators.min(1), Validators.max(100)]],

    paymentProfile: ['SUCCESS', Validators.required],
  });

  submit() {
    this.submitted = true;

    if (this.form.invalid) return;

    const profile = this.paymentProfiles.find((p) => p.id === this.form.value.paymentProfile)!;

    const payload = {
      items: [
        {
          productId: this.form.value.productId,
          quantity: this.form.value.quantity,
        },
      ],
      payment: {
        cardNumber: profile.cardNumber,
        cvv: profile.cvv,
      },
    };

    console.log(payload);

    this.orderFacade.create(payload);

    this.form.reset({
      quantity: 1,
      paymentProfile: 'SUCCESS',
    });

    this.submitted = false;
  }

  get productId() {
    return this.form.get('productId')!;
  }

  get quantity() {
    return this.form.get('quantity')!;
  }

  get cardNumber() {
    return this.form.get('cardNumber')!;
  }

  get cvv() {
    return this.form.get('cvv')!;
  }
}
