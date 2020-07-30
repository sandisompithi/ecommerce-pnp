import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Observable } from 'rxjs';
import { Products } from '../model/products';
import { Router } from '@angular/router';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  products: Observable<Products[]>;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
      this.reloadData();
  }

  reloadData() {
    this.userService.getPublicContent().subscribe(
      data => {
        this.products = data;
      },
      err => {
        this.products = JSON.parse(JSON.stringify(err)).message;
      }
    );
  }
  deleteProduct(id: number) {
    this.userService.deleteProduct(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        }, 
        error => console.log(error)
      );
  }

  updateProduct(id: number){
    this.router.navigate(['admin/update', id]);
  }

}
