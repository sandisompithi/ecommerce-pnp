import { Component, OnInit } from '@angular/core';
import { Products } from '../model/products';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {

  id: number;
  product: Products;

  constructor(private route: ActivatedRoute, private router: Router,
    private userService: UserService) { }

  ngOnInit(): void {
    this.product = new Products();

    this.id = this.route.snapshot.params['id'];

    this.userService.getProduct(this.id)
      .subscribe(data => {
        console.log(data)
        this.product = data;
      }, error => console.log(error));
  }

  updateProduct() {
    this.userService.updateProduct(this.id, this.product)
      .subscribe(
        data => console.log(data),
        error =>console.log(error)
      );
    this.product = new Products();
    this.gotoList();
  }

  onSubmit() {
    this.updateProduct();
  }

  gotoList() {
    this.router.navigate(['/admin']);
  }

}
