import { Component, OnInit, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Phone } from 'src/app/models/phone';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';

@Component({
  selector: 'app-phone-add',
  templateUrl: './phone-add.component.html',
  styleUrls: ['./phone-add.component.css']
})
export class PhoneAddComponent implements OnInit {

  @Input() user: User;
  @Input() toggle: boolean;
  @Output() toggleEvent = new EventEmitter();
  errors: Error;

  profileForm = this.fb.group({
      phoneNumber: null
    });

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    this.errors = null;
    this.addPhone(this.profileForm.getRawValue() as Phone);
  }

  toggleComponent() {
    this.toggle = !this.toggle;
    this.toggleEvent.emit(this.toggle);
  }

  addPhone(phone: Phone) {
    this.userService.addPhone(this.user.userId, phone)
      .subscribe(phone => {
        this.user.phones.push(phone);
        this.toggleComponent();
      }, err => {
        this.errors = err;
      });
  }
}
