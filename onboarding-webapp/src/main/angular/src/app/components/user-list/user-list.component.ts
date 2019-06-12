import { Phone } from './../../models/phone';
import { USER } from 'src/app/mock-data';
import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  testuser: User = USER;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  add() {
    /*
    this.userService.addUser(this.testuser)
    .subscribe(user => {
      this.users.push(user);
    });
    */
    this.addUser(null, 'uname2', 'fname2', 'lname2', this.testuser.phones);
  }

  addUser(userId: string, username: string, firstName: string, lastName: string, phones: Phone[]) {
    this.userService.addUser({username, firstName, lastName, phones} as User)
    .subscribe(user => {
      this.users.push(user);
    });
  }

}


