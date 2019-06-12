import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})

export class UserAddComponent implements OnInit {

  /*
    Data we need to store
  User:
   - Username
   - First Name
   - Last Name
   - Phones:
      - Phone Number
  */

  profileForm = this.fb.group({
    username: [''],
    firstName: [''],
    lastName: [''],
    phones: this.fb.group({
      phoneNumber: ['']
    }),
  });
  name = new FormControl('');

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  onSubmit() {
    console.warn(this.profileForm.value);
  }
}
