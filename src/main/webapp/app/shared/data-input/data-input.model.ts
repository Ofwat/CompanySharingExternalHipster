export class DataInput {
    public id?: any;
    public name?: string;
    public description?: string;
    public guidance?: string;
    public defaultDeadline?: any;
    public orderIndex?: any;
    public statusId?: any;
    public statusStatus?: string;
    public ownerId?: any;
    public ownerFirstName?: string;
    public ownerLastName?: string;
    public reviewerId?: any;
    public reviewerFirstName?: string;
    public reviewerLastName?: string;
    public dataBundleId?: any;
    public dataBundleName?: string;
    public createdDate?: any;
    public createdBy?: string;
    public lastModifiedDate?: any;
    public lastModifiedBy?: string;
    public fileName?: string;
    public fileLocation?: string;

    constructor(
        id?: any,
        name?: string,
        description?: string,
        guidance?: string,
        defaultDeadline?: any,
        orderIndex?: any,
        statusId?: any,
        statusStatus?: string,
        ownerId?: any,
        ownerFirstName?: string,
        ownerLastName?: string,
        reviewerId?: any,
        reviewerFirstName?: string,
        reviewerLastName?: string,
        dataBundleId?: any,
        dataBundleName?: string,
        createdDate?: any,
        createdBy?: any,
        lastModifiedDate?: any,
        lastModifiedBy?: any,
        fileName?: any,
        fileLocation?: any
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.description = description ? description : null;
        this.guidance = guidance ? guidance : null;
        this.defaultDeadline = defaultDeadline ? defaultDeadline : null;
        this.orderIndex = orderIndex ? orderIndex : null;
        this.statusId = statusId ? statusId : null;
        this.statusStatus = statusStatus ? statusStatus : null;
        this.ownerId = ownerId ? ownerId : null;
        this.ownerFirstName = ownerFirstName ? ownerFirstName : null;
        this.ownerLastName = ownerLastName ? ownerLastName : null;
        this.reviewerId = reviewerId ? reviewerId : null;
        this.reviewerFirstName = reviewerFirstName ? reviewerFirstName : null;
        this.reviewerLastName = reviewerLastName ? reviewerLastName : null;
        this.dataBundleId = dataBundleId ? dataBundleId : null;
        this.dataBundleName = dataBundleName ? dataBundleName : null;
        this.createdDate = createdDate ? createdDate : null;
        this.createdBy = createdBy ? createdBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.fileName = fileName ? fileName : null;
        this.fileLocation = fileLocation ? fileLocation : null;
    }
}
