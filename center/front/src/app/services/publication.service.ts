import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {

  private basicPath = 'center/publication';

  constructor(private httpClient: HttpClient) { }

  getById(id : number) {
        return this.httpClient.get(this.basicPath + "/" + id) as Observable<any>;
  }
}
