import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Client } from '../models/client';
import { Observable } from 'rxjs';
import { ClientValidation } from '../models/clientValidation';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private clientsUrl = `${environment.apiUrl}/api/clients`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http:HttpClient) { }

  getClients(): Observable<Client[]>{
    return this.http.get<Client[]>(this.clientsUrl);
  }

  getClient(id: number):Observable<Client> {
    const url = `${this.clientsUrl}/${id}`;
    return this.http.get<Client>(url);
  }

  updateClient(client:Client): Observable<any>{
    return this.http.put(this.clientsUrl, client, this.httpOptions);
  }

  addClient(client:Client): Observable<Client> {
    return this.http.post<Client>(this.clientsUrl, client, this.httpOptions);
  }

  deleteClient(id: number): Observable<Client> {
    const url = `${this.clientsUrl}/${id}`;
    return this.http.delete<Client>(url, this.httpOptions);
  }

  async validateClient(email:string, passportNumber:number, id:number):Promise<ClientValidation>{
    const params = new HttpParams()
      .set('email', email)
      .set('passportNumber', passportNumber)
      .set('id', id);

    const url = `${this.clientsUrl}/validate`;
    const res: any = await this.http.get<ClientValidation>(url, {params}).toPromise();
    return res;
  }
}
