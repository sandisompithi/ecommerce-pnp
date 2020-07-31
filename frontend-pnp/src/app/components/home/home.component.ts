import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Products } from 'src/app/model/products';
import { UserService } from 'src/app/_services/user.service';
import { Category } from 'src/app/model/category';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  products: Observable<Products[]>;
  categories: Observable<Category[]>;

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

    this.userService.getAllCategories().subscribe(
      data => {
        console.log(data);
      },
      err => {
        console.log(err);
      }
    );
  }
}
