import {Phone} from "./models/phone";
import {User} from "./models/user";

const PHONES: Phone[] = [
  {
    phoneId: null,
    userId: null,
    phoneNumber: '123456789',
    verified: false,
    primary: false,
    verificationLink: null
  },
  {
    phoneId: null,
    userId: null,
    phoneNumber: '098765213',
    verified: false,
    primary: false,
    verificationLink: null
  }
];

export const USER: User = {
  userId: null,
  username: 'username2',
  firstName: 'firstname2',
  lastName: 'lastname2',
  phones: PHONES
  };
