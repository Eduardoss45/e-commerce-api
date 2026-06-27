import { Routes } from '@angular/router';
import { AppShell } from './core/layouts/app-shell/app-shell';
import { CreateOrder } from './features/orders/pages/create-order/create-order';
import { OrderList } from './features/orders/pages/order-list/order-list';

export const routes: Routes = [
  {
    path: '',
    component: AppShell,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/orders/pages/create-order/create-order').then((m) => m.CreateOrder),
      },
      {
        path: 'orders/new',
        component: CreateOrder,
      },
      {
        path: 'orders',
        component: OrderList,
      },
    ],
  },
];
