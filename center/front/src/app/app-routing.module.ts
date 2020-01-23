import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaymentComponent } from './payment/payment.component'
import {NavbarComponent} from './navbar/navbar.component'
import {BankComponent} from "./bank/bank.component";
import {PaymentStatusComponent} from "./payment-status/payment-status.component";


const routes: Routes = [
  {path: 'home', component: PaymentComponent},
  {path: 'paymentStatus', component: PaymentStatusComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
