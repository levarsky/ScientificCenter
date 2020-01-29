import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaymentComponent } from './payment/payment.component'
import {NavbarComponent} from './navbar/navbar.component'
import {BankComponent} from "./bank/bank.component";
import {PaymentStatusComponent} from "./payment-status/payment-status.component";
import {SigninComponent} from "./signin/signin.component";
import {SignupComponent} from "./signup/signup.component";
import {MagazinesComponent} from "./magazines/magazines.component";
import {MagazineComponent} from "./magazine/magazine.component";
import {CartComponent} from "./cart/cart.component"
import {PurchasedComponent} from "./purchased/purchased.component"
import {SubscriptionsComponent} from "./subscriptions/subscriptions.component"
import {PublicationsComponent} from "./publications/publications.component"


const routes: Routes = [
  {path: 'home', component: PaymentComponent},
  {path: 'paymentStatus', component: PaymentStatusComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'signin', component: SigninComponent},
  {path: 'magazines', component: MagazinesComponent},
  {path: 'magazine', component: MagazineComponent, pathMatch: 'full'},
  {path: 'cart', component: CartComponent},
  {path: 'subscriptions', component: SubscriptionsComponent},
  {path: 'purchased', component: PurchasedComponent},
  {path: 'publications', component: PublicationsComponent, pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
