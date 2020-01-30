import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmPasswordValidator} from "../validation/confirm-pass.validator";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private userService:UserService) {
  }

  ngOnInit() {
    this.signUpForm = this.formBuilder.group({

      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      username: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_ ]*'), Validators.maxLength(15)]],
      email: ['', [Validators.required, Validators.email]],
      city: ['', [Validators.required]],
      country: ['', [Validators.required]],
      title: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]],
      passwordConfirm: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]]
    }, {
      validator: ConfirmPasswordValidator.validate.bind(this)
    });
  }

  onSubmit() {

    console.log(this.signUpForm.value);

    this.userService.signup(this.signUpForm.value).subscribe(data=>{
      console.log(data);
    });



  }

}
