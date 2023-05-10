import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatSelectModule} from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientsComponent } from './clients/clients.component';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CreditOffersComponent } from './credit-offers/credit-offers.component';
import { ClientDetailComponent } from './client-detail/client-detail.component';
import { CreditOfferDetailComponent } from './credit-offer-detail/credit-offer-detail.component';
import { LoginComponent } from './login/login.component';
import { Router } from '@angular/router';
import { RequestInterceptor } from './request.interceptor';
import { LogoutComponent } from './logout/logout.component';
import { RegistrationComponent } from './registration/registration.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    CreditOffersComponent,
    ClientDetailComponent,
    CreditOfferDetailComponent,
    LoginComponent,
    LogoutComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatSelectModule,
    BrowserAnimationsModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, 
    useFactory: function(router:Router){
      return new RequestInterceptor(router);
    }, multi: true,
  deps:[Router]}],
  bootstrap: [AppComponent]
})
export class AppModule { }
