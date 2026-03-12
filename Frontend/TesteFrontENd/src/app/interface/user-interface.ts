import { ProfileInterface } from "./user-profile-interface";

export interface UserInterface {
    id: string,
    name: string,
    profiles: ProfileInterface[]
}
