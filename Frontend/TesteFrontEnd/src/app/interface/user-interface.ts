import { ProfileInterface } from "./profile-interface";

export interface UserInterface {
    id: string,
    name: string,
    profiles: ProfileInterface[]
}
