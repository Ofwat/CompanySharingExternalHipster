import {Company} from '../../entities/company/company.model';
import {CompanyUserDetails} from "../company-user-details/company.user.model";

export class User {
    public id?: any;
    public login?: string;
    public firstName?: string;
    public lastName?: string;
    public email?: string;
    public activated?: Boolean;
    public langKey?: string;
    public authorities?: any[];
    public privileges?: any[];
    public companies?: any[];
    public companyUserDetails?: any[];
    public companyUserPrivilegeDetails?: any[];
    public createdBy?: string;
    public createdDate?: Date;
    public lastModifiedBy?: string;
    public lastModifiedDate?: Date;
    public password?: string;
    public enabled?: Boolean;
    public mobileTelephoneNumber: string;
    public passwordLastChangeDate: Date;

    constructor(
        id?: any,
        login?: string,
        firstName?: string,
        lastName?: string,
        email?: string,
        activated?: Boolean,
        langKey?: string,
        authorities?: any[],
        privileges?: any[],
        companies?: any[],
        companyUserDetails?: any[],
        companyUserPrivilegeDetails?: any[],
        createdBy?: string,
        createdDate?: Date,
        lastModifiedBy?: string,
        lastModifiedDate?: Date,
        password?: string,
        enabled?: Boolean,
        mobileTelephoneNumber?: string,
        passwordLastChangeDate?: Date,

    ) {
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.privileges = privileges ? privileges : null;
        this.companies = companies ? this.companies : null;
        this.companyUserDetails = companyUserDetails ? companyUserDetails : null;
        this.companyUserPrivilegeDetails = companyUserPrivilegeDetails ? companyUserPrivilegeDetails : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.password = password ? password : null;
        this.enabled = enabled ? enabled : null;
        this.mobileTelephoneNumber = mobileTelephoneNumber ? mobileTelephoneNumber : null;
        this.passwordLastChangeDate = passwordLastChangeDate ? passwordLastChangeDate : null;
    }
}
