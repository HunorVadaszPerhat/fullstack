import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressService } from '../services/address.service';
import { Address } from '../models/address.model';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-address-list',
  standalone: true,
  templateUrl: './address-list.component.html',
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
  ]
})
export class AddressListComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['addressId', 'addressLine1', 'city', 'postalCode', 'stateProvinceId'];
  dataSource = new MatTableDataSource<Address>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private addressService: AddressService) {}

  ngOnInit(): void {
    this.addressService.getAll().subscribe({
      next: (data) => {
        this.dataSource.data = data;
      },
      error: (err) => console.error('Failed to load addresses', err)
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }
}
