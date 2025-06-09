// src/app/features/address/address.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatListModule } from '@angular/material/list';

import { AddressListComponent } from './containers/address-list.component';
import { AddressDetailComponent } from './containers/address-detail.component';
import { AddressFormComponent } from './containers/address-form.component';

const routes: Routes = [
  { path: '', component: AddressListComponent },
  { path: 'new', component: AddressFormComponent },
  { path: ':id', component: AddressDetailComponent },
  { path: ':id/edit', component: AddressFormComponent },
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatListModule,
    RouterModule.forChild(routes),
  ]
})
export class AddressModule {}
