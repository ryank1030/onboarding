import {Phone} from "./phone";
import {User} from "./user";

const PHONE: Phone = {
  phoneId: '098',
  userId: '123',
  phoneNumber: '123456789',
  verified: false,
  primary: true,
  verificationLink: '4654785'
}

export const USERS: User[] = [
  {
  userId: '123',
  username: 'test',
  firstName: 'first',
  lastName: 'last',
  phones: PHONE
  },
  {
    userId: '234',
    username: 'test',
    firstName: 'first',
    lastName: 'last',
    phones: PHONE
  },
  {
    userId: '2435',
    username: 'test',
    firstName: 'first',
    lastName: 'last',
    phones: PHONE
  },
  {
    userId: '634',
    username: 'test',
    firstName: 'first',
    lastName: 'last',
    phones: PHONE
  }
  ];
