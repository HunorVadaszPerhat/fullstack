// src/app/features/address/containers/address-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AddressService } from '../services/address.service';
import { Address } from '../models/address.model';
import { ReactiveFormsModule } from '@angular/forms';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-address-form',
  imports: [ReactiveFormsModule],
  template: `
    <form [formGroup]="addressForm" (ngSubmit)="onSubmit()">
      <label>
        Address Line 1:
        <input formControlName="addressLine1" />
      </label>
      <label>
        Address Line 2:
        <input formControlName="addressLine2" />
      </label>
      <label>
        City:
        <input formControlName="city" />
      </label>
      <label>
        Postal Code:
        <input formControlName="postalCode" />
      </label>
      <label>
        State Province ID:
        <input type="number" formControlName="stateProvinceId" />
      </label>
      <button type="submit" [disabled]="addressForm.invalid">{{ isEditMode ? 'Update' : 'Create' }}</button>
    </form>
  `
})
export class AddressFormComponent implements OnInit {
    addressForm: FormGroup;

    constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private addressService: AddressService,
  ) {
    this.addressForm = this.fb.group({
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      city: ['', Validators.required],
      postalCode: ['', Validators.required],
      stateProvinceId: [null, Validators.required],
    });
  }

  isEditMode = false;
  addressId?: number;

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.addressId = Number(idParam);
      this.addressService.getById(this.addressId).subscribe(address => {
        this.addressForm.patchValue(address);
      });
    }
  }

  onSubmit() {
    if (this.addressForm.invalid) {
      return;
    }

    const formValue = this.addressForm.value as Address;

    if (this.isEditMode && this.addressId) {
      this.addressService.update(this.addressId, formValue).subscribe(() => {
        this.router.navigate(['/addresses']);
      });
    } else {
      this.addressService.create(formValue).subscribe(() => {
        this.router.navigate(['/addresses']);
      });
    }
  }
}
