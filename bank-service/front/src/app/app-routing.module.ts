import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AccountRegistrationComponent} from "./account-registration/account-registration.component";


const routes: Routes = [
  {path: 'registrationRequest', component: AccountRegistrationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
