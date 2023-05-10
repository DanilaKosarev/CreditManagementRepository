import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientsComponent } from './clients/clients.component';
import { CreditOffersComponent } from './credit-offers/credit-offers.component';
import { ClientDetailComponent } from './client-detail/client-detail.component';
import { CreditOfferDetailComponent } from './credit-offer-detail/credit-offer-detail.component';
import { AuthenticationGuard } from './authentication.guard';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  {path: '', canActivate:[AuthenticationGuard], children:[
    {path: 'login', component:LoginComponent},
    {path: 'clients', component:ClientsComponent},
    {path: 'clients/:id', component:ClientDetailComponent},
    {path: 'credit-offers', component:CreditOffersComponent},
    {path: 'credit-offers/:id', component:CreditOfferDetailComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'registration', component:RegistrationComponent},
    {path: '**', redirectTo: ''}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
