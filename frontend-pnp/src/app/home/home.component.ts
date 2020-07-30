import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Products } from '../model/products'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: Observable<Products[]>;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getPublicContent().subscribe(
      data => {
        this.products = data;
      },
      err => {
        this.products = JSON.parse(err.error).message;
      }
    );
  }

}
