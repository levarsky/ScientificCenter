import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  private basicPath = 'center/magazine';

  constructor(private httpClient: HttpClient) { }

  getAll() {
    return this.httpClient.get(this.basicPath) as Observable<any>;
  }

  getById(id : number) {
      return this.httpClient.get(this.basicPath + "/" + id) as Observable<any>;
  }

  getAreas(id : number) {
      return this.httpClient.get(this.basicPath + "/areas/" + id) as Observable<any>;
  }

  getRedactors(id : number) {
        return this.httpClient.get(this.basicPath + "/redactors/" + id) as Observable<any>;
  }

  getReviewers(id : number) {
        return this.httpClient.get(this.basicPath + "/reviewers/" + id) as Observable<any>;
  }

  getPublications(id : number) {
        return this.httpClient.get(this.basicPath + "/publications/" + id) as Observable<any>;
  }
}
