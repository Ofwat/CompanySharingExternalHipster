import { User } from './../../shared';

export class Company {
    public id?: number;
    public name?: string;
    public deleted?: boolean;
    public users?: User[];

    constructor(
        id?: number,
        name?: string,
        deleted?: boolean,
        users?: User[],
    ) {
        this.id = id ? id : null;
        this.name = name ? name : null;
        this.deleted = deleted ? deleted : false;
        this.users = users ? users : null;
    }
}
