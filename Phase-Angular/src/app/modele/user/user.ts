import { roletype } from "../role/roletype";

export class User {
  constructor(
    public userid: number,
    public firstname: string,
    public lastname: string,
    public cin: number,
    public email: string,
    public password: string,
    public phone: number,
    public address: string,
    public companyname: string,
    public profilepicture: string,
    public enabled:boolean,
    public roles:roletype
      ) { }
}
