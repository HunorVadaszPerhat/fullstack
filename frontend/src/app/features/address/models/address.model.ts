// src/app/features/address/models/address.model.ts
export interface Address {
  addressId: number;
  addressLine1: string;
  addressLine2?: string;
  city: string;
  postalCode: string;
  stateProvinceId: number;
}
