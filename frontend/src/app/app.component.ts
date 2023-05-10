import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Credit Management';

  isLoggedIn():boolean{
    let token =  sessionStorage.getItem('token');
    if(token)
      return true;
    else
      return false;
  }
}
