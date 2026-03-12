import { inject, Injectable, signal } from '@angular/core';
import { ProfileInterface } from '../interface/user-profile-interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
    profiles = signal<ProfileInterface[]>([]);
    http = inject(HttpClient);
  
    fetchProfiles() {
      this.http
        .get<ProfileInterface[]>(`http://localhost:8080/profiles/`)
        .subscribe((response) => {
           this.profiles.set(response);
        });
    }

    
}
