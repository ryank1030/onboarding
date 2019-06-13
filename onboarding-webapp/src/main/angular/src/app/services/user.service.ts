import { Phone } from './../models/phone';
import { Injectable } from '@angular/core';
import { User } from "../models/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = './api/v1/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user);
  }

  getUser(id: string): Observable<User> {
    return this.http.get<User>(this.usersUrl + '/' + id);
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete(this.usersUrl + '/' + userId);
  }

  deletePhone(userId: string, phoneId: string): Observable<any> {
    return this.http.delete(this.usersUrl + '/' + userId + '/phones/' + phoneId);
  }

  makePrimary(userId: string, phoneId: string): Observable<any> {
    return this.http.put(this.usersUrl + '/' + userId + '/phones/' + phoneId + '/makePrimary', null);
  }

  verifyPhone(userId: string, phoneId: string): Observable<any> {
    return this.http.get(this.usersUrl + '/' + userId + '/phones/' + phoneId + '/verifyPhone');
  }

  verify(userId: string, phoneId: string, link: string): Observable<any> {
    return this.http.get(this.usersUrl + '/' + userId + '/phones/' + phoneId + '/' + link);
  }
}
