import {
  Directive,
  ElementRef,
  HostListener,
  Input,
  Renderer2,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[ngMask]',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: NgMaskDirective,
      multi: true,
    },
  ],
})
export class NgMaskDirective implements ControlValueAccessor {
  onTouched: any;
  onChange: any;
  valor: any;

  @Input() ngMask: string;

  constructor(private elementRef: ElementRef, private renderer2: Renderer2) {}

  setDisabledState(isDisabled: boolean): void {
    this.renderer2.setProperty(
      this.elementRef.nativeElement,
      'disabled',
      isDisabled
    );
  }

  writeValue(value: string): void {
    if (value) {
      this.applyMask(value);
    } else if (this.onChange != null) {
      this.onChange(null);
      this.elementRef.nativeElement.value = '';
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  @HostListener('keyup', ['$event'])
  onKeyup($event: any) {
    this.applyMask($event);
  }

  private applyMask($event: any) {
    let valor =
      $event.target && $event.target.value ? $event.target.value : $event;
    valor = valor ? valor.toString().replace(/\D/g, '') : '';

    if ($event.key === 'Backspace' || $event.keyCode === 8) {
      this.onChange(valor);
      return;
    }

    this.elementRef.nativeElement.value = NgMaskDirective.setMask(
      valor,
      this.ngMask
    );
    if (this.onChange) {
      this.onChange(NgMaskDirective.setMask(valor, this.ngMask));
    }
  }

  public static setMask(val: any, mask: string) {
    val = val || '';
    const pad = mask.replace(/\D/g, '').replace(/9/g, '_');
    const value = val + pad.substring(0, pad.length - val.length);

    let valuePos = 0;
    let vTemp = 0;
    val = '';
    for (let i = 0; i < mask.length; i++) {
      vTemp = valuePos;
      if (isNaN(parseInt(mask.charAt(i), 10)) && value[++vTemp]) {
        val += mask.charAt(i);
      } else {
        val += value[valuePos++] || '';
      }
    }

    if (val.indexOf('_') > -1) {
      val = val.substr(0, val.indexOf('_'));
    }

    return val;
  }
}
