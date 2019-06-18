import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent {

  public form = this.fb.group({
    phoneOne: ['', Validators.required]
  });

  constructor(private fb: FormBuilder) { }

}
