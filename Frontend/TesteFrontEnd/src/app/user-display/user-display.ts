import { LowerCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProfileInterface } from '../interface/user-profile-interface';

@Component({
  selector: 'app-user-display',
  imports: [LowerCasePipe],
  templateUrl: './user-display.html',
  styleUrl: './user-display.css',
})
export class UserDisplay {
  @Input() profiles: ProfileInterface[] = [];
  @Input() name: string = "";
  @Input() id: string = "";

  @Output() editClicked = new EventEmitter<void>();
  @Output() deleteClicked = new EventEmitter<void>();

  editUser() {
    this.editClicked.emit();
  }

  openDelete() {
     this.deleteClicked.emit();
  }
}
