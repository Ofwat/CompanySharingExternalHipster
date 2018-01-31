export class DataDownload {
    constructor(public fileNames: string[],
                public fileContent: string,
                public fileName: string){

        this.fileNames = fileNames ? fileNames : null;
        this.fileContent = fileContent ? fileContent : null;
        this.fileName = fileName ? fileName : null;
    }
}
