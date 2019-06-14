import { Component, OnInit, Input, Output } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder } from '@angular/forms';
import { Phone } from 'src/app/models/phone';

@Component({
  selector: 'app-update-phone',
  templateUrl: './update-phone.component.html',
  styleUrls: ['./update-phone.component.css']
})
export class UpdatePhoneComponent implements OnInit {

  profileForm = this.fb.group({
    phoneNumber: ['']
  });

  @Input() phone: Phone;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
  }

  get phoneNumber() {
    return this.profileForm.get('phoneNumber');
  }

  assemblePhone(phoneNumber: string) {
    this.phone.phoneNumber = phoneNumber;
  }

  onSubmit() {
    this.assemblePhone(this.phoneNumber.value);
    this.updatePhone(this.phone);
  }

  updatePhone(phone: Phone) {
    this.userService.updatePhone(this.phone)
      .subscribe();
  }

}
