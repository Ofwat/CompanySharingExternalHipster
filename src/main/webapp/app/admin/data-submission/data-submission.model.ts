
export class DataSubmissionModel {
    public id?: any;
    public jobStatus?: string;
    public rejectedReason?: string;
    public companyName?: string;
    public companyDataInputName?: string;
    public companyDataBundleName?: string;
    public companyDeadline?: string;

    constructor(
        id?: any,
        jobStatus?: string,
        rejectedReason?: string,
        companyName?: string,
        companyDataInputName?: string,
        companyDataBundleName?: string,
        companyDeadline?: string
    ) {
        this.id = id ? id : null;
        this.jobStatus = jobStatus ? jobStatus : null;
        this.rejectedReason = rejectedReason ? rejectedReason : null;
        this.companyName = companyName ? companyName : null;
        this.companyDataInputName = companyDataInputName ? companyDataInputName : null;
        this.companyDataBundleName = companyDataBundleName ? companyDataBundleName : null;
        this.companyDeadline = companyDeadline ? companyDeadline : null;
    }
}
