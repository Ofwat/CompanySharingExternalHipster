export class CompanyDataBundle {
    public id?: any;
    public name?: string;
    public companyDeadline?: any;
    public statusStatus?: string;
    public companyId?: any;
    public companyDataCollectionId?: any;
    public companyDataCollectionName?: string;
    public dataBundleId?: any;
    public dataBundleName?: string;
    public companyOwnerId?: any;
    public companyOwnerFirstName?: string;
    public companyReviewerId?: any;
    public companyReviewerFirstName?: string;
    public companyDataInputs?: any[];

    constructor(
        id?: any,
        name?: string,
        companyDeadline?: any,
        statusStatus?: string,
        companyId?: any,
        companyDataCollectionId?: any,
        companyDataCollectionName?: string,
        dataBundleId?: any,
        dataBundleName?: string,
        companyOwnerId?: any,
        companyOwnerFirstName?: string,
        companyReviewerId?: any,
        companyReviewerFirstName?: string,
        companyDataInputs?: any[]
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.companyDeadline = companyDeadline ? companyDeadline : null;
        this.statusStatus = statusStatus ? statusStatus : null;
        this.companyId = companyId ? companyId : null;
        this.companyDataCollectionId = companyDataCollectionId ? companyDataCollectionId : null;
        this.companyDataCollectionName = companyDataCollectionName ? companyDataCollectionName : null;
        this.dataBundleId = dataBundleId ? dataBundleId : null;
        this.dataBundleName = dataBundleName ? dataBundleName : null;
        this.companyOwnerId = companyOwnerId ? companyOwnerId : null;
        this.companyOwnerFirstName = companyOwnerFirstName ? companyOwnerFirstName : null;
        this.companyReviewerId = companyReviewerId ? companyReviewerId : null;
        this.companyReviewerFirstName = companyReviewerFirstName ? companyReviewerFirstName : null;
        this.companyDataInputs = companyDataInputs ? companyDataInputs : null;
    }
}

