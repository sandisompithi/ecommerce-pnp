import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Products } from '../model/products';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  products: Products = new Products();
  submitted = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  newProduct(): void {
    this.submitted = false;
    this.products = new Products();
  }

  save() {
    this.userService.createProduct(this.products)
      .subscribe(
        data => console.log(data),
        error => console.log(error)
    );
    this.products = new Products();
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/admin']);
  }
}
