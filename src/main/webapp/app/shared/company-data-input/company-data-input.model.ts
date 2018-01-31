export class CompanyDataInput {
    public id?: any;
    public name?: string;
    public statusId?: any;
    public statusStatus?: string;
    public companyId?: any;
    public companyName?: string;
    public guidance?: string;
    public companyDataBundleId?: any;
    public companyDataBundleName?: string;
    public dataInputId?: any;
    public dataInputName?: string;
    public companyOwnerId?: any;
    public companyReviewerId?: any;
    public companyOwnerFirstName?: string;
    public companyReviewerFirstName?: string;
    public inputTypeId?: any;
    public inputTypeType?: string;

    constructor(
        id?: any,
        name?: string,
        companyId?: any,
        companyName?: string,
        companyDataBundleId?: any,
        companyDataBundleName?: string,
        statusId?: any,
        statusStatus?: string,
        dataInputId?: any,
        dataInputName?: string,
        companyOwnerId?: any,
        companyReviewerId?: any,
        companyOwnerFirstName?: string,
        companyReviewerFirstName?: string,
        inputTypeId?: any,
        inputTypeType?: string,
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.statusId = statusId ? statusId : null;
        this.statusStatus = statusStatus ? statusStatus : null;
        this.companyId = companyId ? companyId : null;
        this.companyName = companyName ? companyName : null;
        this.companyDataBundleId = companyDataBundleId ? companyDataBundleId : null;
        this.companyDataBundleName = companyDataBundleName ? companyDataBundleName : null;

        this.dataInputId = dataInputId ? dataInputId : null;
        this.dataInputName = dataInputName ? dataInputName : null;
        this.companyOwnerId = companyOwnerId ? companyOwnerId : null;
        this.companyReviewerId = companyReviewerId ? companyReviewerId : null;
        this.companyOwnerFirstName = companyOwnerFirstName ? companyOwnerFirstName : null;
        this.companyReviewerFirstName = companyReviewerFirstName ? companyReviewerFirstName : null;
        this.inputTypeId = inputTypeId ? inputTypeId : null;
        this.inputTypeType = inputTypeType ? inputTypeType : null;
    }
}
