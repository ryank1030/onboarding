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
  navArray;

  constructor(
    private userService: UserService,
    private router: Router
    ) {
  }

  ngOnInit() {
    //this.getUsers();
    //this.getPageSorted();
    this.getPage(0);
  }

  /*
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
  */

  getPage(i): void {  //testing
    console.log('i = ' + i);
    this.userService.getPageSorted(i)
      .subscribe(page => {
        this.page = page;
        this.users =  page.content;
        this.navArray = Array(this.page.totalPages);
        console.log('Current Page: ' + this.page.number);
        console.log('Number of pages: ' + this.page.totalPages);
        console.log('Number of elements: ' + this.page.numberOfElements);
      });
  }

  /*
  addUser(username: string, firstName: string, lastName: string, phones: Phone[]) {
    this.userService.addUser({username, firstName, lastName, phones} as User)
    .subscribe(user => {
      this.users.push(user);
    });
  }
  */

  toggleAddUser() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }

  receiveUsers($event) {
    this.users = $event;
  }

  receiveNav($event) {
    this.navArray = $event;
  }

  userClick(user: User) {
    this.router.navigate(['detail/' + user.userId]);
  }
}


