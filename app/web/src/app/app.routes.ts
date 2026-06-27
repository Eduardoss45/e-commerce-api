import { Routes } from '@angular/router';
import { AppShell } from './core/layouts/app-shell/app-shell';
import { OrdersDashboard } from './features/orders/pages/orders-dashboard/orders-dashboard';

export const routes: Routes = [
  {
    path: '',
    component: AppShell,
    children: [
      {
        path: 'orders',
        component: OrdersDashboard,
      },
      { path: '', redirectTo: 'orders', pathMatch: 'full' },
    ],
  },
];
