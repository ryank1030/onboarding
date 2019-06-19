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
  toggle = false;
  collectionSize;
  pageSize;
  navArray;
  page = 0;

  constructor(
    private userService: UserService,
    private router: Router
    ) {
  }

  ngOnInit() {
    this.getPage(0);
  }

  getPage(i): void {
    this.userService.getPageSorted(i)
      .subscribe(page => {
        console.log(this.pageSize);
        this.users =  page.content;
        this.collectionSize = page.totalElements;
        this.pageSize = page.size;
        this.navArray = Array(page.totalPages);
      });
  }

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

  searchData(value: string) {
    this.userService.getPageSearch(0, value)
      .subscribe(page => {
        this.users =  page.content;
        this.navArray = Array(page.totalPages);
      });
  }
}


