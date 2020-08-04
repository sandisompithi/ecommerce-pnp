import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';
import { Observable } from 'rxjs';
import { UserService } from './_services/user.service';
import { CategoryService } from './_services/category.service';
import { Category } from './model/category';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;
  categories: Observable<Category[]>;

  constructor(private tokenStorageService: TokenStorageService, private userService: UserService, 
      private categoryService: CategoryService) {
   }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
    this.getCategories();
  }

  getCategories(){
    this.categoryService.getCategories()
      .subscribe(data => {
        let response = JSON.parse(JSON.stringify(data));
        this.categories = response.data;
        console.log(data);
      }, error => {
        this.categories = error;
        console.log(error);
      });
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
