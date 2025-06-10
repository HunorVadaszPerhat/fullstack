// src/app/features/address/services/address.service.ts
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../models/address.model';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../../../environment/environment';

@Injectable({ providedIn: 'root' })
export class AddressService {
  private readonly api = `${environment.apiBaseUrl}/address`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Address[]> {
    return this.http.get<Address[]>(this.api).pipe(
      tap(data => console.log('Received addresses:', data)),
      catchError(err => {
        console.error('Error loading addresses', err);
        return throwError(() => err);
      })
    );
  }

  getById(id: number): Observable<Address> {
    return this.http.get<Address>(`${this.api}/${id}`).pipe(
      tap(data => console.log(`Received address id=${id}:`, data)),
      catchError(err => {
        console.error(`Error loading address id=${id}`, err);
        return throwError(() => err);
      })
    );
  }

  create(address: Address): Observable<Address> {
    return this.http.post<Address>(this.api, address).pipe(
      tap(data => console.log('Created address:', data)),
      catchError(err => {
        console.error('Error creating address', err);
        return throwError(() => err);
      })
    );
  }

  update(id: number, address: Address): Observable<Address> {
    return this.http.put<Address>(`${this.api}/${id}`, address).pipe(
      tap(data => console.log(`Updated address id=${id}:`, data)),
      catchError(err => {
        console.error(`Error updating address id=${id}`, err);
        return throwError(() => err);
      })
    );
  }

  getPaginated(page = 0, size = 10, sort: string[] = ['addressId', 'asc']): Observable<{ content: Address[], totalPages: number }> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort.join(','));

    return this.http.get<{ content: Address[], totalPages: number }>(`${this.api}/paginated`, { params }).pipe(
      tap(data => console.log(`Received paginated addresses: page=${page}, size=${size}`, data)),
      catchError(err => {
        console.error('Error loading paginated addresses', err);
        return throwError(() => err);
      })
    );
  }

    delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`).pipe(
      tap(() => console.log(`Deleted address id=${id}`)),
      catchError(err => {
        console.error(`Error deleting address id=${id}`, err);
        return throwError(() => err);
      })
    );
  }
}