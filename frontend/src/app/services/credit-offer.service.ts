import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { CreditOffer } from '../models/creditOffer';

@Injectable({
  providedIn: 'root'
})
export class CreditOfferService {

  private creditOffersUrl = `${environment.apiUrl}/api/credit-offers`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http:HttpClient) { }

  getCreditOffers():Observable<CreditOffer[]>{
    return this.http.get<CreditOffer[]>(this.creditOffersUrl);
  }

  getCreditOffer(id: number):Observable<CreditOffer>{
    const url = `${this.creditOffersUrl}/${id}`;
    return this.http.get<CreditOffer>(url);
  }

  updateCreditOffer(creditOffer: CreditOffer): Observable<any>{
    return this.http.put(this.creditOffersUrl, creditOffer, this.httpOptions);
  }

  addCreditOffer(creditOffer: CreditOffer): Observable<CreditOffer>{
    return this.http.post<CreditOffer>(this.creditOffersUrl, creditOffer, this.httpOptions);
  }

  deleteCreditOffer(id: number){
    const url = `${this.creditOffersUrl}/${id}`;
    return this.http.delete<CreditOffer>(url, this.httpOptions);
  }
}
