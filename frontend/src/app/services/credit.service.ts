import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { NewCreditData } from '../models/newCreditData';
import { Credit } from '../models/credit';
import { CreditValidation } from '../models/creditValidation';

@Injectable({
  providedIn: 'root'
})
export class CreditService {

  private creditsUrl = `${environment.apiUrl}/api/credits`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http:HttpClient) { }

  createNewCredit(newCreditData: NewCreditData):Observable<Credit>{
    return this.http.post<Credit>(this.creditsUrl, newCreditData, this.httpOptions);
  }

  deleteCredit(id: number): Observable<Credit>{
    const url = `${this.creditsUrl}/${id}`;
    return this.http.delete<Credit>(url, this.httpOptions);
  }

  async validateCredit(creditOfferId:number, amount:number):Promise<CreditValidation>{
    const params = new HttpParams()
      .set('creditOfferId', creditOfferId)
      .set('amount', amount);

    const url = `${this.creditsUrl}/validate`;
    const res: any = await this.http.get<CreditValidation>(url, {params}).toPromise();
    return res;
  }
}
