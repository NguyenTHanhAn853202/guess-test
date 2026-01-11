export type ErrorList = {
    errorCode: string;
    fieldName: string;
    errorMessage: string;
}

export type Error = {
    name: string;
    errors: ErrorList[];
}

