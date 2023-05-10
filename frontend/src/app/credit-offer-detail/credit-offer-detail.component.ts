import { Component, OnInit } from '@angular/core';
import { CreditOffer } from '../models/creditOffer';
import { Credit } from '../models/credit';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { CreditOfferService } from '../services/credit-offer.service';

@Component({
  selector: 'app-credit-offer-detail',
  templateUrl: './credit-offer-detail.component.html',
  styleUrls: ['./credit-offer-detail.component.css']
})
export class CreditOfferDetailComponent implements OnInit{

  creditOffer: CreditOffer | undefined;
  credits: Credit[] = [];

  isBelowZero: boolean = false;
  isNotEnoughParameters: boolean = false;

  selectedCredit?:Credit;

  constructor(
    private route: ActivatedRoute,
    private creditOfferService: CreditOfferService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getCreditOffer();
  }

  onSelect(credit:Credit):void{
    if(this.selectedCredit === credit)
      this.selectedCredit = undefined;
    else
      this.selectedCredit = credit;
  }

  getCreditOffer(): void{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.creditOfferService.getCreditOffer(id)
      .subscribe(creditOffer => {this.creditOffer = creditOffer, this.credits = creditOffer.credits});
  }

  save(): void{
    this.isBelowZero = false;
    this.isNotEnoughParameters = false;

    if(this.creditOffer && (this.creditOffer.creditLimit<= 0 || this.creditOffer.percent<=0)){
        this.isBelowZero = true;
        return;
    }
    if(this.creditOffer && (!this.creditOffer.creditLimit || !this.creditOffer.percent)){
      this.isNotEnoughParameters = true;
      return;
    }

    if(this.creditOffer){
      this.creditOfferService.updateCreditOffer(this.creditOffer)
        .subscribe(() => this.goBack());
    }
  }

  goBack(): void{
    this.location.back();
  }
}
