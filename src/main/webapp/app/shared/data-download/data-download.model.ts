export class DataDownload {
    constructor(public fileNames: string[],
                public fileContent: string){

        this.fileNames = fileNames ? fileNames : null;
        this.fileContent = fileContent ? fileContent : null;
    }
}
