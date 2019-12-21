import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PaymentComponent } from './payment/payment.component';
import { NavbarComponent } from './navbar/navbar.component';
import { BankComponent } from './bank/bank.component';
import { PaypalComponent } from './paypal/paypal.component';
import { BitcoinComponent } from './bitcoin/bitcoin.component';
import { FormsModule, ReactiveFormsModule }         from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    PaymentComponent,
    NavbarComponent,
    BankComponent,
    PaypalComponent,
    BitcoinComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
