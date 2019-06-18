import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Phone } from 'src/app/models/phone';
import { User } from 'src/app/models/user';
import { userInfo } from 'os';

@Component({
  selector: 'app-phone-details',
  templateUrl: './phone-details.component.html',
  styleUrls: ['./phone-details.component.css']
})
export class PhoneDetailsComponent implements OnInit {

  phoneForm = this.fb.group({
    phoneNumber: null
  });

  @Input() user: User;
  phone: Phone;
  toggle = false;
  errors: Error;

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

  updateThisPhone(phone: Phone) {
    this.phone = phone;
  }

  toggleAddPhone() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }

  deletePhone(phone: Phone) {
    if (this.user.phones.length > 1) {
    this.userService.deletePhone(this.user.userId, phone.phoneId)
      .subscribe(() => {
        this.getUser();
      });
    } else {
      this.userService.handleError(Error('User Must Have One Phone'));
    }
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

  updatePhone(userId: string, phoneId: string, phoneNumber: string) {
    this.userService.updatePhone({userId, phoneId, phoneNumber} as Phone)
      .subscribe(() => {
        this.getUser();
      }, err => {
        this.errors = err;
      });
  }

  onSubmit() {
      this.errors = null;
      this.updatePhone(this.phone.userId, this.phone.phoneId, this.phoneNumber.value);
  }
}
