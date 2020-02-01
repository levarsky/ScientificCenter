import {Injectable} from "@angular/core";
import {CanDeactivate} from "@angular/router";
import {CompCanDeactivate} from "./can-deactivate";

@Injectable()
export class CanDeactivateGuard implements CanDeactivate<CompCanDeactivate> {
  canDeactivate(component: CompCanDeactivate): boolean {

    if(!component.canDeactivate()){
      if (confirm("You have unsaved changes! If you leave, your changes will be lost.")) {
        return true;
      } else {
        return false;
      }
    }
    return true;
  }
}
