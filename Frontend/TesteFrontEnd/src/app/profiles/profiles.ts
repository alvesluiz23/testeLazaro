import { Component, EventEmitter, Output, inject } from '@angular/core';
import { ProfileInterface } from '../interface/profile-interface';
import { ProfileService } from '../service/profile-service';
import { errorToAlertMessage } from '../utils/http-error';

@Component({
  selector: 'app-profiles',
  imports: [],
  templateUrl: './profiles.html',
  styleUrl: './profiles.css',
})
export class Profiles {
  profileService = inject(ProfileService);

  mode = 0;
  selected: ProfileInterface | undefined;
  idText = '';
  description = '';

  errors: Map<string, string> = new Map();

  @Output() backEvent = new EventEmitter<void>();

  ngOnInit() {
    this.profileService.fetchProfiles();
    this.errors = new Map<string, string>();
  }

  startCreate() {
    this.mode = 1;
    this.selected = undefined;
    this.idText = '';
    this.description = '';
    this.errors = new Map<string, string>();
  }

  startEdit(profile: ProfileInterface) {
    this.mode = 2;
    this.selected = { ...profile };
    this.description = profile.description;
    this.errors = new Map<string, string>();
  }

  cancel() {
    this.mode = 0;
    this.selected = undefined;
    this.idText = '';
    this.description = '';
    this.errors = new Map<string, string>();
  }

  onDescriptionChange(description: string) {
    this.description = description;
    const trimmed = description.trim();
    if (trimmed === '') {
      this.errors.set('description', 'Description cannot be empty!');
      return;
    }
    if (trimmed.length < 5) {
      this.errors.set('description', 'Description must be at least 5 characters long!');
      return;
    }
    this.errors.delete('description');
  }

  errorMessages(): string[] {
    return Array.from(this.errors.values());
  }

  async saveCreate() {
    const id = Number.parseInt(this.idText, 10);
    if (!Number.isFinite(id) || id <= 0) {
      this.errors.set('id', 'Id must be a positive number!');
      return;
    }
    this.errors.delete('id');

    this.onDescriptionChange(this.description);
    if (this.errors.size !== 0) {
      return;
    }
    try {
      await this.profileService.createProfile({ id, description: this.description.trim() });
      alert('Profile created successfully!');
      this.profileService.fetchProfiles();
      this.cancel();
    } catch (error) {
      alert(errorToAlertMessage(error));
    }
  }

  async saveEdit() {
    if (!this.selected) {
      return;
    }
    this.onDescriptionChange(this.description);
    if (this.errors.size !== 0) {
      return;
    }
    try {
      await this.profileService.updateProfile(this.selected.id, this.description.trim());
      alert('Profile updated successfully!');
      this.profileService.fetchProfiles();
      this.cancel();
    } catch (error) {
      alert(errorToAlertMessage(error));
    }
  }

  async deleteProfile(profile: ProfileInterface) {
    const confirmed = confirm(`Delete profile "${profile.description}"?`);
    if (!confirmed) {
      return;
    }
    try {
      await this.profileService.deleteProfile(profile.id);
      alert('Profile deleted successfully!');
      this.profileService.fetchProfiles();
    } catch (error) {
      alert(errorToAlertMessage(error));
    }
  }
}
