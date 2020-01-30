import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  buttonName = 'Log in';
  form: any = {};
  loginDiv = true;
  signupDiv = false;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loginForm: FormGroup;
  checkIP = false;
  checkU = false;

  constructor(private route:Router,
              private formBuilder:FormBuilder,
              private authService:AuthService) { }

  ngOnInit() {

    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_ ]*')]],
      password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*')]]
    });

  }

  onSubmit() {
    this.authService.login(this.loginForm.value);
  }
}
