import { Component, OnInit  } from '@angular/core';
import { CreditOffer } from '../models/creditOffer';
import { CreditOfferService } from '../services/credit-offer.service';

@Component({
  selector: 'app-credit-offers',
  templateUrl: './credit-offers.component.html',
  styleUrls: ['./credit-offers.component.css']
})
export class CreditOffersComponent implements OnInit{

  creditOffers: CreditOffer[] = [];

  isBelowZero: boolean = false;
  isNotEnoughParameters: boolean = false;

  constructor(private creditOfferService:CreditOfferService) {}

  ngOnInit(): void {
    this.getCreditOffers();
  }

  getCreditOffers():void {
    this.creditOfferService.getCreditOffers()
      .subscribe(creditOffers => this.creditOffers = creditOffers);
  }

  add(creditLimit: number, percent:number): void {
    this.isBelowZero = false;
    this.isNotEnoughParameters = false;
    
    if(!creditLimit || !percent) {
      this.isNotEnoughParameters = true;
      return;
    }
    if(creditLimit <= 0 || percent <=0){
      this.isBelowZero = true;
      return;
    }
    this.creditOfferService.addCreditOffer({creditLimit, percent} as CreditOffer)
      .subscribe(creditOffer => {
        this.creditOffers.push(creditOffer);
      });
  }

  delete(creditOffer: CreditOffer):void{
    this.creditOffers = this.creditOffers.filter(c => c !==creditOffer);
    this.creditOfferService.deleteCreditOffer(creditOffer.id).subscribe();
  }

}
