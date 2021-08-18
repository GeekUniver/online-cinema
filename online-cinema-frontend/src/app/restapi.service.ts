import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  private loginUrl!: 'http://localhost:8080/auth';
  private signUpUrl!: '';

  constructor(private http: HttpClient) { }

  login(username: string,password: string){
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    return this.http.post("http://localhost:8080/auth",{headers,responseType: 'text' as 'json'})
  }
  // ,skj this.http.post("http://localhost:8080/auth",{headers,responseType: 'text' as 'json'})
  // дописал auth, поменял на post c get  //{headers,responseType: 'text' as 'json'}
//this.uri + '/authenticate', {email: email,password: password}
  // this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
}

