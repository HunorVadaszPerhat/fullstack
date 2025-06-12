import { Component } from '@angular/core';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { AddressType } from '../../models/addresstype.model';
import { AddressTypeService } from '../../services/addresstype.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-addresstype-list',
  imports: [TableComponent],
  templateUrl: './addresstype-list.component.html',
  styleUrl: './addresstype-list.component.css'
})
export class AddresstypeListComponent {
  columnHeadNameArray : string[] = ["Name", "Modified Date"];
  objectFieldNameArray : string[] = ["name", "modifiedDate"];
  dataSource: AddressType[] = [];

  constructor(private readonly addressTypeService: AddressTypeService, private readonly router: Router) {}
  
  ngOnInit(): void {
    this.loadAddressTypes();
  }

  loadAddressTypes(): void {
    this.addressTypeService.getAll().subscribe({
      next: (data) => {
        this.dataSource = data;
      },
      error: (err) => console.error('Failed to load address types', err)
    });
  }

  onEdit(addressType: AddressType): void {
      this.router.navigate(['/address-types', addressType.addressTypeId, 'edit']);
    }
  
    onDelete(addressType: AddressType): void {
      const confirmed = confirm(`Are you sure you want to delete address: ${addressType.name}?`);
      if (confirmed) {
        this.addressTypeService.delete(addressType.addressTypeId).subscribe({
          next: () => {
            console.log('Address type deleted');
            this.loadAddressTypes(); // refresh list
          },
          error: (err) => console.error('Error deleting address type', err)
        });
      }
    }
  
    onCreate(): void {
      this.router.navigate(['/address-types/new']);
    }

}
