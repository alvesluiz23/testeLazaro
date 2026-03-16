import { Component, inject, signal } from '@angular/core';
import { UserDisplay } from "../user-display/user-display";
import { UserService } from '../service/user-service';
import { UserInterface } from '../interface/user-interface';
import { ModifingProfile } from '../modifing-profile/modifing-profile';
import { ProfileService } from '../service/profile-service';
import { PageInterface } from '../interface/page-interface';
import { CreatingUser } from "../creating-user/creating-user";

@Component({
  selector: 'app-users',
  imports: [UserDisplay, CreatingUser, ModifingProfile],
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

    async openDelete(user: UserInterface) {
       const confirmed = confirm(`Delete user "${user.name}"?`);
       if (confirmed) {
        try {
          await this.userService.deleteUser(user.id);
        } catch (error) {
          alert("Error deleting user: " + error);
        }
       }
       this.userService.fetchUsers(this.numberPage);
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
