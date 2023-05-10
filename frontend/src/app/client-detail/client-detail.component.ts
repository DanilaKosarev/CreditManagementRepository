import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Client } from '../models/client';
import { Credit } from '../models/credit';
import { Payment } from '../models/payment';
import { ClientService } from '../services/client.service';
import { CreditOffer } from '../models/creditOffer';
import { CreditOfferService } from '../services/credit-offer.service';
import { CreditService } from '../services/credit.service';
import { NewCreditData } from '../models/newCreditData';
import { CreditValidation } from '../models/creditValidation';
import { ClientValidation } from '../models/clientValidation';

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {
  
  client: Client | undefined;
  credits: Credit[] = [];
  creditOffers: CreditOffer[] = [];

  selectedCreditOffer: CreditOffer | undefined;

  selectedCredit?:Credit;

  isBelowZero: boolean = false;
  isNotEnoughCreditParameters: boolean = false;
  isExceedLimit: boolean = false;

  creditValidation: CreditValidation | undefined;

  validationResult:ClientValidation | undefined;
  isEmailOccupied: boolean = false;
  isPassportNumberOccupied:boolean = false;
  isNotEnoughParameters:boolean = false;

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService,
    private creditOfferService: CreditOfferService,
    private creditService: CreditService,
    private location: Location
  ) {}

  ngOnInit(): void{
    this.getClient();
    this.getCreditOffers();
  }

  onSelect(credit:Credit):void{
    if(this.selectedCredit === credit)
      this.selectedCredit = undefined;
    else
      this.selectedCredit = credit;
  }

  getCreditOffers():void {
    this.creditOfferService.getCreditOffers()
      .subscribe(creditOffers => this.creditOffers = creditOffers);
  }

  getClient(): void{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.clientService.getClient(id)
      .subscribe(client => {this.client = client, this.credits = client.credits});
  }

  async save():Promise<void>{
    this.isEmailOccupied = false;
    this.isPassportNumberOccupied = false;
    this.isNotEnoughParameters = false;

    if(this.client){

      if(!this.client.fullName.trim() || !this.client.email.trim() || !this.client.passportNumber) {
        this.isNotEnoughParameters = true;
        return;
      }

      this.validationResult = await this.clientService.validateClient(this.client.email.trim(),this.client.passportNumber, this.client.id);

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

      this.clientService.updateClient(this.client)
        .subscribe(() => this.goBack());
    }
  }

  async addCredit(pureAmount:number, monthQuantity:number):Promise<void>{
    this.isBelowZero = false;
    this.isNotEnoughCreditParameters = false;
    this.isExceedLimit = false;

    if(!this.client || !pureAmount || !monthQuantity || !this.selectedCreditOffer){
      this.isNotEnoughCreditParameters = true;
      return;
    }

    const clientId = Number(this.route.snapshot.paramMap.get('id'));
    const creditOfferId = this.selectedCreditOffer.id;

    if(pureAmount<=0 || monthQuantity<=0){
      this.isBelowZero = true;
      return;
    }

    // if(pureAmount + pureAmount*(this.selectedCreditOffer.percent/100) > this.selectedCreditOffer.creditLimit){
    //   this.isExceedLimit = true;
    //   return;
    // }

    this.creditValidation = await this.creditService.validateCredit(creditOfferId, pureAmount);
    if(this.creditValidation.exceed){
        this.isExceedLimit = true;
        return;
    }

    this.creditService.createNewCredit({clientId, creditOfferId, pureAmount, monthQuantity} as NewCreditData)
      .subscribe(credit => {
        this.credits.push(credit);
      });
  }

  deleteCredit(credit: Credit):void{
    this.credits = this.credits.filter(c => c !== credit);
    this.creditService.deleteCredit(credit.id).subscribe();
  }

  goBack(): void{
    this.location.back();
  }
}
