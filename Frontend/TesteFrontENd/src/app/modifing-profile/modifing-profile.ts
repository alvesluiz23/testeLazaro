import { Component, EventEmitter, inject, Input, Output, signal, Signal } from '@angular/core';
import { UserInterface } from '../interface/user-interface';
import { ProfileInterface } from '../interface/user-profile-interface';
import { UserService } from '../service/user-service';
import { ProfileService } from '../service/profile-service';
import { NgModel } from '@angular/forms';

@Component({
  selector: 'app-modifing-profile',

  templateUrl: './modifing-profile.html',
  styleUrl: './modifing-profile.css',
})
export class ModifingProfile {
  @Input() user!: UserInterface;

  userService: UserService = inject(UserService);
  profileService = inject(ProfileService);

  selectedProfile!: string;

  @Output() editClicked = new EventEmitter<void>();

  actualStateUser!: UserInterface;

  errors: Map<string, string> = new Map();

  deleteItem(profile: ProfileInterface) {
    if (this.actualStateUser.profiles.length > 1) {
      this.actualStateUser.profiles = this.actualStateUser.profiles.filter(value => value.id !== profile.id);
    } else {
      this.errors.set("profiles", "You can't leave the user without any profiles!");
    }
  }

  onProfileChange(profile: string) {
    this.actualStateUser.profiles = this.actualStateUser.profiles.concat(this.profileService.profiles()
      .filter(p => p.id == parseInt(profile, 10)));
  }

  ngOnInit() {

    this.actualStateUser = {
      ...this.user,
      profiles: this.user.profiles.map(p => ({ ...p }))
    };
    console.log(this.actualStateUser);
    this.errors = new Map<string, string>();
    this.profileService.fetchProfiles();

  }


  getProfiles(){
    if(this.userService.loading()) {
      return [];
    }
    return   this.profileService.profiles()
    .map(({ id, description }) => ({ id, description }))
    .filter(profile => !this.actualStateUser.profiles.some(p => p.id === profile.id));
  }

  resetForm() {
    this.userService.getUser(this.user.id);
    this.actualStateUser = {
      ...this.user,
      profiles: this.userService.user()!.profiles.map(p => ({ ...p }))
    };
    this.errors = new Map<string, string>();
  }

  onNameChange(name: string) {
    if (name.trim() === "") {
      this.errors.set("name", "Name cannot be empty!");
      return;
    }
    if (name.length < 10) {
      this.errors.set("name", "Name must be at least 10 characters long!");
      return;
    }
    this.actualStateUser.name = name;
    console.log(this.actualStateUser);
    this.errors = new Map<string, string>();
  }

  async saveChanges() {
    if (this.actualStateUser.name.trim() === "") {
      this.errors.set("name", "Name cannot be empty!");
      return;
    }
    try {
      await this.userService.updateUsers(this.actualStateUser.id, this.actualStateUser);
      this.errors = new Map<string, string>();
      this.user = {
        ...this.userService.user()!,
        profiles: this.userService.user()!.profiles.map(p => ({ ...p }))
      };
      alert("Changes saved successfully!");
    } catch {
      alert("An error occurred while saving changes!");
    }
  }
}
