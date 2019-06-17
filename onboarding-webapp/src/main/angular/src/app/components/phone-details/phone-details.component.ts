import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Phone } from 'src/app/models/phone';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-phone-details',
  templateUrl: './phone-details.component.html',
  styleUrls: ['./phone-details.component.css']
})
export class PhoneDetailsComponent implements OnInit {

  phoneForm = this.fb.group({
    phoneNumber: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(16), Validators.pattern('[0-9]*')]]
  });

  @Input() user: User;
  phone: Phone;
  toggle = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit() {
  }

  getUser(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.userService.getUser(id)
      .subscribe(user => this.user = user);
  }

  get phoneNumber() {
    return this.phoneForm.get('phoneNumber');
  }

  toggleAddPhone() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }

  deletePhone(phone: Phone) {
    this.userService.deletePhone(this.user.userId, phone.phoneId)
      .subscribe(() => {
        this.getUser();
      });
  }

  makePrimary(phone: Phone) {
    this.userService.makePrimary(this.user.userId, phone.phoneId)
      .subscribe(() => {
        this.getUser();
      });
  }

  verifyPhone(phone: Phone) {
    this.userService.verifyPhone(this.user.userId, phone.phoneId)
      .subscribe(() => {
        this.getUser();
      });
  }

  verify(phone: Phone) {
    this.userService.verify(this.user.userId, phone.phoneId, phone.verificationLink)
      .subscribe(() => {
        this.getUser();
      });
  }

  updatePhone(phone: Phone) {
    this.userService.updatePhone(this.phone)
      .subscribe(() => {
        this.getUser();
      });
  }

  assemblePhone(phoneNumber: string) {
    this.phone.phoneNumber = phoneNumber;
  }

  updateThisPhone(phone: Phone) {
    this.phone = phone;
  }

  onSubmit() {
    if (this.phoneNumber.value !== null) {
      this.assemblePhone(this.phoneNumber.value);
      this.updatePhone(this.phone);
    }
  }
}
