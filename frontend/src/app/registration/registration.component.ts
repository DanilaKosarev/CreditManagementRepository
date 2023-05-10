import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.development';
import { Location } from '@angular/common';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit{

  private registrationUrl = `${environment.apiUrl}/api/auth/registration`;

  isNotEnoughParameters: boolean | undefined;
  isInvalid: boolean | undefined;
  model: any = {};

  constructor(
    private router: Router,
    private http: HttpClient,
    private location: Location
  ) { }

  ngOnInit(): void {
    let token = sessionStorage.getItem('token');
    if(token)
      this.router.navigate(['/clients']);
  }

  regist() {
    this.isNotEnoughParameters = false;
    this.isInvalid = false;


    if(!this.model.username || !this.model.password){
        this.isNotEnoughParameters = true;
        return;
    }

    this.http.post<boolean>(this.registrationUrl, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(res => {
      if (res) 
        this.router.navigate(['/login']);

          this.isInvalid = !res;
      
    });
  }

  goBack(): void{
    this.location.back();
  }
  
}
