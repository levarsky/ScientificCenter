import {AbstractControl, Validators, FormGroup} from '@angular/forms';

export class ConfirmPasswordValidator {
  static validate(registrationFormGroup: FormGroup) {
    let password = registrationFormGroup.controls.password.value;
    let repeatPassword = registrationFormGroup.controls.passwordConfirm.value;

    if (repeatPassword.length <= 6) {
      return null;
    }

    if (repeatPassword !== password) {
      return {
        doesMatchPassword: true
      };
    }

    return null;

  }
}
