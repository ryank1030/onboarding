import { Component, OnInit, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-phone-add',
  templateUrl: './phone-add.component.html',
  styleUrls: ['./phone-add.component.css']
})
export class PhoneAddComponent implements OnInit {

  @Input() users: User[];
  @Input() toggle: boolean;
  @Output() toggleEvent = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  toggleAddPhone() {
    this.toggle = !this.toggle;
    this.toggleEvent.emit(this.toggle);
  }

}
