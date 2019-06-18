import { Component, ViewChild, ElementRef, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-phone-number',
  templateUrl: './phone-number.component.html',
  styleUrls: ['./phone-number.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PhoneNumberComponent),
      multi: true
    }
  ]
})
export class PhoneNumberComponent implements ControlValueAccessor {

  @ViewChild('input') input: ElementRef;
  disabled: boolean;
  value: string;
  onChange: (value: any) => void;
  onTouched: () => void;


  constructor() { }

  writeValue(value: string): void {
    this.input.nativeElement.value = value;
  }
  registerOnChange(fn: (value: any) => void) {
    this.onChange = fn;
  }
  registerOnTouched(fn: () => void) {
    this.onTouched = fn;
  }
  setDisabledState?(disabled: boolean): void {
    this.disabled = disabled;
  }

}
