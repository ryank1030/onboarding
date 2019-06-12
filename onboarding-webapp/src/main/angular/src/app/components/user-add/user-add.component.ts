import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';
import { User } from 'src/app/models/user';
import { Phone } from 'src/app/models/phone';
import { USER } from 'src/app/mock-data';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})

export class UserAddComponent implements OnInit {

  profileForm = this.fb.group({
    username: [''],
    firstName: [''],
    lastName: [''],
    phones: this.fb.group({
      phoneNumber: ['']
    }),
  });

  @Input() users: User[];
  tempuser: User = USER;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
    ) { }

  ngOnInit() {
  }

  get username() {
    return this.profileForm.get('username');
  }

  get firstName() {
    return this.profileForm.get('firstName');
  }

  get lastName() {
    return this.profileForm.get('lastName');
  }

  get phones() {
    return this.profileForm.get('phones');
  }

  get phoneNumber() {
    return this.phones.get('phoneNumber');
  }

  onSubmit() {
    //this.addUser(this.username.value, this.firstName.value, this.lastName.value, this.tempuser.phones);
    this.addUser(this.makeUser(this.username.value, this.firstName.value, this.lastName.value, this.makePhone(this.phoneNumber.value)));
  }

  makePhone(phoneNumber: string): Phone[] {
    return [ { phoneNumber } as Phone ];
  }

  makeUser(username: string, firstName: string, lastName: string, phones: Phone[]): User {
    return {username, firstName, lastName, phones} as User;
  }

  /*
  addUser(username: string, firstName: string, lastName: string, phones: Phone[]) {
    this.userService.addUser({username, firstName, lastName, phones} as User)
      .subscribe(user => {
        this.users.push(user);
    });
  }
  */
 addUser(u: User) {
  this.userService.addUser(u)
    .subscribe(user => {
      this.users.push(user);
  });
}
}
