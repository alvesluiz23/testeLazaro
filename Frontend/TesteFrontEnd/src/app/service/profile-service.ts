import { inject, Injectable, signal } from '@angular/core';
import { ProfileDTO } from '../dto/profile-dto';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { errorToAlertMessage } from '../utils/http-error';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
    profiles = signal<ProfileDTO[]>([]);
    loading = signal<boolean>(false);
    http = inject(HttpClient);

    baseUrl = 'http://localhost:8080';
	  
    fetchProfiles() {
      this.loading.set(true);
      this.http.get<ProfileDTO[]>(`${this.baseUrl}/profiles/`).subscribe({
        next: (response) => {
          this.profiles.set(response);
        },
        error: (error) => {
          alert(errorToAlertMessage(error));
          this.loading.set(false);
        },
        complete: () => {
          this.loading.set(false);
        }
      });
    }

    async createProfile(profile: ProfileDTO): Promise<ProfileDTO> {
      this.loading.set(true);
      try {
        const payload: ProfileDTO = profile;
        const created = await firstValueFrom(
          this.http.post<ProfileDTO>(`${this.baseUrl}/profiles`, payload)
        );
        return created;
      } finally {
        this.loading.set(false);
      }
    }

    async updateProfile(id: number, description: string): Promise<ProfileDTO> {
      this.loading.set(true);
      try {
        const payload = { description };
        const updated = await firstValueFrom(
          this.http.put<ProfileDTO>(`${this.baseUrl}/profiles/${id}`, payload)
        );
        return updated;
      } finally {
        this.loading.set(false);
      }
    }

    async deleteProfile(id: number): Promise<void> {
      this.loading.set(true);
      try {
        await firstValueFrom(this.http.delete(`${this.baseUrl}/profiles/${id}`));
      } finally {
        this.loading.set(false);
      }
    }
}
