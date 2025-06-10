// src/app/features/address/models/address.model.ts
export interface Address {
  addressId: number;
  addressLine1: string;
  addressLine2?: string | null;
  city: string;
  postalCode: string;
  stateProvinceId: number;
  spatialLocation?: string;
  rowguid?: string;
  modifiedDate?: string;
}

