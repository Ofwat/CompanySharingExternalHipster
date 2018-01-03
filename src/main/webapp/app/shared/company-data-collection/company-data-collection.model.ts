import {User} from "../user/user.model";

export class CompanyDataCollection {
    public id?: any;
    public name?: string;
    public statusId?: any;
    public statusStatus?: string;
    public companyId?: any;
    public companyName?: string;
    public dataCollectionId?: any;
    public dataCollectionName?: string;
    public companyOwnerId?: any;
    public companyOwnerFirstName?: string;
    public companyReviewerId?: any;
    public companyReviewerFirstName?: string;
    public companyDataBundles?: any[];

    constructor(
        id?: any,
        name?: string,
        statusId?: any,
        statusStatus?: string,
        companyId?: any,
        companyName?: string,
        dataCollectionId?: any,
        dataCollectionName?: string,
        companyOwnerId?: any,
        companyOwnerFirstName?: string,
        companyReviewerId?: any,
        companyReviewerFirstName?: string,
        companyDataBundles?: any[]
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.statusId = statusId ? statusId : null;
        this.statusStatus = statusStatus ? statusStatus : null;
        this.companyId = companyId ? companyId : null;
        this.companyName = companyName ? companyName : null;
        this.dataCollectionId = dataCollectionId ? dataCollectionId : null;
        this.dataCollectionName = dataCollectionName ? dataCollectionName : null;
        this.companyOwnerId = companyOwnerId ? companyOwnerId : null;
        this.companyOwnerFirstName = companyOwnerFirstName ? companyOwnerFirstName : null;
        this.companyReviewerId = companyReviewerId ? companyReviewerId : null;
        this.companyReviewerFirstName = companyReviewerFirstName ? companyReviewerFirstName : null;
        this.companyDataBundles = companyDataBundles ? companyDataBundles : null;
    }
}
