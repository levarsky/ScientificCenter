import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaymentComponent } from './payment/payment.component'
import {NavbarComponent} from './navbar/navbar.component'
import {BankComponent} from "./bank/bank.component";


const routes: Routes = [
  {path: '', component: PaymentComponent},
  {path: 'forgot-password', component: BankComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
