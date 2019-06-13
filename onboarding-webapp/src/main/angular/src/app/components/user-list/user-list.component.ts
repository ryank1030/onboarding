import { Phone } from './../../models/phone';
import { USER } from 'src/app/mock-data';
import { Component, OnInit } from '@angular/core';
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
  toggle = false;
  test = '';

  constructor(
    private userService: UserService,
    private router: Router
    ) {
  }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
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


