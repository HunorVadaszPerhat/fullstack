import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AddressService } from '../../services/address.service';
import { Address } from '../../models/address.model';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-address-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './address-form.component.html',
  styleUrl: './address-form.component.css'
})
export class AddressFormComponent implements OnInit {
  addressForm!: FormGroup;
  isEditMode = false;
  addressId!: number;

  stateProvinces = [
    { id: 1, name: 'California' },
    { id: 2, name: 'Texas' },
    { id: 3, name: 'New York' },
    { id: 4, name: 'Illinois' },
    { id: 5, name: 'Florida' }
  ];

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!idParam;
    this.addressId = Number(idParam);

    this.initForm();

    if (this.isEditMode) {
      this.addressService.getById(this.addressId).subscribe(address => {
        this.addressForm.patchValue(address);
      });
    }
  }

  initForm(): void {
    this.addressForm = this.fb.group({
      addressLine1: ['', [Validators.required, Validators.maxLength(100)]],
      addressLine2: ['', [Validators.maxLength(100)]],
      city: ['', [Validators.required, Validators.maxLength(50)]],
      stateProvinceId: ['', Validators.required],
      postalCode: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      spatialLocation: ['']
    });
  }


  onSubmit(): void {
    if (this.addressForm.invalid) return;

    const addressData: Address = this.addressForm.value;

    if (this.isEditMode) {
      this.addressService.update(this.addressId, addressData).subscribe(() => {
        this.router.navigate(['/addresses']);
      });
    } else {
      this.addressService.create(addressData).subscribe(() => {
        this.router.navigate(['/addresses']);
      });
    }
  }
}
