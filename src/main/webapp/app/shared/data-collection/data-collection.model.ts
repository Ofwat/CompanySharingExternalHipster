export class DataCollection {
    public id?: any;
    public name?: string;
    public publishingStatus?: any;
    public owner?: any;
    public reviewer?: any;
    public description?: string;
    public guidance?: string;
    public createdDate?: any;
    public createdBy?: string;
    public lastModifiedDate?: any;
    public lastModifiedBy?: string;

    constructor(
        id?: any,
        name?: string,
        publishingStatus?: any,
        owner?: any,
        reviewer?: any,
        description?: string,
        guidance?: string,
        createdDate?: any,
        createdBy?: any,
        lastModifiedDate?: any,
        lastModifiedBy?: any
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.publishingStatus = publishingStatus ? publishingStatus : null;
        this.owner = owner ? owner : null;
        this.reviewer = reviewer ? reviewer : null;
        this.description = description ? description : null;
        this.guidance = guidance ? guidance : null;
        this.createdDate = createdDate ? createdDate : null;
        this.createdBy = createdBy ? createdBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
    }
}
