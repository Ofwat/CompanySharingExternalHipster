
export class PublishingStatus {
    public id?: any;
    public status?: any;

    constructor(
        id?: any,
        status?: any
    ) {
        this.id = id ? id : null;
        this.status = status ? status : null;
    }
}
