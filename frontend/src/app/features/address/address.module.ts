// src/app/features/address/address.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { AddressListComponent } from './components/address-list/address-list.component';

import { AddressService } from './services/address.service';
import { AddressFormComponent } from './components/address-form/address-form.component';
import { AddressDetailComponent } from './components/address-detail/address-detail.component';

const routes: Routes = [
  { path: '', component: AddressListComponent },
  { path: 'new', component: AddressFormComponent },
  { path: ':id', component: AddressDetailComponent },
  { path: ':id/edit', component: AddressFormComponent }, 
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    AddressListComponent,
    AddressFormComponent,
    AddressDetailComponent    
  ],
  providers: [AddressService]
})
export class AddressModule {}
