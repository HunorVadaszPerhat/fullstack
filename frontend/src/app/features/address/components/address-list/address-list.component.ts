import { Component } from '@angular/core';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { AddressService } from '../../services/address.service';
import { Address } from '../../models/address.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-address-list',
  imports: [TableComponent],
  templateUrl: './address-list.component.html',
  styleUrl: './address-list.component.css'
})
export class AddressListComponent {
  columnHeadNameArray : string[] = ["Address Line 1", "Address Line 2", "City", "State Province", "Postal Code", "Spatial Location", "Modified Date"];
  objectFieldNameArray : string[] = ["addressLine1", "addressLine2", "city", "stateProvinceId", "postalCode", "spatialLocation", "modifiedDate"];
  dataSource: Address[] = [];

constructor(private addressService: AddressService, private router: Router) {}

  ngOnInit(): void {
    this.loadAddresses();
  }

  loadAddresses(): void {
    this.addressService.getAll().subscribe({
      next: (data) => {
        this.dataSource = data;
      },
      error: (err) => console.error('Failed to load addresses', err)
    });
  }

  onEdit(address: Address): void {
    this.router.navigate(['/addresses', address.addressId, 'edit']);
  }

  onDelete(address: Address): void {
    const confirmed = confirm(`Are you sure you want to delete address: ${address.addressLine1}?`);
    if (confirmed) {
      this.addressService.delete(address.addressId).subscribe({
        next: () => {
          console.log('Address deleted');
          this.loadAddresses(); // refresh list
        },
        error: (err) => console.error('Error deleting address', err)
      });
    }
  }

  onCreate(): void {
    this.router.navigate(['/addresses/new']);
  }


}
