import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  products: Product[] = [
    {
      id: 1,
      name: 'Mouse',
      stock: 20,
      price: 99.9,
    },

    {
      id: 2,
      name: 'Keyboard',
      stock: 15,
      price: 189.9,
    },
  ];

  getAll() {
    return this.products;
  }
}
