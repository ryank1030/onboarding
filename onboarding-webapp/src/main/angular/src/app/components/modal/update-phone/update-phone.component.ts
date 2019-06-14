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

  phoneForm = this.fb.group({
    phoneNumber: null
  });

  @Input() phone: Phone;

  constructor(
    private userService: UserService,
    private fb: FormBuilder
  ) { }

  ngOnInit() {
  }

  get phoneNumber() {
    return this.phoneForm.get('phoneNumber');
  }

  assemblePhone(phoneNumber: string) {
    this.phone.phoneNumber = phoneNumber;
  }

  onSubmit() {
    if (this.phoneNumber.value !== null) {
      this.assemblePhone(this.phoneNumber.value);
      this.updatePhone(this.phone);
    }
  }

  updatePhone(phone: Phone) {
    this.userService.updatePhone(this.phone)
      .subscribe();
  }

}
