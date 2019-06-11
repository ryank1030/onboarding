import { Injectable } from '@angular/core';
import { User } from "./user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = './api/v1/users'

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
    //return of(USERS);
  }

}
