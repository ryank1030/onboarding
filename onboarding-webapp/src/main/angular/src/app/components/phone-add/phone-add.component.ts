import { Component, OnInit, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Phone } from 'src/app/models/phone';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';

@Component({
  selector: 'app-phone-add',
  templateUrl: './phone-add.component.html',
  styleUrls: ['./phone-add.component.css']
})
export class PhoneAddComponent implements OnInit {

  @Input() user: User;
  @Input() toggle: boolean;
  @Output() toggleEvent = new EventEmitter();

  profileForm = this.fb.group({
      phoneNumber: null
    });

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
  }

  get phoneNumber() {
    return this.profileForm.get('phoneNumber');
  }

  onSubmit() {
    if (this.phoneNumber !== null) {
      this.addPhone(this.assemblePhone(this.phoneNumber.value));
    }
    this.toggle = !this.toggle;
    this.toggleEvent.emit(this.toggle);
  }

  assemblePhone(phoneNumber: string): Phone {
    return { phoneNumber } as Phone;
  }

  addPhone(phone: Phone) {
    this.userService.addPhone(this.user.userId, phone)
      .subscribe(phone => {
        this.user.phones.push(phone);
      });
  }
}
