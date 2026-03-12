import { Component, inject, signal } from '@angular/core';
import { UserDisplay } from "../user-display/user-display";
import { UserService } from '../service/user-service';
import { UserInterface } from '../interface/user-interface';
import { ModifingProfile } from '../modifing-profile/modifing-profile';
import { ProfileService } from '../service/profile-service';
import { PageInterface } from '../interface/page-interface';
import { DeletingUser } from '../deleting-user/deleting-user';
import { CreatingUser } from "../creating-user/creating-user";

@Component({
  selector: 'app-users',
  imports: [UserDisplay, CreatingUser, DeletingUser, ModifingProfile],
  templateUrl: './users.html',
  styleUrl: './users.css',
})
export class Users {
    userService = inject(UserService)
    profileService = inject(ProfileService);
    users: UserInterface[] = [];
    selectedUser!: UserInterface;
    numberPage!: number;
    pages!: PageInterface<UserInterface>;
    mode = 0;

    openEdit(user: UserInterface) {
      this.mode = 1;
      this.selectedUser = user;

    }

    openDelete(user: UserInterface) {
      this.selectedUser = user;
      this.mode = 2;
    }

    openCreate() {
      this.mode = 3;
    }

    backToView() {
      this.userService.fetchUsers(this.numberPage);
      this.mode = 0;
    }

    changePage(page:number){
      this.userService.fetchUsers(page);

    }
    
    ngOnInit(){
       this.numberPage = 0;
       this.userService.fetchUsers(0);

    }
}
