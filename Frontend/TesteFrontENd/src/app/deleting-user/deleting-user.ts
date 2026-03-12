import { Component, Inject, Input, input } from '@angular/core';
import { UserService } from '../service/user-service';

@Component({
  selector: 'app-deleting-user',
  imports: [],
  templateUrl: './deleting-user.html',
  styleUrl: './deleting-user.css',
})
export class DeletingUser {
  @Input() userId: string = '';
  userService: UserService = Inject(UserService);
  
  deleteUser() {
    this.userService.deleteUser(this.userId);
  }
}
