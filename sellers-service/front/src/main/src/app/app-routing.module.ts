import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { RegistrationComponent } from './registration/registration.component';
import {LoginComponent} from "./login/login.component";
import {PaymentRequestComponent} from "./payment-request/payment-request.component";
import {DashboardComponent} from "./dashboard/dashboard.component";


const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'request', component: PaymentRequestComponent},
  {path: 'signup', component: RegistrationComponent },
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent,
    children:[
      {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
      {path: 'dashboard', component: DashboardComponent},
      {path: 'client', component: RegisterComponentComponent },
      ]
  }
  ]
;

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
