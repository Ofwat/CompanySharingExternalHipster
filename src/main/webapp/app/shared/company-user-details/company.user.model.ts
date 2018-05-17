import {Company} from '../../entities/company/company.model';
import {User} from "../user/user.model";

export class CompanyUserDetails {
    public id?: any;
    public company?: Company[];
    public user?: User[];
    public privileges?: any[];

    constructor(
        id?: any,
        company?: Company[],

    ) {
        this.id = id ? id : null;
        this.company = company ? company : null;

    }
}
