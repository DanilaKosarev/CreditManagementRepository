import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{


  private authUrl = `${environment.apiUrl}/api/auth/login`;


  isBadCredentials = false;
  model: any = {};
  jwt: any = "";

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    let token = sessionStorage.getItem('token');
    if(token)
      this.router.navigate(['/clients']);
  }

  login() {
    this.isBadCredentials = false;

    this.http.post<any>(this.authUrl, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(res => {
      if (res) {
        this.jwt = res.jwt;

        sessionStorage.setItem(
          'token',
          this.jwt
        );
        this.router.navigate(['/clients']);
      }
    });
	  if(this.jwt =="")
      this.isBadCredentials = true;
  }

}
