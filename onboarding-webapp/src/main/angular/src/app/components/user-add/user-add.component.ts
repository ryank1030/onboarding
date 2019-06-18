import { catchError } from 'rxjs/operators';
import { Component, OnInit, Input, Output } from '@angular/core';
import { FormBuilder, FormsModule, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
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
    username: null,
    firstName: null,
    lastName: null,
    phones: this.fb.array([
      this.fb.group({
        phoneNumber: null
      })
    ]),
  });

  @Input() users: User[];
  @Input() toggle: boolean;
  @Output() toggleEvent = new EventEmitter();
  usernameError = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
    ) { }

  ngOnInit() {
  }

  toggleComponent() {
    this.toggle = !this.toggle;
    this.toggleEvent.emit(this.toggle);
  }

  onSubmit() {
    this.addUser(this.profileForm.getRawValue() as User);
    this.toggleComponent();
  }

  addUser(u: User) {
    this.userService.addUser(u)
      .subscribe(user => {
        this.users.push(user);
    });
  }
}
