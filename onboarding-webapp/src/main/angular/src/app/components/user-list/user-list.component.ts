import { Phone } from './../../models/phone';
import { USER } from 'src/app/mock-data';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  page: any;
  toggle = false;
  navArray = Array(5);

  constructor(
    private userService: UserService,
    private router: Router
    ) {
  }

  ngOnInit() {
    this.getUsers();
    this.getPageSorted();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  getPageSorted(): void {
    this.userService.getPageSortedbyFirst()
      .subscribe(page => {
        this.page = page;
        //this.users = page.content;
        console.log(this.page.totalPages);
      });
  }

  getPage(i): void {  //testing
    console.log('i = ' + i);
    this.userService.getPageSorted(i)
      .subscribe(page => {
        //this.page = page;
        console.log(this.page.pageNumber);
      });
  }

  addUser(username: string, firstName: string, lastName: string, phones: Phone[]) {
    this.userService.addUser({username, firstName, lastName, phones} as User)
    .subscribe(user => {
      this.users.push(user);
    });
  }

  toggleAddUser() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }

  userClick(user: User) {
    this.router.navigate(['detail/' + user.userId]);
  }
}


