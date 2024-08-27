import { Role } from './Role';

export class UserModel {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  phoneNumber?: number;
  address?: string;
  role?: Role;
  sexe?: string;
  age?: number;
}
