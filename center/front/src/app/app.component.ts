import { Component } from '@angular/core';
import {AuthService} from "./services/auth.service"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';
  loggedIn : boolean;
  constructor(private authService : AuthService){
    this.loggedIn = authService.isLoggedIn();
  }

  logOut(){
    this.authService.logout();
  }
}
