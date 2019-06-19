import { catchError } from 'rxjs/operators';
import { Phone } from './../models/phone';
import { Injectable } from '@angular/core';
import { User } from "../models/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = './api/v1/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  getUsersSortedByFirst(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + '/sortFirst')
      .pipe(
        catchError(this.handleError)
      );
  }

  getUsersSortedByLast(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + '/sortLast')
      .pipe(
        catchError(this.handleError)
      );
  }

  getPageSortedbyFirst(): Observable<any> {
    return this.http.get<any>(this.usersUrl + '/page')
    .pipe(
      catchError(this.handleError)
    );
  }

  getPageSorted(i): Observable<any> {
    return this.http.get<any>(this.usersUrl + '?page=' + i)
      .pipe(
        catchError(this.handleError)
      );
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user)
      .pipe(
        catchError(this.handleError)
      );
  }

  getUser(id: string): Observable<User> {
    return this.http.get<User>(this.usersUrl + '/' + id)
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteUser(userId: string): Observable<any> {
    return this.http.delete(this.usersUrl + '/' + userId)
      .pipe(
        catchError(this.handleError)
      );
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(this.usersUrl + '/' + user.userId, user)
      .pipe(
        catchError(this.handleError)
      );
  }

  addPhone(userId: string, phone: Phone): Observable<Phone> {
    return this.http.post<Phone>(this.phoneUrl(userId), phone)
      .pipe(
        catchError(this.handleError)
      );
  }

  updatePhone(phone: Phone): Observable<Phone> {
    return this.http.put<Phone>(this.phoneIdUrl(phone.userId, phone.phoneId), phone)
      .pipe(
        catchError(this.handleError)
      );
  }

  deletePhone(userId: string, phoneId: string): Observable<any> {
    return this.http.delete(this.phoneIdUrl(userId, phoneId))
      .pipe(
        catchError(this.handleError)
      );
  }

  makePrimary(userId: string, phoneId: string): Observable<any> {
    return this.http.put(this.phoneIdUrl(userId, phoneId) + '/makePrimary', null)
      .pipe(
        catchError(this.handleError)
      );
  }

  verifyPhone(userId: string, phoneId: string): Observable<any> {
    return this.http.get(this.phoneIdUrl(userId, phoneId) + '/verifyPhone')
      .pipe(
        catchError(this.handleError)
      );
  }

  verify(userId: string, phoneId: string, link: string): Observable<any> {
    return this.http.get(this.phoneIdUrl(userId, phoneId) + '/' + link)
      .pipe(
        catchError(this.handleError)
      );
  }

  phoneUrl(userId: string): string {
    return this.usersUrl + '/' + userId + '/phones';
  }

  phoneIdUrl(userId: string, phoneId: string): string {
    return this.phoneUrl(userId) + '/' + phoneId;
  }

  handleError(error) {
    return throwError(error.error);
  }
}
