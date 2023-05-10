import { Component, OnInit } from '@angular/core';
import { Client } from '../models/client';
import { ClientService } from '../services/client.service';
import { ClientValidation } from '../models/clientValidation';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit{

  clients: Client[] = [];

  validationResult:ClientValidation | undefined;
  isEmailOccupied: boolean = false;
  isPassportNumberOccupied:boolean = false;
  isNotEnoughParameters:boolean = false;

  constructor(private clientService: ClientService) {}

  ngOnInit(): void{
    this.getClients();
  }

  getClients():void {
    this.clientService.getClients()
      .subscribe(clients => this.clients = clients);
  }

  async add(fullName: string, email:string, passportNumber:number):Promise<void>{
    this.isEmailOccupied = false;
    this.isPassportNumberOccupied = false;
    this.isNotEnoughParameters = false;

    fullName = fullName.trim();
    email = email.trim();
    if(!fullName || !email || !passportNumber) {
      this.isNotEnoughParameters = true;
      return;
    }

    this.validationResult = await this.clientService.validateClient(email,passportNumber,0);

    if(this.validationResult.emailOccupied){
      this.isEmailOccupied = true;
      if(this.validationResult.passportNumberOccupied)
        this.isPassportNumberOccupied = true;
      return;
    }
    if(this.validationResult.passportNumberOccupied){
      this.isPassportNumberOccupied = true;
      if(this.validationResult.emailOccupied)
        this.isEmailOccupied = true;
      return;
    }

    this.clientService.addClient({fullName, email, passportNumber} as Client)
      .subscribe(client => {
        this.clients.push(client);
      });
    
  }

  delete(client: Client):void{
    this.clients = this.clients.filter(c => c !== client);
    this.clientService.deleteClient(client.id).subscribe();
  }

}
