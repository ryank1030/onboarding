import { Component, OnInit, Input, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { Phone } from 'src/app/models/phone';
import { USER } from 'src/app/mock-data';
import {UserService} from '../../services/user.service';
import { EventEmitter } from '@angular/core';


@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})

export class UserAddComponent implements OnInit {

  profileForm = this.fb.group({
    username: [null, Validators.required],
    firstName: [null, Validators.required],
    lastName: [null, Validators.required],
    phones: this.fb.group({
      phoneNumber: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(16), Validators.pattern('[0-9]*')]]
    }),
  });

  @Input() users: User[];
  @Input() toggle: boolean;
  @Output() toggleEvent = new EventEmitter();

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

  assemblePhone(phoneNumber: string): Phone[] {
    return [ { phoneNumber } as Phone ];
  }

  assembleUser(username: string, firstName: string, lastName: string, phones: Phone[]): User {
    return {username, firstName, lastName, phones} as User;
  }

  onSubmit() {
    this.addUser(
      this.assembleUser(this.username.value, this.firstName.value, this.lastName.value, this.assemblePhone(this.phoneNumber.value))
      );
    this.toggle = !this.toggle;
    this.toggleEvent.emit(this.toggle);
  }

  addUser(u: User) {
    this.userService.addUser(u)
      .subscribe(user => {
        this.users.push(user);
    });
  }
}
