import { Injectable } from '@angular/core';
import { environment } from '../../../../environment/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AddressType } from '../models/addresstype.model';


@Injectable({ providedIn: 'root' })
export class AddressTypeService {
    private readonly api = `${environment.apiBaseUrl}/address-type`;

    constructor(private readonly http: HttpClient) {}

  getAll(): Observable<AddressType[]> {
    return this.http.get<AddressType[]>(this.api).pipe(
      tap(data => console.log('Received addresse types:', data)),
      catchError(err => {
        console.error('Error loading addresse types', err);
        return throwError(() => err);
      })
    );
  }

    getById(id: number): Observable<AddressType> {
      return this.http.get<AddressType>(`${this.api}/${id}`).pipe(
        tap(data => console.log(`Received address type id=${id}:`, data)),
        catchError(err => {
          console.error(`Error loading address type id=${id}`, err);
          return throwError(() => err);
        })
      );
    }

    getPaginated(page = 0, size = 10, sort: string[] = ['addressId', 'asc']): Observable<{ content: AddressType[], totalPages: number }> {
        let params = new HttpParams()
            .set('page', page)
            .set('size', size)
            .set('sort', sort.join(','));

        return this.http.get<{ content: AddressType[], totalPages: number }>(`${this.api}/paginated`, { params }).pipe(
            tap(data => console.log(`Received paginated address types: page=${page}, size=${size}`, data)),
            catchError(err => {
                console.error('Error loading paginated address types', err);
                return throwError(() => err);
            })
        );
    }

    create(addressType: AddressType): Observable<AddressType> {
        return this.http.post<AddressType>(this.api, addressType).pipe(
            tap(data => console.log('Created address type:', data)),
            catchError(err => {
                console.error('Error creating address type', err);
                return throwError(() => err);
            })
        );
    }

    update(id: number, addressType: AddressType): Observable<AddressType> {
        return this.http.put<AddressType>(`${this.api}/${id}`, addressType).pipe(
            tap(data => console.log(`Updated address type id=${id}:`, data)),
            catchError(err => {
                console.error(`Error updating address type id=${id}`, err);
                return throwError(() => err);
            })
        );
    }   

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.api}/${id}`).pipe(
        tap(() => console.log(`Deleted address type id=${id}`)),
            catchError(err => {
                console.error(`Error deleting address type id=${id}`, err);
                return throwError(() => err);
            })
        );
    }
}