import { Component, OnInit } from '@angular/core';
import { Category } from '../model/category';
import { UserService } from '../_services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  category: Category = new Category();
  submitted = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  newCategory(){
    this.submitted = false;
    this.category = new Category();
  }

  save() {
    this.userService.createCategory(this.category)
      .subscribe(
        data => console.log(data),
        error => console.log(error)
      );
    this.category = new Category();
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
