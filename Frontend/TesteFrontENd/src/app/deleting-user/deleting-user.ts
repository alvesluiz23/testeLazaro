import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserService } from '../service/user-service';
import { errorToAlertMessage } from '../utils/http-error';

@Component({
  selector: 'app-deleting-user',
  imports: [],
  templateUrl: './deleting-user.html',
  styleUrls: ['./deleting-user.css'],
})
export class DeletingUser {
  @Input() userId: string = '';

  constructor(private userService: UserService) {}

  @Output() deleteEvent = new EventEmitter<void>();

  async deleteUser() {
    if (!this.userId) {
      return;
    }
    try {
      await this.userService.deleteUser(this.userId);
      alert("User with ID " + this.userId + " deleted successfully.");
      this.deleteEvent.emit();
    } catch (error) {
      alert(errorToAlertMessage(error));
    }
  }


}
