import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/home/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all');
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin');
  }

  getAllCategories(): Observable<any> {
    return this.http.get(API_URL + 'category');
  }

  getCategoryById(id: number): Observable<any> {
    return this.http.get(API_URL + 'category/' + id);
  }

  getProduct(id: number): Observable<any> {
    return this.http.get(API_URL + 'product/' + id);
  }

  createCategory(category: Object): Observable<Object> {
    return this.http.post(API_URL + 'admin/add/category', category);
  }

  createProduct(product: Object): Observable<Object> {
    return this.http.post(API_URL + 'admin/add/product', product);
  }

  updateProduct(id: number, value: any): Observable<Object> {
    return this.http.put(API_URL + 'admin/update/product/' + id, value);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(API_URL + 'admin/delete/product/' + id)
  }

}
