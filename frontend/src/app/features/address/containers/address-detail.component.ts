// src/app/features/address/containers/address-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AddressService } from '../services/address.service';
import { Address } from '../models/address.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-address-detail',
  imports: [CommonModule],
  template: `
    <div *ngIf="address; else loadingOrError">
      <h2>Address Details</h2>
      <p>ID: {{ address.addressId }}</p>
      <p>Address Line 1: {{ address.addressLine1 }}</p>
      <p>Address Line 2: {{ address.addressLine2 }}</p>
      <p>City: {{ address.city }}</p>
      <p>Postal Code: {{ address.postalCode }}</p>
      <p>State Province ID: {{ address.stateProvinceId }}</p>
    </div>
    <ng-template #loadingOrError>
      <p *ngIf="loading">Loading address...</p>
      <p *ngIf="error">Failed to load address.</p>
    </ng-template>
  `
})
export class AddressDetailComponent implements OnInit {
  address?: Address;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private addressService: AddressService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.addressService.getById(id).subscribe({
      next: (data) => {
        this.address = data;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }
}