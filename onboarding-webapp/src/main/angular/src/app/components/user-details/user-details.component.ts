import { Phone } from 'src/app/models/phone';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;
  phone: Phone;
  toggle = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.userService.getUser(id)
      .subscribe(user => this.user = user);
  }

  deleteUser() {
    this.userService.deleteUser(this.user.userId)
      .subscribe();
    this.router.navigate(['users']);
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

  toggleAddPhone() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }
}
