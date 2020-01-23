import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PaymentComponent } from './payment/payment.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FormsModule, ReactiveFormsModule }         from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BankComponent } from './bank/bank.component';
import { PaymentStatusComponent } from './payment-status/payment-status.component';

@NgModule({
  declarations: [
    AppComponent,
    PaymentComponent,
    NavbarComponent,
    BankComponent,
    PaymentStatusComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [

    // [
    //   {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
    // ]

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
