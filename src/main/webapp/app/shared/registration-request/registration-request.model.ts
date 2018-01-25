import {Company} from '../../entities/company/company.model';

export class RegistrationRequest {
    public id?: any;
    public login?: string;
    public firstName?: string;
    public lastName?: string;
    public email?: string;
    public createdBy?: string;
    public createdDate?: Date;
    public lastModifiedBy?: string;
    public lastModifiedDate?: Date;
    public mobileTelephoneNumber: string;
    public userActivated: boolean;
    public adminApproved: boolean;
    public registrationKey: string;
    public company:Company;

    constructor(
        id?: any,
        login?: string,
        firstName?: string,
        lastName?: string,
        email?: string,
        createdBy?: string,
        createdDate?: Date,
        lastModifiedBy?: string,
        lastModifiedDate?: Date,
        mobileTelephoneNumber?: string,
        adminApproved?: boolean,
        userActivated?: boolean,
        registrationKey?: string
    ) {
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.mobileTelephoneNumber = mobileTelephoneNumber ? mobileTelephoneNumber : null;
        this.adminApproved = this.adminApproved ? adminApproved : null;
        this.userActivated = this.userActivated ? userActivated : null;
        this.registrationKey = this.registrationKey ? registrationKey : null;
    }
}
