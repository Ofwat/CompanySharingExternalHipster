
export class DataCollection {
    public id?: any;
    public name?: string;
    public statusId?: any;
    public statusStatus?: string;
    public ownerId?: any;
    public ownerFirstName?: string;
    public ownerLastName?: string;
    public reviewerId?: any;
    public reviewerFirstName?: string;
    public reviewerLastName?: string;
    public description?: string;
    public guidance?: string;
    public createdDate?: any;
    public createdBy?: string;
    public lastModifiedDate?: any;
    public lastModifiedBy?: string;
    public dataBundles?: any[];

    constructor(
        id?: any,
        name?: string,
        statusId?: any,
        statusStatus?: string,
        ownerId?: any,
        ownerFirstName?: string,
        ownerLastName?: string,
        reviewerId?: any,
        reviewerFirstName?: string,
        reviewerLastName?: string,
        description?: string,
        guidance?: string,
        createdDate?: any,
        createdBy?: any,
        lastModifiedDate?: any,
        lastModifiedBy?: any,
        dataBundles?: any[]
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.statusId = statusId ? statusId : null;
        this.statusStatus = statusStatus ? statusStatus : null;
        this.ownerId = ownerId ? ownerId : null;
        this.ownerFirstName = ownerFirstName ? ownerFirstName : null;
        this.ownerLastName = ownerLastName ? ownerLastName : null;
        this.reviewerId = reviewerId ? reviewerId : null;
        this.reviewerFirstName = reviewerFirstName ? reviewerFirstName : null;
        this.reviewerLastName = reviewerLastName ? reviewerLastName : null;
        this.description = description ? description : null;
        this.guidance = guidance ? guidance : null;
        this.createdDate = createdDate ? createdDate : null;
        this.createdBy = createdBy ? createdBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.dataBundles = dataBundles ? dataBundles : null;
    }
}
