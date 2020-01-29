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
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { MagazinesComponent } from './magazines/magazines.component';
import { MagazineComponent } from './magazine/magazine.component';
import { CartComponent } from './cart/cart.component';
import { PurchasedComponent } from './purchased/purchased.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { PublicationsComponent } from './publications/publications.component';

@NgModule({
  declarations: [
    AppComponent,
    PaymentComponent,
    NavbarComponent,
    BankComponent,
    PaymentStatusComponent,
    SigninComponent,
    SignupComponent,
    MagazinesComponent,
    MagazineComponent,
    CartComponent,
    PurchasedComponent,
    SubscriptionsComponent,
    PublicationsComponent
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
