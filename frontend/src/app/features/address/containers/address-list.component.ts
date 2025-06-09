import { Component, OnInit } from '@angular/core';
import { AddressService } from '../services/address.service';
import { Address } from '../models/address.model';
import { CommonModule } from '@angular/common';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-address-list',
  imports: [CommonModule, MatListModule],
  template: `
    <mat-list *ngIf="addresses?.length; else noData">
      <mat-list-item *ngFor="let address of addresses">
        {{ address.addressLine1 }}, {{ address.city }}
      </mat-list-item>
    </mat-list>
    <ng-template #noData>
      <p>No addresses found.</p>
    </ng-template>
  `
})
export class AddressListComponent implements OnInit {
  addresses: Address[] = [];

  constructor(private addressService: AddressService) {}

  ngOnInit() {
    this.addressService.getAll().subscribe({
      next: (data) => this.addresses = data,
      error: (err) => console.error('Failed to load addresses', err)
    });
  }
}
