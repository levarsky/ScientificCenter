import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfirmPasswordValidator} from "../validation/confirm-pass.validator";
import {RegistrationService} from "../services/registration.service";
import {RegisterClientService} from "../services/register-client.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  signUpForm: FormGroup;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private registerClientService:RegisterClientService,
              private registrationService:RegistrationService) {
  }

  ngOnInit() {
    this.signUpForm = this.formBuilder.group({

      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      username: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_ ]*'), Validators.maxLength(15)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]],
      passwordConfirm: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]]
    }, {
      validator: ConfirmPasswordValidator.validate.bind(this)
    });
  }

  onSubmit() {

    console.log(this.signUpForm.value);

    this.registerClientService.signup(this.signUpForm.value).subscribe(data=>{
      console.log(data);
    });

  }
}
