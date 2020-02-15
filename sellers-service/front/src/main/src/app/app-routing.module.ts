import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { RegistrationComponent } from './registration/registration.component';
import {LoginComponent} from "./login/login.component";
import {PaymentRequestComponent} from "./payment-request/payment-request.component";
import {DashboardComponent} from "./dashboard/dashboard.component";


const routes: Routes = [
  {path: 'request', component: PaymentRequestComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'register-client', component: RegisterComponentComponent },
  {path: 'signup', component: RegistrationComponent },
  {path: 'login', component: LoginComponent},
  {path: '', component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
