export interface PageInterface<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}
