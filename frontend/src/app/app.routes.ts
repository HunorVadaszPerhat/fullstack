// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'addresses',
    loadChildren: () =>
      import('./features/address/address.module').then(m => m.AddressModule),
  },
  {
    path: 'address-types',
    loadChildren: () =>
      import('./features/addresstype/addresstype.module').then(m => m.AddressTypeModule),
  },
  { path: '', redirectTo: 'addresses', pathMatch: 'full' },
  { path: '**', redirectTo: 'addresses' },
];

