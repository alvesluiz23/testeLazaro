import { Component, EventEmitter, Output, inject } from '@angular/core';
import { UserInterface } from '../interface/user-interface';
import { UserService } from '../service/user-service';
import { ProfileInterface } from '../interface/user-profile-interface';
import { ProfileService } from '../service/profile-service';
import { errorToAlertMessage } from '../utils/http-error';

@Component({
  selector: 'app-creating-user',
  imports: [],
  templateUrl: './creating-user.html',
  styleUrl: './creating-user.css',
})
export class CreatingUser {
  @Output() createEvent = new EventEmitter<void>();

  newUser: UserInterface = {
    id: '',
    name: '',
    profiles: [],
  };

  errors: Map<string, string> = new Map();

  userService = inject(UserService);
  profileService = inject(ProfileService);

  ngOnInit() {
    this.profileService.fetchProfiles();
    this.errors = new Map<string, string>();
  }

  onNameChange(name: string) {
    const trimmed = name.trim();
    if (trimmed === '') {
      this.errors.set('name', 'Name cannot be empty!');
    } else if (trimmed.length < 10) {
      this.errors.set('name', 'Name must be at least 10 characters long!');
    } else {
      this.errors.delete('name');
    }
    this.newUser.name = name;
  }

  getProfiles(): ProfileInterface[] {
    return this.profileService
      .profiles()
      .map(({ id, description }) => ({ id, description }))
      .filter(profile => !this.newUser.profiles.some(p => p.id === profile.id));
  }

  errorMessages(): string[] {
    return Array.from(this.errors.values());
  }

  onProfileChange(profile: string) {
    const profileId = Number.parseInt(profile, 10);
    if (!Number.isFinite(profileId)) {
      return;
    }

    const selected = this.profileService.profiles().find(p => p.id === profileId);
    if (!selected) {
      return;
    }

    if (this.newUser.profiles.some(p => p.id === selected.id)) {
      return;
    }

    this.newUser.profiles = this.newUser.profiles.concat(selected);
    this.errors.delete('profiles');
  }
  
  deleteItem(profile: ProfileInterface) {
    const index = this.newUser.profiles.findIndex(p => p.id === profile.id);
    if(index !== -1) {
      this.newUser.profiles.splice(index, 1);
    }
  }

  async saveChanges() {
    this.onNameChange(this.newUser.name);
    if (this.newUser.profiles.length === 0) {
      this.errors.set('profiles', "Select at least one profile!");
    } else {
      this.errors.delete('profiles');
    }

    if (this.errors.size !== 0) {
      return;
    }

    try {
      await this.userService.createUser(this.newUser);
      this.newUser = { id: '', name: '', profiles: [] };
      this.errors = new Map<string, string>();
      alert('User created successfully!');
      this.createEvent.emit();
    } catch (error) {
      alert(errorToAlertMessage(error));
    }
  }

}
