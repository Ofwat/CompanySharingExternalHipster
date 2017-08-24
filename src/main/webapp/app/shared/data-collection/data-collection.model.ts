export class DataCollection {
    public id?: any;
    public name?: string;

    constructor(
        id?: any,
        name?: string
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
    }
}
