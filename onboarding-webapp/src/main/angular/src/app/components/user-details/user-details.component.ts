import { Phone } from 'src/app/models/phone';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { FormBuilder } from '@angular/forms';
import { $ } from 'protractor';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User;
  phone: Phone;
  toggle = false;

  profileForm = this.fb.group({
    firstName: [''],
    lastName: ['']
  });

  constructor(
    private fb: FormBuilder,
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

  get firstName() {
    return this.profileForm.get('firstName');
  }

  get lastName() {
    return this.profileForm.get('lastName');
  }

  deleteUser() {
    this.userService.deleteUser(this.user.userId)
      .subscribe();
    this.router.navigate(['users']);
  }

  modifyUser(firstName: string, lastName: string) {
    this.user.firstName = firstName;
    this.user.lastName = lastName;
  }

  toggleAddPhone() {
    this.toggle = !this.toggle;
  }

  receiveToggle($event) {
    this.toggle = $event;
  }

  onSubmit() {
    this.modifyUser(this.firstName.value, this.lastName.value);
    console.log(this.user);
    this.userService.updateUser(this.user)
      .subscribe(() => {
        this.getUser();
      });
  }
}
