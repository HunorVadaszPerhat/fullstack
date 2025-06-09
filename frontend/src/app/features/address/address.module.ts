// src/app/features/address/address.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AddressListComponent } from './containers/address-list.component';
import { AddressDetailComponent } from './containers/address-detail.component';
import { AddressFormComponent } from './containers/address-form.component';
import { AddressService } from './services/address.service';

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
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    AddressListComponent,       // ✅ import instead of declare
    AddressFormComponent,
    AddressDetailComponent
  ],
  providers: [AddressService]
})
export class AddressModule {}
