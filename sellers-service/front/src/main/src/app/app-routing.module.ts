import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegisterComponentComponent } from './register-component/register-component.component';
import { RegistrationComponent } from './registration/registration.component';


const routes: Routes = [
  {path: 'request', component: HomeComponent},
  {path: 'register-client', component: RegisterComponentComponent },
  {path: 'registration', component: RegistrationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }