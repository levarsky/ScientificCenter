import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  navBarForm: FormGroup;

  buttonName = 'Sign up';
  loginDiv = true;
  signupDiv = false;

  isAdmin = false;
  isEditor = false;
  isReviewer = false;


  isLoggedIn = false;

  roles: string[] = [];
  adminRoles: string[] = ['camunda-admin'];

  name = 'Log in';

  username = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit() {


      // this.authService.getGroups().subscribe(data=>{
      //     this.roles = data;
      //     console.log(data);
      //
      //   for (let role of this.adminRoles){
      //     if (this.roles.includes(role)){
      //       console.log(role);
      //       this.isAdmin = true;
      //     }
      //   };
      // });


  }

  logout() {
    this.authService.logout()
    window.location.href = "home";
  }

}
