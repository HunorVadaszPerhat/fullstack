import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AddressTypeService } from '../../services/addresstype.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Validators } from '@angular/forms';
import { AddressType } from '../../models/addresstype.model';

@Component({
  selector: 'app-addresstype-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './addresstype-form.component.html',
  styleUrl: './addresstype-form.component.css'
})
export class AddresstypeFormComponent implements OnInit {
  addressForm!: FormGroup;
  isEditMode = false;
  addressTypeId!: number;

  addressTypes = [
    { id: 1, name: 'California' },
    { id: 2, name: 'Texas' },
    { id: 3, name: 'New York' },
    { id: 4, name: 'Illinois' },
    { id: 5, name: 'Florida' }
  ];

  constructor(
    private fb: FormBuilder,
    private addressTypeService: AddressTypeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!idParam;
    this.addressTypeId = Number(idParam);

    this.initForm();

    if (this.isEditMode) {
      this.addressTypeService.getById(this.addressTypeId).subscribe(addressType => {
        this.addressForm.patchValue(addressType);
      });
    }
  }

  initForm(): void {
    this.addressForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.addressForm.invalid) return;

    const addressTypeData: AddressType = this.addressForm.value;

    if (this.isEditMode) {
      this.addressTypeService.update(this.addressTypeId, addressTypeData).subscribe(() => {
        this.router.navigate(['/address-types']);
      });
    } else {
      this.addressTypeService.create(addressTypeData).subscribe(() => {
        this.router.navigate(['/address-types']);
      });
    }
  }
}
